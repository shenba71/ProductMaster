package com.aspire.productmaster.web.rest.controller;

import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.productmaster.feed.service.ProductMasterSearchService;
import com.aspire.productmaster.feed.service.ProductMasterStagingService;

@RestController
@RequestMapping("/product")
public class ProductMasterController {

	@Autowired
	private ProductMasterStagingService productMasterStagingService;

	@Autowired
	private ProductMasterSearchService productMasterSearchservice;

	private final Logger log = LoggerFactory.getLogger(ProductMasterController.class);

	@RequestMapping(method = RequestMethod.POST)
	public String saveInputfeed(@RequestBody String inputFeed) {
		log.debug("JSONRequest recieved!!!");
		return productMasterStagingService.saveInputFeed(URLDecoder.decode(inputFeed));
	}

	@RequestMapping(value = "/style/{styleNumber}", method = RequestMethod.GET)
	public String getInputJsonFeed(@PathVariable("styleNumber") String styleNumber,
			@RequestParam(value = "color", required = false) String color,
			@RequestParam(value = "size", required = false) String size) {
		String response = null;
		log.debug("Controller for search...");
		String searchResult = productMasterSearchservice.searchProductDetails(styleNumber, color, size);
		log.debug("Response===" + searchResult);
		if (searchResult == null && searchResult.isEmpty()) {
			response = "Style Not Found";
		} else {
			response = searchResult;
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String searchProducts(@RequestParam("styleNumbers") String[] styleNumbers) {
		log.debug("Search Multiple Styles...");
		String response = null;
		List<String> searchResults = productMasterSearchservice.searchProductDetailsbyStyles(styleNumbers);
		if (CollectionUtils.isEmpty(searchResults)) {
			response = "Styles Not Found";

		} else {
			response = searchResults.toString();
		}

		return response;
	}

}
