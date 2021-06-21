package com.stylight.urllookupapp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorMessage;
	private List<UrlMappingDetails> urlMappingDetails = new ArrayList<>();
	private List<ErrorMappingDetails> erroMappingDetails = new ArrayList<>();

	public List<UrlMappingDetails> getUrlMappingDetails() {
		return urlMappingDetails;
	}

	public void setUrlMappingDetails(List<UrlMappingDetails> urlMappingDetails) {
		this.urlMappingDetails.addAll(urlMappingDetails);
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<ErrorMappingDetails> getErroMappingDetails() {
		return erroMappingDetails;
	}

	public void setErroMappingDetails(List<ErrorMappingDetails> erroMappingDetails) {
		this.erroMappingDetails.addAll(erroMappingDetails);
	}

}
