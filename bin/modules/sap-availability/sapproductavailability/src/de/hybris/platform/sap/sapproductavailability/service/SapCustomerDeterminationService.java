/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package de.hybris.platform.sap.sapproductavailability.service;

/**
 * Interface for sap customer Determination
 */
public interface SapCustomerDeterminationService {

	/**
	 * This method reads SapCustomerId
	 * 
	 * @return SapCustomerId in String value
	 */
	public String readSapCustomerID();

}
