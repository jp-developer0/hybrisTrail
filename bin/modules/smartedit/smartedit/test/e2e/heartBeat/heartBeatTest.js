/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
describe('Storefront FrontEnd <-> SmartEdit FrontEnd connectivity E2E', function() {
    var alertsComponent = require('../utils/components/systemAlertsComponentObject');

    it('shows warning when storefront is not sending a heartbeat', function() {
        browser.waitForAngularEnabled(false);
        browser.get('test/e2e/heartBeat/noHeartBeatMocks/smartedit.html');
        alertsComponent.assertions.assertAlertTextByIndex(0, 'Heart beat failed');
    });

    it('shows info popup when storefront is responding after unresponsive period', function() {
        browser.waitForAngularEnabled(false);
        browser.get('test/e2e/heartBeat/reconnectingHeartBeatMocks/smartedit.html');

        var expected = new RegExp('Heart beat reconnected', 'i');

        browser.waitUntil(function() {
            return browser.findElement(alertsComponent.locators.alertMessageByIndex(0)).getText().then(function(text) {
                return expected.test(text);
            });
        }, "was expecting to see an alert with text: 'Heart beat reconnected'");
    });
});
