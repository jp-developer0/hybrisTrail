/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN!
 * --- Generated at 2 ene. 2020 14:28:40
 * ----------------------------------------------------------------
 *
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.catalog.data;

import java.io.Serializable;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import java.util.Map;

public  class CatalogVersionOverview  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>CatalogVersionOverview.catalogVersion</code> property defined at extension <code>catalog</code>. */
		
	private CatalogVersionModel catalogVersion;

	/** <i>Generated property</i> for <code>CatalogVersionOverview.typeAmounts</code> property defined at extension <code>catalog</code>. */
		
	private Map<ComposedTypeModel, Long> typeAmounts;
	
	public CatalogVersionOverview()
	{
		// default constructor
	}
	
	public void setCatalogVersion(final CatalogVersionModel catalogVersion)
	{
		this.catalogVersion = catalogVersion;
	}

	public CatalogVersionModel getCatalogVersion() 
	{
		return catalogVersion;
	}
	
	public void setTypeAmounts(final Map<ComposedTypeModel, Long> typeAmounts)
	{
		this.typeAmounts = typeAmounts;
	}

	public Map<ComposedTypeModel, Long> getTypeAmounts() 
	{
		return typeAmounts;
	}
	


}
