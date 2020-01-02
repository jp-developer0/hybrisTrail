/**
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.hybris.merchandising.exporter;

/**
 * MerchCategoryExporter is a class to handle the export of category information from the
 * Hybris product catalog for import into Merch v2.
 *
 */
public interface MerchCategoryExporter {
	/**
	 * exportCategories is a method for retrieving the category hierarchy and
	 * invoking a call to Merch v2 to persist the hierarchy.
	 */
	void exportCategories();

	/**
	 * exportCategoriesForCurrentBaseSite is a method for retrieving the category hierarchy and
	 * invoking a call to Merch v2 to persist the hierarchy.
	 */
	void exportCategoriesForCurrentBaseSite();
}
