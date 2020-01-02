/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.consignmenttrackingmock.controller.pages;

import de.hybris.platform.consignmenttrackingmock.controller.ConsignmenttrackingmockControllerConstants;
import de.hybris.platform.consignmenttrackingmock.data.MockDataProvider;
import de.hybris.platform.consignmenttrackingmock.service.impl.MockConsignmentTrackingService;
import de.hybris.platform.consignmenttrackingservices.service.ConsignmentTrackingService;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 */
@Controller
@RequestMapping("/tracking/mock")
public class MockCarrierController
{

	@Resource(name = "consignmentTrackingService")
	private ConsignmentTrackingService consignmentTrackingService;

	@Resource(name = "mockDataProvider")
	private MockDataProvider mockDataProvider;

	@Resource(name = "carrier")
	private String carrier;

	@Resource(name = "mockConsignmentTrackingService")
	private MockConsignmentTrackingService mockConsignmentTrackingService;

	private static final String[] DISALLOWED_FIELDS = new String[] {};

	@InitBinder
	public void initBinder(final WebDataBinder binder)
	{
		binder.setDisallowedFields(DISALLOWED_FIELDS);
	}

	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public String redirectToMockCarrier() 
	{
		return ConsignmenttrackingmockControllerConstants.Pages.MOCKCARRIERPAGE;
	}
}
