/**
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.hybris.merchandising.addon.component.renderer;

import java.util.Map;
import java.util.Optional;

import javax.servlet.jsp.PageContext;

import org.apache.commons.text.StringEscapeUtils;

import com.hybris.merchandising.constants.MerchandisingaddonConstants;

import de.hybris.platform.addonsupport.renderer.impl.DefaultAddOnCMSComponentRenderer;
import de.hybris.platform.apiregistryservices.model.ConsumedDestinationModel;
import de.hybris.platform.apiregistryservices.strategies.ConsumedDestinationLocatorStrategy;
import de.hybris.platform.cms2.model.contents.components.AbstractCMSComponentModel;
import de.hybris.platform.commerceservices.i18n.CommerceCommonI18NService;
import de.hybris.platform.core.model.c2l.CurrencyModel;


/**
 * MerchandisingComponentRenderer is a custom component renderer for Merch v2 CMS components.
 * This is intended to allow us to expose additional values to the page if required (e.g.
 * the component ID).
 *
 * @param <C> a Component which extends {@code AbstractCMSComponentModel}.
 */
public class MerchandisingComponentRenderer<C extends AbstractCMSComponentModel> extends DefaultAddOnCMSComponentRenderer<C> {
	public static final String COMPONENT_ID = "componentID";
	public static final String SERVICE_URL = "serviceUrl";
	public static final String CURRENCY_SYMBOL = "currency";

	private ConsumedDestinationLocatorStrategy consumedDestinationLocatorStrategy;
	private CommerceCommonI18NService commerceCommonI18NService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<String, Object> getVariablesToExpose(final PageContext pageContext, final C component)
	{
		final ConsumedDestinationModel model = consumedDestinationLocatorStrategy.lookup(MerchandisingaddonConstants.STRATEGY_SERVICE);
		final Map<String, Object> variables = super.getVariablesToExpose(pageContext, component);

		final String currency = StringEscapeUtils.escapeHtml4(
				Optional.ofNullable(
						commerceCommonI18NService.getDefaultCurrency()).map(CurrencyModel :: getSymbol).orElse(""));
		variables.put(CURRENCY_SYMBOL, currency);

		model.getDestinationTarget().getDestinations()
									.stream()
									.filter(dest -> dest.getEndpoint().getId().equals(MerchandisingaddonConstants.STRATEGY_SERVICE))
									.forEach(dest -> {
										variables.put(COMPONENT_ID, component.getUid());
										variables.put(SERVICE_URL, dest.getUrl());
									});
		return variables;
	}

	/**
	 * Sets the injected {@link ConsumedDestinationLocatorStrategy}.
	 * @param consumedDestinationLocatorStrategy the locator strategy to inject.
	 */
	public void setConsumedDestinationLocatorStrategy(
			final ConsumedDestinationLocatorStrategy consumedDestinationLocatorStrategy) {
		this.consumedDestinationLocatorStrategy = consumedDestinationLocatorStrategy;
	}

	/**
	 * Sets the injected {@link CommerceCommonI18NService} used for retrieving current configuration.
	 * @param commerceCommonI18NService the service to inject.
	 */
	public void setCommerceCommonI18NService(final CommerceCommonI18NService commerceCommonI18NService) {
		this.commerceCommonI18NService = commerceCommonI18NService;
	}
}
