package com.stylight.urllookupapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stylight.urllookupapp.domain.Result;
import com.stylight.urllookupapp.domain.UrlMapperThreadLocal;
import com.stylight.urllookupapp.domain.UrlMappingDetails;
import com.stylight.urllookupapp.service.UrlLookupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/urllookup")
@Api(value = "URL Lookup Controller")
public class UrlLookupController {

	@Autowired
	UrlLookupService urlLookupService;

	/**
	 * This Controller takes parameterized urls and returns pretty for valid mapping
	 * 
	 * @param parameterizedUrls
	 * @return result containing parameterzied urls mapping and error list for which mapping not found
	 */
	@ApiOperation(value = "find pretty url list by parameterized urls.", response = Result.class, responseContainer = "List")
	@GetMapping(value = "/findprettyurls")
	ResponseEntity<Result> findPrettyUrlByParameterziedUrl(@RequestBody List<UrlMappingDetails> parameterizedUrls) {
		
		Result result = new Result();
		HttpStatus status = HttpStatus.OK;
		try {
			List<UrlMappingDetails> urlMappingDetails = urlLookupService
					.findPrettyUrlByParameterziedUrl(parameterizedUrls);
			
			result.setUrlMappingDetails(urlMappingDetails);
			result.setErroMappingDetails(UrlMapperThreadLocal.getContext().getErrorMappings());
			
			UrlMapperThreadLocal.clear();
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			result.setErrorMessage(e.getMessage());
		}
		return new ResponseEntity<Result>(result, status);
	}

	/**
	 * This Controller takes pretty urls and returns parameterzied urls for valid mapping
	 * 
	 * @param prettyUrls list
	 * @return result containing parameterzied urls mapping and error list for which mapping not found
	 */
	@ApiOperation(value = "find parameterized url list by pretty urls.", response = Result.class, responseContainer = "List")
	@GetMapping(value = "/findparameterizedurl")
	ResponseEntity<Result> findParameterziedUrlByPrettyUrl(@RequestBody List<UrlMappingDetails> prettyUrls) {
		
		Result result = new Result();
		HttpStatus status = HttpStatus.OK;
		try {
			List<UrlMappingDetails> urlMappingDetails = urlLookupService.findParameterziedUrlByPrettyUrl(prettyUrls);
			
			result.setUrlMappingDetails(urlMappingDetails);
			result.setErroMappingDetails(UrlMapperThreadLocal.getContext().getErrorMappings());
			
			UrlMapperThreadLocal.clear();
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			result.setErrorMessage(e.getMessage());
		}
		return new ResponseEntity<Result>(result, status);
	}
}
