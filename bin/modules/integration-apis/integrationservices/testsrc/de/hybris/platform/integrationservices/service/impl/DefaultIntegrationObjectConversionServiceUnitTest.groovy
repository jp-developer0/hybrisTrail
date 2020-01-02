/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.integrationservices.service.impl

import de.hybris.bootstrap.annotations.UnitTest
import de.hybris.platform.core.model.ItemModel
import de.hybris.platform.core.model.type.ComposedTypeModel
import de.hybris.platform.integrationservices.model.IntegrationObjectItemModel
import de.hybris.platform.integrationservices.model.IntegrationObjectModel
import de.hybris.platform.integrationservices.model.TypeDescriptor
import de.hybris.platform.integrationservices.populator.ItemToMapConversionContext
import de.hybris.platform.integrationservices.service.IntegrationObjectAndItemMismatchException
import de.hybris.platform.integrationservices.service.IntegrationObjectNotFoundException
import de.hybris.platform.integrationservices.service.IntegrationObjectService
import de.hybris.platform.servicelayer.dto.converter.Converter
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException
import org.junit.Test
import spock.lang.Specification

@UnitTest
class DefaultIntegrationObjectConversionServiceUnitTest extends Specification {
    private static final String INTEGRATION_OBJECT = "ProductIntegrationObject"
    private static final def CONVERSION_RESULT = [:]

    def conversionService = new DefaultIntegrationObjectConversionService()
    def integrationObjectService = Stub(IntegrationObjectService)

    def setup() {
        conversionService.setIntegrationObjectService(integrationObjectService)
        conversionService.itemToIntegrationObjectMapConverter = Stub(Converter) {
            convert(_, _) >> CONVERSION_RESULT
        }
    }

    @Test
    def "populate result map when item matches an integration object item type"() {
        given: "a data item"
        def item = itemModel('Product')
        and: "an integration object containing an item type definition for the data item"
        integrationObjectService.findIntegrationObject(INTEGRATION_OBJECT) >> Stub(IntegrationObjectModel) {
            getItems() >> [integrationObjectItem('Product')]
        }

        when:
        def map = conversionService.convert(item, INTEGRATION_OBJECT)

        then:
        map == CONVERSION_RESULT
    }

    @Test
    def "populate result map when item matches an integration object item subtype"() {
        given: "a data item"
        def item = itemModel('ApparelProduct')
        and: "an integration object containing an item type definition for the data item"
        integrationObjectService.findIntegrationObject(INTEGRATION_OBJECT) >> Stub(IntegrationObjectModel) {
            getItems() >> [integrationObjectItem('Product', ['ApparelProduct'])]
        }

        when:
        def map = conversionService.convert(item, INTEGRATION_OBJECT)

        then:
        map == CONVERSION_RESULT
    }

    @Test
    def "throws exception when item model is null"() {
        when:
        conversionService.convert(null, INTEGRATION_OBJECT)

        then:
        thrown IllegalArgumentException
    }

    @Test
    def "throws exception when specified integration object is not found"() {
        given: 'the specified integration object does not exist'
        integrationObjectService.findIntegrationObject(INTEGRATION_OBJECT) >> {throw new ModelNotFoundException('')}

        when:
        conversionService.convert(itemModel('SomeType'), INTEGRATION_OBJECT)

        then:
        def e = thrown IntegrationObjectNotFoundException
        e.message.contains INTEGRATION_OBJECT
        e.cause instanceof ModelNotFoundException
    }

    @Test
    def "throws exception when data item is not present in the integration object model"() {
        given: "a data item"
        def item = itemModel('SomeType')
        and: "an integration object with an integration object item for type other than the data item's type"
        integrationObjectService.findIntegrationObject(INTEGRATION_OBJECT) >> Stub(IntegrationObjectModel) {
            getCode() >> INTEGRATION_OBJECT
            getItems() >> [integrationObjectItem('AnotherType')]
        }

        when:
        conversionService.convert(item, INTEGRATION_OBJECT)

        then:
        def e = thrown IntegrationObjectAndItemMismatchException
        e.message.contains INTEGRATION_OBJECT
        e.message.contains item.toString()
    }

    @Test
    def "context conversion places the conversion result into the context"() {
        ItemToMapConversionContext context = Mock() {
            getTypeDescriptor() >> Stub(TypeDescriptor)
        }

        when:
        def result = conversionService.convert(context)

        then:
        result.is CONVERSION_RESULT
        1 * context.setConversionResult([:])
    }

    @Test
    def "context conversion returns matching conversion result in the context"() {
        given:
        def contextConversionResult = [source: 'context']
        def context = Stub(ItemToMapConversionContext) {
            getConversionResult() >> contextConversionResult
            getTypeDescriptor() >> Stub(IntegrationObjectItemModel)
        }

        when:
        def result = conversionService.convert(context)

        then:
        result == contextConversionResult
    }

    private ItemModel itemModel(String type) {
        Stub(ItemModel) {
            getItemtype() >> type
        }
    }

    private IntegrationObjectItemModel integrationObjectItem(String type, List<String> subtypes=[]) {
        Stub(IntegrationObjectItemModel) {
            getType() >> Stub(ComposedTypeModel) {
                getCode() >> type
                getAllSubTypes() >> subtypes.collect { composedType(it) }
            }
        }
    }

    private ComposedTypeModel composedType(String type) {
        Stub(ComposedTypeModel) {
            getCode() >> type
        }
    }
}
