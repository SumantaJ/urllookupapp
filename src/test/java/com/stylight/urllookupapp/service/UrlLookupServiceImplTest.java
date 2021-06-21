package com.stylight.urllookupapp.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.stylight.urllookupapp.domain.UrlMapperThreadLocal;
import com.stylight.urllookupapp.domain.UrlMappingDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UrlLookupServiceImplTest {

	@Autowired
	UrlLookupService urlLookupService;

	@Test
	public void getPrettyUrlsByParameterziedUrlIsOK() throws Exception {
		List<UrlMappingDetails> result = urlLookupService.findPrettyUrlByParameterziedUrl(getList("/products", null));

		Assert.assertTrue(!result.isEmpty());
	}

	@Test(expected = Exception.class)
	public void getPrettyUrlsByParameterziedUrlWhenInputEmpty() throws Exception {
		urlLookupService.findPrettyUrlByParameterziedUrl(getList(null, null));
	}

	@Test
	public void getPrettyUrlsByParameterziedUrlWhenMappingNotFound() throws Exception {
		List<UrlMappingDetails> result = urlLookupService.findPrettyUrlByParameterziedUrl(getList("/invalid", null));

		Assert.assertTrue(result.isEmpty());
		Assert.assertTrue(UrlMapperThreadLocal.getContext().getErrorMappings().size() != 0);
	}

	@Test
	public void getNearestPrettyUrlsByParameterziedUrlIsOK() throws Exception {
		List<UrlMappingDetails> result = urlLookupService
				.findPrettyUrlByParameterziedUrl(getList("/products?gender=female&tag=123&tag=1234&tag=5678", null));

		Assert.assertTrue(!result.isEmpty());
		Assert.assertTrue(result.get(0).getToUrl().equals("/Women/Shoes/?tag=5678"));
	}

	@Test
	public void getParameterziedUrlByPrettyUrlsIsOK() throws Exception {
		List<UrlMappingDetails> result = urlLookupService
				.findParameterziedUrlByPrettyUrl(getList(null, "/Women/Shoes/"));

		Assert.assertTrue(!result.isEmpty());
	}

	@Test(expected = Exception.class)
	public void getParameterziedUrlByPrettyUrlsWhenInputEmpty() throws Exception {
		urlLookupService.findParameterziedUrlByPrettyUrl(getList(null, null));
	}

	@Test
	public void getParameterziedUrlByPrettyUrlsWhenMappingNotFound() throws Exception {
		List<UrlMappingDetails> result = urlLookupService.findParameterziedUrlByPrettyUrl(getList(null, "/invalid"));

		Assert.assertTrue(result.isEmpty());
		Assert.assertTrue(UrlMapperThreadLocal.getContext().getErrorMappings().size() != 0);
	}

	private List<UrlMappingDetails> getList(String fromUrl, String toUrl) {
		List<UrlMappingDetails> testData = new ArrayList<>();
		UrlMappingDetails urlMappingDetails = null;

		if (fromUrl != null) {
			urlMappingDetails = new UrlMappingDetails();
			urlMappingDetails.setFromUrl(fromUrl);
		}

		if (toUrl != null) {
			urlMappingDetails = new UrlMappingDetails();
			urlMappingDetails.setToUrl(toUrl);
		}

		if (urlMappingDetails != null) {
			testData.add(urlMappingDetails);
		}

		return testData;
	}

}
