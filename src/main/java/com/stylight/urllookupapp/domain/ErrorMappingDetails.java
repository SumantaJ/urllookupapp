package com.stylight.urllookupapp.domain;

public class ErrorMappingDetails{
	
	private String inputUrl;
	
	public ErrorMappingDetails(String inputUrl) {
		super();
		this.inputUrl = inputUrl;
	}

	public String getInputUrl() {
		return inputUrl;
	}

	public void setInputUrl(String inputUrl) {
		this.inputUrl = inputUrl;
	}

	@Override
	public String toString() {
		return "ErrorMappingDetails [inputUrl=" + inputUrl + "]";
	}
	
}
