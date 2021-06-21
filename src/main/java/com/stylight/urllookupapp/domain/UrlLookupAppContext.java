package com.stylight.urllookupapp.domain;

import java.util.ArrayList;
import java.util.List;

public class UrlLookupAppContext {

	/** This is to hold input urls for which no mapping is being found in db */
	private final List<ErrorMappingDetails> errorMappings = new ArrayList<>();

	public List<ErrorMappingDetails> getErrorMappings() {
		return errorMappings;
	}
}
