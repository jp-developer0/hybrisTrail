/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
angular.module('genericEditorApp', ['smarteditServicesModule', 'genericEditorModule', 'localizedElementModule', 'templateCacheDecoratorModule'])
    .controller('defaultController', function($rootScope, sharedDataService, restServiceFactory, languageService) {
        restServiceFactory.setDomain('thedomain');
        sharedDataService.set('experience', {
            siteDescriptor: {
                uid: 'someSiteUid'
            },
            catalogDescriptor: {
                catalogId: 'someCatalogId',
                catalogVersion: 'someCatalogVersion'
            }
        });

        this.thesmarteditComponentType = 'thesmarteditComponentType';
        this.thesmarteditComponentId = 'thesmarteditComponentId';
        this.structureApi = "cmswebservices/v1/types/:smarteditComponentType";
        this.displaySubmit = true;
        this.displayCancel = true;
        this.CONTEXT_CATALOG = "CURRENT_CONTEXT_CATALOG";
        this.CONTEXT_CATALOG_VERSION = "CURRENT_CONTEXT_CATALOG_VERSION";

        this.contentApi = '/cmswebservices/v1/catalogs/' + this.CONTEXT_CATALOG + '/versions/' + this.CONTEXT_CATALOG_VERSION + '/items';

        this.setGenericEditorApi = function(api) {
            this.genericEditorApi = api;
            this.genericEditorApi.getLanguages = function() {
                return languageService.getLanguagesForSite('someOtherSiteUid');
            }.bind(this);
        }.bind(this);

    });
