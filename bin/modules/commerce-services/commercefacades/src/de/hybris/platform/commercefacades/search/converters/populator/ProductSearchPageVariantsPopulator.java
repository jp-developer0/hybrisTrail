/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.commercefacades.search.converters.populator;

import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;


public class ProductSearchPageVariantsPopulator<ITEM extends ProductData> implements Populator<Object, SearchPageData<ITEM>>
{
	private ProductFacade productVariantFacade;

	private ProductService productService;

	private ConfigurablePopulator<ProductModel, ITEM, ProductOption> productConfiguredPopulator;

	private Collection<ProductOption> productSearchOptions;

	@Override
	public void populate(final Object source, final SearchPageData<ITEM> target) throws ConversionException
	{
		if (target != null && CollectionUtils.isNotEmpty(target.getResults()))
		{
			for (final ITEM productData : target.getResults())
			{
				final ProductModel productModel = productService.getProductForCode(productData.getCode());
				productConfiguredPopulator.populate(productModel, productData, productSearchOptions);
			}
		}
	}


	@Required
	public void setProductVariantFacade(final ProductFacade productVariantFacade)
	{
		this.productVariantFacade = productVariantFacade;
	}

	@Required
	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}


	@Required
	public void setProductConfiguredPopulator(
			final ConfigurablePopulator<ProductModel, ITEM, ProductOption> productConfiguredPopulator)
	{
		this.productConfiguredPopulator = productConfiguredPopulator;
	}

	protected Collection<ProductOption> getProductSearchOptions()
	{
		return productSearchOptions;
	}

	@Required
	public void setProductSearchOptions(final Collection<ProductOption> productSearchOptions)
	{
		this.productSearchOptions = productSearchOptions;
	}



}
