/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.odata2webservicesfeaturetests.useraccess

import de.hybris.bootstrap.annotations.IntegrationTest
import de.hybris.platform.catalog.model.CatalogModel
import de.hybris.platform.core.model.user.EmployeeModel
import de.hybris.platform.integrationservices.model.IntegrationObjectModel
import de.hybris.platform.integrationservices.util.IntegrationTestUtil
import de.hybris.platform.odata2webservices.constants.Odata2webservicesConstants
import de.hybris.platform.servicelayer.ServicelayerSpockSpecification
import de.hybris.platform.webservicescommons.testsupport.server.NeedsEmbeddedServer
import org.apache.olingo.odata2.api.commons.HttpStatusCodes
import org.junit.Test
import org.springframework.http.MediaType
import spock.lang.Unroll

import javax.ws.rs.client.Entity

import static de.hybris.platform.integrationservices.util.JsonBuilder.json
import static de.hybris.platform.odata2webservicesfeaturetests.useraccess.UserAccessTestUtils.PASSWORD
import static de.hybris.platform.odata2webservicesfeaturetests.useraccess.UserAccessTestUtils.SERVICE

@NeedsEmbeddedServer(webExtensions = [Odata2webservicesConstants.EXTENSIONNAME])
@IntegrationTest
class HttpSecurityIntegrationTest extends ServicelayerSpockSpecification {
    private static final String CATALOG_ID = 'testNonRestrictedCatalog'
    private static final String USER = 'tester'

    def setupSpec() {
        importCsv '/impex/essentialdata-odata2services.impex', 'UTF-8'
        importCsv '/impex/essentialdata-odata2webservices.impex', 'UTF-8'

        UserAccessTestUtils.givenCatalogIOExists()
    }

    def cleanupSpec() {
        IntegrationTestUtil.removeAll IntegrationObjectModel
    }

    def setup() {
        IntegrationTestUtil.createCatalogWithId(CATALOG_ID)
    }

    def cleanup() {
        IntegrationTestUtil.findAny(EmployeeModel, { it.uid == USER }).ifPresent { IntegrationTestUtil.remove(it) }
        IntegrationTestUtil.findAny(CatalogModel, { it.id == CATALOG_ID }).ifPresent { IntegrationTestUtil.remove it }
    }

    @Test
    @Unroll
    def "User must be authenticated in order to GET #path"() {
        when:
        def response = UserAccessTestUtils.basicAuthRequest(SERVICE)
                .path(path)
                .build()
                .get()

        then:
        response.status == HttpStatusCodes.UNAUTHORIZED.statusCode

        where:
        path << ['$metadata', "Catalogs('${CATALOG_ID}')"]
    }

    @Test
    @Unroll
    def "User must be authenticated in order to POST to #path"() {
        when:
        def response = UserAccessTestUtils.basicAuthRequest(SERVICE)
                .path(path)
                .build()
                .post(Entity.json('{}'))

        then:
        response.status == HttpStatusCodes.UNAUTHORIZED.statusCode

        where:
        path << ['Catalogs', '$batch']
    }

    @Test
    def "User must be authenticated in order to DELETE"() {
        when:
        def response = UserAccessTestUtils.basicAuthRequest(SERVICE)
                .path('Catalogs')
                .build()
                .delete()

        then:
        response.status == HttpStatusCodes.UNAUTHORIZED.statusCode
    }

    @Test
    def "Wrong credentials for GET"() {
        given:
        IntegrationTestUtil.importImpEx(
                '$password=@password[translator = de.hybris.platform.impex.jalo.translators.UserPasswordTranslator]',
                'INSERT_UPDATE Employee; UID[unique = true] ; groups(uid)[mode = append] ;$password',
                "                      ; $USER            ; integrationadmingroup      ;*:$PASSWORD")

        when:
        def response = UserAccessTestUtils.basicAuthRequest(SERVICE)
                .credentials(USER, "wrong-$PASSWORD")
                .build()
                .get()

        then:
        response.status == HttpStatusCodes.UNAUTHORIZED.statusCode
    }

    @Test
    @Unroll
    def "Wrong credentials for POST to #path"() {
        given:
        IntegrationTestUtil.importImpEx(
                '$password=@password[translator = de.hybris.platform.impex.jalo.translators.UserPasswordTranslator]',
                'INSERT_UPDATE Employee; UID[unique = true] ; groups(uid)[mode = append] ;$password',
                "                      ; $USER            ; integrationadmingroup      ;*:$PASSWORD"
        )

        when:
        def response = UserAccessTestUtils.basicAuthRequest(SERVICE)
                .path(path)
                .credentials(USER, "wrong-${PASSWORD}")
                .build()
                .post Entity.json(json().withField("id", "testNewCatalog").build())

        then:
        response.status == HttpStatusCodes.UNAUTHORIZED.statusCode

        where:
        path << ['Catalogs', '$batch']
    }

    @Test
    def "Wrong credentials for DELETE"() {
        given:
        IntegrationTestUtil.importImpEx(
                '$password=@password[translator = de.hybris.platform.impex.jalo.translators.UserPasswordTranslator]',
                'INSERT_UPDATE Employee; UID[unique = true] ; groups(uid)           ;$password',
                "                      ; $USER            ; integrationdeletegroup;*:secret")

        when:
        def response = UserAccessTestUtils.basicAuthRequest(SERVICE)
                .path("Catalogs('$CATALOG_ID')")
                .credentials(USER, '123')
                .build()
                .delete()

        then:
        response.status == HttpStatusCodes.UNAUTHORIZED.statusCode
    }

    @Test
    @Unroll
    def "User in group '#group' gets #status for GET #path"() {
        given:
        IntegrationTestUtil.importImpEx(
                '$password=@password[translator = de.hybris.platform.impex.jalo.translators.UserPasswordTranslator]',
                'INSERT_UPDATE Employee; UID[unique = true]; groups(uid);$password',
                "                      ; $USER             ; $group     ;*:$PASSWORD")

        when:
        def response = UserAccessTestUtils.basicAuthRequest(SERVICE)
                .path(path)
                .credentials(USER, PASSWORD)
                .build()
                .get()

        then:
        response.status == status.statusCode

        where:
        group                        | status                    | path
        ''                           | HttpStatusCodes.FORBIDDEN | 'Catalogs'
        'integrationusergroup'       | HttpStatusCodes.FORBIDDEN | 'Catalogs'
        'integrationadmingroup'      | HttpStatusCodes.OK        | 'Catalogs'
        'integrationcreategroup'     | HttpStatusCodes.FORBIDDEN | 'Catalogs'
        'integrationviewgroup'       | HttpStatusCodes.OK        | 'Catalogs'
        'integrationdeletegroup'     | HttpStatusCodes.FORBIDDEN | 'Catalogs'
        'integrationservicegroup'    | HttpStatusCodes.FORBIDDEN | 'Catalogs'
        'integrationmonitoringgroup' | HttpStatusCodes.FORBIDDEN | 'Catalogs'
        ''                           | HttpStatusCodes.FORBIDDEN | '$metadata'
        'integrationusergroup'       | HttpStatusCodes.FORBIDDEN | '$metadata'
        'integrationadmingroup'      | HttpStatusCodes.OK        | '$metadata'
        'integrationcreategroup'     | HttpStatusCodes.OK        | '$metadata'
        'integrationviewgroup'       | HttpStatusCodes.OK        | '$metadata'
        'integrationdeletegroup'     | HttpStatusCodes.FORBIDDEN | '$metadata'
        'integrationservicegroup'    | HttpStatusCodes.FORBIDDEN | '$metadata'
        'integrationmonitoringgroup' | HttpStatusCodes.FORBIDDEN | '$metadata'
    }

    @Test
    @Unroll
    def "User in group '#group' gets #status for POST"() {
        given:
        IntegrationTestUtil.importImpEx(
                '$password=@password[translator = de.hybris.platform.impex.jalo.translators.UserPasswordTranslator]',
                'INSERT_UPDATE Employee; UID[unique = true]; groups(uid);$password',
                "                      ; $USER             ; $group     ;*:$PASSWORD")

        when:
        def response = UserAccessTestUtils.basicAuthRequest(SERVICE)
                .path('Catalogs')
                .credentials(USER, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .build()
                .post Entity.json(json().withField("id", "testNewCatalog").build())

        then:
        response.status == status.statusCode

        where:
        group                        | status
        ''                           | HttpStatusCodes.FORBIDDEN
        'integrationusergroup'       | HttpStatusCodes.FORBIDDEN
        'integrationadmingroup'      | HttpStatusCodes.CREATED
        'integrationcreategroup'     | HttpStatusCodes.CREATED
        'integrationviewgroup'       | HttpStatusCodes.FORBIDDEN
        'integrationdeletegroup'     | HttpStatusCodes.FORBIDDEN
        'integrationservicegroup'    | HttpStatusCodes.FORBIDDEN
        'integrationmonitoringgroup' | HttpStatusCodes.FORBIDDEN
    }

    @Test
    @Unroll
    def "User in group '#group' gets #status for DELETE"() {
        given:
        IntegrationTestUtil.importImpEx(
                '$password=@password[translator = de.hybris.platform.impex.jalo.translators.UserPasswordTranslator]',
                'INSERT_UPDATE Employee; UID[unique = true]; groups(uid);$password',
                "                      ; $USER             ; $group     ;*:$PASSWORD")

        when:
        def response = UserAccessTestUtils.basicAuthRequest(SERVICE)
                .path("Catalogs('$CATALOG_ID')")
                .credentials(USER, PASSWORD)
                .build()
                .delete()

        then:
        response.status == status.statusCode

        where:
        group                        | status
        ''                           | HttpStatusCodes.FORBIDDEN
        'integrationusergroup'       | HttpStatusCodes.FORBIDDEN
        'integrationadmingroup'      | HttpStatusCodes.OK
        'integrationcreategroup'     | HttpStatusCodes.FORBIDDEN
        'integrationviewgroup'       | HttpStatusCodes.FORBIDDEN
        'integrationdeletegroup'     | HttpStatusCodes.OK
        'integrationservicegroup'    | HttpStatusCodes.FORBIDDEN
        'integrationmonitoringgroup' | HttpStatusCodes.FORBIDDEN
    }
}