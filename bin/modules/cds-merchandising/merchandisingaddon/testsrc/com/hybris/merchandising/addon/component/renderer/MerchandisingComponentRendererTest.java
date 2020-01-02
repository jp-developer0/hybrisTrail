/**
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.hybris.merchandising.addon.component.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.hybris.merchandising.addon.model.MerchandisingCarouselComponentModel;
import com.hybris.merchandising.constants.MerchandisingaddonConstants;

import de.hybris.platform.acceleratorservices.uiexperience.UiExperienceService;
import de.hybris.platform.apiregistryservices.model.AbstractDestinationModel;
import de.hybris.platform.apiregistryservices.model.ConsumedDestinationModel;
import de.hybris.platform.apiregistryservices.model.DestinationTargetModel;
import de.hybris.platform.apiregistryservices.model.EndpointModel;
import de.hybris.platform.apiregistryservices.strategies.ConsumedDestinationLocatorStrategy;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import de.hybris.platform.commerceservices.enums.UiExperienceLevel;
import de.hybris.platform.commerceservices.i18n.CommerceCommonI18NService;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.type.TypeService;

/**
 * Test suite for {@code MerchandisingComponentRenderer}.
 *
 */
public class MerchandisingComponentRendererTest {

	MerchandisingComponentRenderer<MerchandisingCarouselComponentModel> renderer;

	private static final String CAROUSEL_ID = "123";
	private static final String COMPONENT_ID = "componentID";
	private static final String SERVICE_URL = "serviceUrl";
	private static final String SERVICE_URL_VALUE = "https://myserviceurl";
	private static final String ENCODED_CURRENCY = "&pound;";
	private static final String CURRENCY = "currency";

	@Before
	public void setUp() {
		renderer = new MerchandisingComponentRenderer<>();

		final ModelService mockModelService = Mockito.mock(ModelService.class);
		renderer.setModelService(mockModelService);

		final CMSComponentService mockCmsComponentService = Mockito.mock(CMSComponentService.class);
		renderer.setCmsComponentService(mockCmsComponentService);

		final CommerceCommonI18NService mockCommonI18NService = Mockito.mock(CommerceCommonI18NService.class);
		renderer.setCommerceCommonI18NService(mockCommonI18NService);

		final TypeService mockTypeService = Mockito.mock(TypeService.class);
		final ComposedTypeModel composedModel = Mockito.mock(ComposedTypeModel.class);
		Mockito.when(mockTypeService.getComposedTypeForCode(Mockito.anyString())).thenReturn(composedModel);
		Mockito.when(composedModel.getExtensionName()).thenReturn("merchandisingaddon");
		renderer.setTypeService(mockTypeService);

		final UiExperienceService mockUiExperienceService = Mockito.mock(UiExperienceService.class);
		Mockito.when(mockUiExperienceService.getUiExperienceLevel()).thenReturn(UiExperienceLevel.DESKTOP);
		renderer.setUiExperienceService(mockUiExperienceService);

		final Map<UiExperienceLevel, String> uiExperienceMap = new HashMap<>();
		uiExperienceMap.put(UiExperienceLevel.DESKTOP, "desktop");
		renderer.setUiExperienceViewPrefixMap(uiExperienceMap);

		final ConsumedDestinationModel model = Mockito.mock(ConsumedDestinationModel.class);
		final AbstractDestinationModel destinationModel = Mockito.mock(AbstractDestinationModel.class);
		final EndpointModel endpoint = Mockito.mock(EndpointModel.class);
		Mockito.when(endpoint.getId()).thenReturn(MerchandisingaddonConstants.STRATEGY_SERVICE);
		Mockito.when(destinationModel.getEndpoint()).thenReturn(endpoint);
		final List<AbstractDestinationModel> destinations = new ArrayList<>();
		
		Mockito.when(destinationModel.getUrl()).thenReturn(SERVICE_URL_VALUE);
		destinations.add(destinationModel);
		final DestinationTargetModel targetModel = Mockito.mock(DestinationTargetModel.class);
		Mockito.when(targetModel.getDestinations()).thenReturn(destinations);

		Mockito.when(model.getDestinationTarget()).thenReturn(targetModel);
		final ConsumedDestinationLocatorStrategy locatorStrategy = Mockito.mock(ConsumedDestinationLocatorStrategy.class);
		Mockito.when(locatorStrategy.lookup(MerchandisingaddonConstants.STRATEGY_SERVICE)).thenReturn(model);

		final CurrencyModel currency = Mockito.mock(CurrencyModel.class);
		Mockito.when(currency.getSymbol()).thenReturn("£");
		Mockito.when(mockCommonI18NService.getDefaultCurrency()).thenReturn(currency);
		renderer.setConsumedDestinationLocatorStrategy(locatorStrategy);
	}

	@Test
	public void testGetVariablesToExpose() {
		final MerchandisingCarouselComponentModel carousel = new MerchandisingCarouselComponentModel();
		carousel.setUid(CAROUSEL_ID);
		final PageContext context = Mockito.mock(PageContext.class);
		try {
			final Map<String, Object> variablesToExpose = renderer.getVariablesToExpose(context, carousel);
			Assert.assertNotNull("Expected variables to expose to not be null", variablesToExpose);
			Assert.assertEquals("Expected variablesToExpose to contain component ID", CAROUSEL_ID, variablesToExpose.get(COMPONENT_ID));
			Assert.assertEquals("Expected variablesToExpose to contain service URL", SERVICE_URL_VALUE, variablesToExpose.get(SERVICE_URL));
			Assert.assertEquals("Expected variablesToExpose to contain encoded currency symbol", ENCODED_CURRENCY, variablesToExpose.get(CURRENCY));
		} catch (final Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
