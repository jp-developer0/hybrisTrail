/**
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.hybris.merchandising.service;

import java.util.List;

import com.hybris.platform.merchandising.yaas.CategoryHierarchy;

/**
 * MerchCatalogService is a service for the purpose of making catalog queries.
 *
 */
public interface MerchCatalogService {
	/**
	 * getCategories is a method for retrieving the category hierarchy (including subcategories).
	 * @param baseSite the base site we wish to retrieve the category hierarchy for.
	 * @param catalog  the catalog we wish to retrieve the category hierarchy from.
	 * @param catalogVersion the catalog version we wish to retrieve the category hierarchy for.
	 * @param baseCategoryUrl the URL we wish to use to access the category from.
	 * @return a List of {@link CategoryHierarchy} representing the categories.
	 */
	List<CategoryHierarchy> getCategories(String baseSite, String catalog, String catalogVersion, String baseCategoryUrl);
}
