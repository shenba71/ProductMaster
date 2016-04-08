package com.aspire.productmaster.feed.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.productmaster.feed.dao.ProductMasterFeedDao;
import com.aspire.productmaster.feed.dao.impl.ProductMasterFeedDaoImpl;
import com.aspire.productmaster.feed.service.ProductMasterSearchService;

@Service
public class ProductMasterSearchServiceImpl implements ProductMasterSearchService {

	private final Logger log = LoggerFactory.getLogger(ProductMasterFeedDaoImpl.class);
	@Autowired
	private ProductMasterFeedDao productMasterFeedDao;

	@Override
	public String searchProductDetails(String style, String color, String size) {
		String jsonOutput = null;
		try {
			log.debug("Search material with style" + style + "color===" + color + "size===" + size);
			jsonOutput = productMasterFeedDao.searchFeed(style, color, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonOutput;
	}

	@Override
	public List<String> searchProductDetailsbyStyles(String[] styleNumbers) {
		List<String> jsonOutputs = null;
		try {
			jsonOutputs = productMasterFeedDao.searchFeedByStyles(styleNumbers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonOutputs;
	}

}
