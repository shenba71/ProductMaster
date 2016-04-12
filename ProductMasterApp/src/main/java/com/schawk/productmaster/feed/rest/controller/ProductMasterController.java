package com.schawk.productmaster.feed.rest.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.schawk.productmaster.feed.service.ProductMasterSearchService;
import com.schawk.productmaster.feed.service.ProductMasterStagingService;

@RestController
@RequestMapping("/product")
public class ProductMasterController {

    @Autowired
    private ProductMasterStagingService productMasterStagingService;

    @Autowired
    private ProductMasterSearchService productMasterSearchservice;

    private static final Logger LOG = LoggerFactory.getLogger(ProductMasterController.class);

    @RequestMapping(method = RequestMethod.POST)
    public String saveInputfeed(@RequestBody String inputFeed) {
        LOG.debug("JSONRequest recieved!!!");
        return productMasterStagingService.saveInputFeed(inputFeed);
    }

    @RequestMapping(value = "/style/{styleNumber}", method = RequestMethod.GET)
    public String getInputJsonFeed(@PathVariable("styleNumber") String styleNumber,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "size", required = false) String size) {

        LOG.debug("Controller for search");
        String searchResult = productMasterSearchservice.searchProductDetails(styleNumber, color,
                size);
        LOG.debug("Response :" + searchResult);
        return searchResult;
    }

    @RequestMapping(value = "/styles", method = RequestMethod.POST)
    public String searchProducts(@RequestBody String styleNumbers) {
        LOG.debug("Search Multiple Styles...");
        String response = null;
        List<String> searchResults = productMasterSearchservice
                .searchProductDetailsbyStyles(styleNumbers.split(","));
        if (CollectionUtils.isEmpty(searchResults)) {
            response = "Styles Not Found";

        } else {
            response = searchResults.toString();
        }

        return response;
    }

}
