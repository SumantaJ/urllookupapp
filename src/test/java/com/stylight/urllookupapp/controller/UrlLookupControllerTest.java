package com.stylight.urllookupapp.controller;

import static com.stylight.urllookupapp.service.impl.UrlLookupServiceImpl.EMPTY_LIST_EXCEPTION_MESSAGE;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class UrlLookupControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	
	@Test
	public void returnCorrectResultForFindingPrettyUrlByParamaterziedUrl() throws Exception {
		String body = "[{ \"fromUrl\":\"/products\" }]";
		mockMvc.perform(get("/urllookup/findprettyurls")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON))
		       	.andExpect(status().is2xxSuccessful())
       			.andExpect(jsonPath("$.urlMappingDetails[*].toUrl", contains("/Fashion/")));
	}
	
	@Test
	public void returnCorrectResultForFindingParamaterziedUrlByPrettyUrl() throws Exception {
		String body = "[{ \"toUrl\":\"/Fashion/\" }]";
		mockMvc.perform(get("/urllookup/findparameterizedurl")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON))
		       	.andExpect(status().is2xxSuccessful())
       			.andExpect(jsonPath("$.urlMappingDetails[*].fromUrl", contains("/products")));
	}
	
	@Test
	public void returnNearestPossibleResultForNotExactMatchOfParameterziedUrl() throws Exception {
		String body = "[{ \"fromUrl\":\"/products?gender=female&tag=123&tag=1234&tag=5678\" }]";
		mockMvc.perform(get("/urllookup/findprettyurls")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON))
		       	.andExpect(status().is2xxSuccessful())
       			.andExpect(jsonPath("$.urlMappingDetails[*].toUrl", contains("/Women/Shoes/?tag=5678")));
	}
	
	@Test
	public void returnErrorDetailsWhenNoMappingFound() throws Exception {
		String body = "[{ \"fromUrl\":\"/invalid\" }]";
		mockMvc.perform(get("/urllookup/findprettyurls")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON))
		       	.andExpect(status().is2xxSuccessful())
       			.andExpect(jsonPath("$.erroMappingDetails[*].inputUrl", contains("/invalid")));
	}
	
	@Test
	public void returnSucessfullFalseWithErrorMessageWhenPrettyUrlListIsEmpty() throws Exception {
		String body = "[]";
		mockMvc.perform(get("/urllookup/findparameterizedurl")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON))
		       	.andExpect(status().is4xxClientError())
       			.andExpect(jsonPath("$.errorMessage", containsString(EMPTY_LIST_EXCEPTION_MESSAGE)));
	}
	
	@Test
	public void returnSucessfullFalseWithErrorMessageWhenParameterziedUrlListIsEmpty() throws Exception {
		String body = "[]";
		mockMvc.perform(get("/urllookup/findprettyurls")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON))
		       	.andExpect(status().is4xxClientError())
       			.andExpect(jsonPath("$.errorMessage", containsString(EMPTY_LIST_EXCEPTION_MESSAGE)));
	}

}
