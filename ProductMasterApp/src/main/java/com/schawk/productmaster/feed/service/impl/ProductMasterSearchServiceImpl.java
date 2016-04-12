package com.schawk.productmaster.feed.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schawk.productmaster.feed.dao.ProductMasterFeedDao;
import com.schawk.productmaster.feed.dao.impl.ProductMasterFeedDaoImpl;
import com.schawk.productmaster.feed.service.ProductMasterSearchService;

@Service
public class ProductMasterSearchServiceImpl implements ProductMasterSearchService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductMasterFeedDaoImpl.class);
    @Autowired
    private ProductMasterFeedDao productMasterFeedDao;

    @Override
    public String searchProductDetails(String style, String color, String size) {
        String searchResult = null;
        try {
            LOG.debug("Search material with style" + style + "color===" + color + "size===" + size);
            searchResult = productMasterFeedDao.searchFeed(style, color, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    @Override
    public List<String> searchProductDetailsbyStyles(String[] styleNumbers) {
        List<String> searchResults = null;
        try {
            searchResults = productMasterFeedDao.searchFeedByStyles(styleNumbers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchResults;
    }

}
