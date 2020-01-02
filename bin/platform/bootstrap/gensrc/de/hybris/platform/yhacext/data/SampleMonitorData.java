/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN!
 * --- Generated at 2 ene. 2020 14:28:40
 * ----------------------------------------------------------------
 *
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.yhacext.data;

import java.io.Serializable;
import de.hybris.platform.yhacext.data.SampleMonitorResultData;
import java.util.List;

public  class SampleMonitorData  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>SampleMonitorData.results</code> property defined at extension <code>yhacext</code>. */
		
	private List<SampleMonitorResultData> results;
	
	public SampleMonitorData()
	{
		// default constructor
	}
	
	public void setResults(final List<SampleMonitorResultData> results)
	{
		this.results = results;
	}

	public List<SampleMonitorResultData> getResults() 
	{
		return results;
	}
	


}
