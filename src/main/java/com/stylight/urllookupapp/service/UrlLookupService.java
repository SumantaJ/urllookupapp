package com.stylight.urllookupapp.service;

import java.util.List;

import com.stylight.urllookupapp.domain.UrlMappingDetails;

public interface UrlLookupService {

	List<UrlMappingDetails> findPrettyUrlByParameterziedUrl(List<UrlMappingDetails> parameterziedUrlList)
			throws Exception;

	List<UrlMappingDetails> findParameterziedUrlByPrettyUrl(List<UrlMappingDetails> prettyUrlList) throws Exception;

}
