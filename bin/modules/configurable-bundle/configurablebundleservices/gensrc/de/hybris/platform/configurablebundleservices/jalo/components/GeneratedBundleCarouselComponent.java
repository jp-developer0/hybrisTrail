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
package de.hybris.platform.configurablebundleservices.jalo.components;

import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.configurablebundleservices.constants.ConfigurableBundleServicesConstants;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.configurablebundleservices.jalo.components.BundleCarouselComponent BundleCarouselComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedBundleCarouselComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>BundleCarouselComponent.productCode</code> attribute **/
	public static final String PRODUCTCODE = "productCode";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PRODUCTCODE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BundleCarouselComponent.productCode</code> attribute.
	 * @return the productCode
	 */
	public String getProductCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BundleCarouselComponent.productCode</code> attribute.
	 * @return the productCode
	 */
	public String getProductCode()
	{
		return getProductCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BundleCarouselComponent.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BundleCarouselComponent.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final String value)
	{
		setProductCode( getSession().getSessionContext(), value );
	}
	
}
