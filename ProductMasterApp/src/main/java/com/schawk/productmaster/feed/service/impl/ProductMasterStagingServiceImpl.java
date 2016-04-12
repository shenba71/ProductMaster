package com.schawk.productmaster.feed.service.impl;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schawk.productmaster.feed.dao.ProductMasterFeedDao;
import com.schawk.productmaster.feed.service.ProductMasterStagingService;

@Service
public class ProductMasterStagingServiceImpl implements ProductMasterStagingService {

    @Autowired
    private ProductMasterFeedDao productMasterFeedDao;

    @Override
    public String saveInputFeed(String inputFeed) {
        String response = null;
        try {
            // Converting input json to jsonArray
            JSONArray jsonArray = new JSONArray(inputFeed);
            response = productMasterFeedDao.saveInputFeed(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
