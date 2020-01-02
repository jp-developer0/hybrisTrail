/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN!
 * --- Generated at 2 ene. 2020 14:28:40
 * ----------------------------------------------------------------
 *
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package concerttours.data;

import java.io.Serializable;
import concerttours.data.ConcertSummaryData;
import java.util.List;

/**
 * Data object representing a tour
 */
public  class TourData  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>TourData.id</code> property defined at extension <code>concerttours</code>. */
		
	private String id;

	/** <i>Generated property</i> for <code>TourData.tourName</code> property defined at extension <code>concerttours</code>. */
		
	private String tourName;

	/** <i>Generated property</i> for <code>TourData.description</code> property defined at extension <code>concerttours</code>. */
		
	private String description;

	/** <i>Generated property</i> for <code>TourData.concerts</code> property defined at extension <code>concerttours</code>. */
		
	private List<ConcertSummaryData> concerts;
	
	public TourData()
	{
		// default constructor
	}
	
	public void setId(final String id)
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	
	public void setTourName(final String tourName)
	{
		this.tourName = tourName;
	}

	public String getTourName() 
	{
		return tourName;
	}
	
	public void setDescription(final String description)
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}
	
	public void setConcerts(final List<ConcertSummaryData> concerts)
	{
		this.concerts = concerts;
	}

	public List<ConcertSummaryData> getConcerts() 
	{
		return concerts;
	}
	


}
