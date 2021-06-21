package com.stylight.urllookupapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stylight.urllookupapp.dao.UrlLookupRepository;
import com.stylight.urllookupapp.domain.ErrorMappingDetails;
import com.stylight.urllookupapp.domain.UrlMapperThreadLocal;
import com.stylight.urllookupapp.domain.UrlMappingDetails;
import com.stylight.urllookupapp.service.UrlLookupService;
import com.stylight.urllookupapp.utility.UrlParamRetreiverUtil;

@Service
public class UrlLookupServiceImpl implements UrlLookupService {

	public static final String EMPTY_LIST_EXCEPTION_MESSAGE = "Input list is empty, please give correct value to get the mapping";
	public static final String NO_MAPPING_FOUND_IN_DB = "No mapping data found in db, please check dataset";
	public static final String NO_MAPPING_FOUND_MESSAGE = "No mapping found for this url";
	public static final String SEPERATOR = "&";

	@Autowired
	UrlLookupRepository urlLookupRepository;

	/**
	 * This method is responsible for finding nearest possible pretty urls by parameterzied urls
	 * 
	 * @param parameterziedUrlList
	 * @return resultMappingList
	 */
	@Override
	public List<UrlMappingDetails> findPrettyUrlByParameterziedUrl(List<UrlMappingDetails> parameterziedUrlList)
			throws Exception {

		if (parameterziedUrlList.isEmpty()) {
			throw new Exception(EMPTY_LIST_EXCEPTION_MESSAGE);
		}

		List<UrlMappingDetails> urlMappingList = urlLookupRepository.findAll();
		
		if(urlMappingList.isEmpty()) {
			throw new Exception(NO_MAPPING_FOUND_IN_DB);
		}

		List<UrlMappingDetails> resultMappingList = new ArrayList<>();

		for (UrlMappingDetails url : parameterziedUrlList) {
			
			if (url.getFromUrl().contains(SEPERATOR)) {
				UrlMappingDetails result = UrlParamRetreiverUtil.extractURIAndFindBestPossibleMatch(url.getFromUrl(),
						urlMappingList);
				
				if (result == null) {
					setMappingError(url.getFromUrl());
					continue;
				}
				resultMappingList.add(result);
			} else {
				UrlMappingDetails result = UrlParamRetreiverUtil.findUrlFromList(url.getFromUrl(),
						urlMappingList);
				
				if (result == null) {
					setMappingError(url.getFromUrl());
					continue;
				}
				resultMappingList.add(result);
			}
		}
		return resultMappingList;
	}

	/**
	 * This method is responsible for finding nearest possible pretty urls by parameterzied urls
	 * 
	 * @param parameterziedUrlList
	 * @return resultMappingList
	 */
	@Override
	public List<UrlMappingDetails> findParameterziedUrlByPrettyUrl(List<UrlMappingDetails> prettyUrlList)
			throws Exception {

		if (prettyUrlList.isEmpty()) {
			throw new Exception(EMPTY_LIST_EXCEPTION_MESSAGE);
		}

		List<UrlMappingDetails> urlMappingList = urlLookupRepository.findAll();
		
		if(urlMappingList.isEmpty()) {
			throw new Exception(NO_MAPPING_FOUND_IN_DB);
		}
		
		List<UrlMappingDetails> resultMappingList = new ArrayList<>();

		for (UrlMappingDetails url : prettyUrlList) {
			
			UrlMappingDetails result = UrlParamRetreiverUtil.findUrlFromList(url.getToUrl(),
					urlMappingList);
			
			if (result == null) {
				setMappingError(url.getToUrl());
				continue;
			}
			resultMappingList.add(result);
		}
		return resultMappingList;
	}

	/* Set urls when mapping not found to show the end user */
	private void setMappingError(String url) {
		UrlMapperThreadLocal.getContext().getErrorMappings().add(new ErrorMappingDetails(url));
	}

}
