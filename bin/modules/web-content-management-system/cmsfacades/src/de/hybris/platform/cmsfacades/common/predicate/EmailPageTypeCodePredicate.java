package de.hybris.platform.cmsfacades.common.predicate;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;

import java.util.function.Predicate;

/**
 * Predicate to test if a given page type code is a Email page code.
 */
public class EmailPageTypeCodePredicate implements Predicate<String>
{
	@Override
	public boolean test(final String pageTypeCode)
	{
		return EmailPageModel._TYPECODE.equals(pageTypeCode);
	}
}