/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN!
 * --- Generated at 2 ene. 2020 14:28:40
 * ----------------------------------------------------------------
 *
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.servicelayer.event.events;

import java.io.Serializable;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.event.events.AbstractCronJobPerformEvent;

public  class AfterCronJobFinishedEvent extends AbstractCronJobPerformEvent {


	/** <i>Generated property</i> for <code>AfterCronJobFinishedEvent.result</code> property defined at extension <code>processing</code>. */
		
	private CronJobResult result;

	/** <i>Generated property</i> for <code>AfterCronJobFinishedEvent.status</code> property defined at extension <code>processing</code>. */
		
	private CronJobStatus status;
	
	public AfterCronJobFinishedEvent()
	{
		super();
	}

	public AfterCronJobFinishedEvent(final Serializable source)
	{
		super(source);
	}
	
	public void setResult(final CronJobResult result)
	{
		this.result = result;
	}

	public CronJobResult getResult() 
	{
		return result;
	}
	
	public void setStatus(final CronJobStatus status)
	{
		this.status = status;
	}

	public CronJobStatus getStatus() 
	{
		return status;
	}
	


}
