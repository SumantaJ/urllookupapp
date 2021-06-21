package com.stylight.urllookupapp.utility;

import java.util.List;
import java.util.Optional;

import com.stylight.urllookupapp.domain.UrlMappingDetails;

public class UrlParamRetreiverUtil {

	public static final Character SEPERATOR = '&';
	public static final Character QUESTION_MARK = '?';
	public static final int KEY = 1;
	private static final StringBuilder leftOverRequestparamList = new StringBuilder();

	/** This method breaks parts of the url to check the nearest possible match */
	public static UrlMappingDetails extractURIAndFindBestPossibleMatch(String url,
			List<UrlMappingDetails> urlMappingList) {

		UrlMappingDetails urlObject = null;
		int lastIndexAmp = url.lastIndexOf(SEPERATOR);
		if (lastIndexAmp != -1) {
			String extractedString = url.substring(0, lastIndexAmp);
			String leftOverRequestParam = url.substring(lastIndexAmp + 1, url.length());

			urlObject = findUrlFromList(extractedString, urlMappingList);

			if (urlObject != null) {
				setFinalUrl(urlObject, leftOverRequestParam);
				return urlObject;
			} else {
				leftOverRequestparamList.append(leftOverRequestParam);
				urlObject = extractURIAndFindBestPossibleMatch(extractedString, urlMappingList);
			}
		}
		leftOverRequestparamList.setLength(0);
		return urlObject;
	}

	/** This method is to set the final url for nearest possible match */
	private static void setFinalUrl(UrlMappingDetails urlObject, String leftOverRequestParam) {
		if (leftOverRequestparamList.length() != 0) {
			urlObject.setToUrl(urlObject.getToUrl(),
					QUESTION_MARK + leftOverRequestParam + SEPERATOR + leftOverRequestparamList.toString());
		} else {
			urlObject.setToUrl(urlObject.getToUrl(), QUESTION_MARK + leftOverRequestParam);
		}
	}

	/** This method looks for the input url in all list to get the mapping */
	public static UrlMappingDetails findUrlFromList(String search, List<UrlMappingDetails> list) {

		Optional<UrlMappingDetails> matchingElements = list.stream()
				.filter(str -> str.toString().trim().contains(search)).findFirst();

		return matchingElements.isPresent() ? matchingElements.get() : null;
	}

}
