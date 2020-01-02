/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN!
 * --- Generated at 2 ene. 2020 14:28:40
 * ----------------------------------------------------------------
 *
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.processengine.adminapi;

import java.io.Serializable;

public  class TriggerEventRequest  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>TriggerEventRequest.event</code> property defined at extension <code>processing</code>. */
		
	private String event;

	/** <i>Generated property</i> for <code>TriggerEventRequest.choice</code> property defined at extension <code>processing</code>. */
		
	private String choice;
	
	public TriggerEventRequest()
	{
		// default constructor
	}
	
	public void setEvent(final String event)
	{
		this.event = event;
	}

	public String getEvent() 
	{
		return event;
	}
	
	public void setChoice(final String choice)
	{
		this.choice = choice;
	}

	public String getChoice() 
	{
		return choice;
	}
	


}
