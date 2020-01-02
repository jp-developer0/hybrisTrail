/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 24 dic. 2019 12:20:12                       ---
 * ----------------------------------------------------------------
 *  
 * [y] hybris Platform
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package de.hybris.platform.b2bacceleratorservices.jalo.process;

import de.hybris.platform.b2bacceleratorservices.constants.B2BAcceleratorServicesConstants;
import de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.orderscheduling.jalo.CartToOrderCronJob;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.b2bacceleratorservices.jalo.process.ReplenishmentProcess ReplenishmentProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedReplenishmentProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>ReplenishmentProcess.cartToOrderCronJob</code> attribute **/
	public static final String CARTTOORDERCRONJOB = "cartToOrderCronJob";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CARTTOORDERCRONJOB, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ReplenishmentProcess.cartToOrderCronJob</code> attribute.
	 * @return the cartToOrderCronJob - Attribute contains cronjob that knows how to create an order from cart.
	 */
	public CartToOrderCronJob getCartToOrderCronJob(final SessionContext ctx)
	{
		return (CartToOrderCronJob)getProperty( ctx, CARTTOORDERCRONJOB);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ReplenishmentProcess.cartToOrderCronJob</code> attribute.
	 * @return the cartToOrderCronJob - Attribute contains cronjob that knows how to create an order from cart.
	 */
	public CartToOrderCronJob getCartToOrderCronJob()
	{
		return getCartToOrderCronJob( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ReplenishmentProcess.cartToOrderCronJob</code> attribute. 
	 * @param value the cartToOrderCronJob - Attribute contains cronjob that knows how to create an order from cart.
	 */
	public void setCartToOrderCronJob(final SessionContext ctx, final CartToOrderCronJob value)
	{
		setProperty(ctx, CARTTOORDERCRONJOB,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ReplenishmentProcess.cartToOrderCronJob</code> attribute. 
	 * @param value the cartToOrderCronJob - Attribute contains cronjob that knows how to create an order from cart.
	 */
	public void setCartToOrderCronJob(final CartToOrderCronJob value)
	{
		setCartToOrderCronJob( getSession().getSessionContext(), value );
	}
	
}
