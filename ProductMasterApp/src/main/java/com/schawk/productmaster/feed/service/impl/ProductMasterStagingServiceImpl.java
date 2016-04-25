package com.schawk.productmaster.feed.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schawk.productmaster.feed.dao.ProductMasterFeedDao;
import com.schawk.productmaster.feed.service.ProductMasterStagingService;

/**
 * @author shenbagaganesh.param
 *
 */
@Service
public class ProductMasterStagingServiceImpl implements ProductMasterStagingService {

    @Autowired
    private ProductMasterFeedDao productMasterFeedDao;

    private static final Logger LOG = LoggerFactory
            .getLogger(ProductMasterStagingServiceImpl.class);

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

    @Override
    public String saveStyleDataToProductMetaData(Map<String, String> productMap) throws Exception {
        String productInputJson = convertMapToJson(productMap);
        LOG.debug("Request in JSON format.." + productInputJson);
        return productMasterFeedDao.saveProductMetaDataStyle(productInputJson);
    }

    @Override
    public String updateStyleDataToProductMetaData(Map<String, String> productMap)
            throws Exception {
        String productInputJson = convertMapToJson(productMap);
        LOG.debug("Request in JSON format.." + productInputJson);
        return productMasterFeedDao.updateProductMetaDataStyle(productInputJson);
    }

    @Override
    public String saveStyleDataToProductMetaData(String productJson) throws Exception {
        return productMasterFeedDao.saveProductMetaDataStyle(productJson);
    }

    @Override
    public String saveColorDataToProductMetadata(Map<String, String> valueMap, String styleNumber)
            throws Exception {
        String colorMetadataJson = convertMapToJson(valueMap);
        LOG.debug("Request in JSON format.." + colorMetadataJson);
        return productMasterFeedDao.saveColorMetaData(colorMetadataJson, styleNumber);

    }

    @Override
    public String saveColorDataToProductMetadata(String colorData, String styleNumber)
            throws Exception {
        return productMasterFeedDao.saveColorMetaData(colorData, styleNumber);

    }

    @Override
    public String saveSizeDataToProductMetadata(Map<String, String> valueMap, String styleNumber,
            String colorNumber) throws Exception {
        String sizeMetadataJson = convertMapToJson(valueMap);
        LOG.debug("Request in JSON format.." + sizeMetadataJson);
        return productMasterFeedDao.saveSizeMetaData(sizeMetadataJson, styleNumber, colorNumber);

    }

    @Override
    public String saveSizeDataToProductMetadata(String sizeDataJson, String styleNumber,
            String colorNumber) throws Exception {
        return productMasterFeedDao.saveSizeMetaData(sizeDataJson, styleNumber, colorNumber);

    }

    /**
     * @param productMap
     * @return the input map recieved as JSON
     */
    private String convertMapToJson(Map<String, String> productMap) {
        String productMetaDataJson = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            productMetaDataJson = mapper.writeValueAsString(productMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productMetaDataJson;
    }

    /**
     * @param valuMap
     * @param type
     * @param pos
     * @return the json with positional operator for updating color and size
     */
    private String convertMapToJsonForUpdate(Map<String, String> valuMap, String type, int pos) {
        Map<String, String> updatedMap = new HashMap<String, String>();
        Set<String> keySet = valuMap.keySet();
        for (String object : keySet) {
            if (("color").equalsIgnoreCase(type)) {
                // key value for color metadata with positional operator
                updatedMap.put("colors.$.color." + object, (String) valuMap.get(object));
            } else {
                // key value for size metadata with positional operator and
                // index of size record to update
                updatedMap.put("colors.$.color.sizes." + pos + ".size." + object,
                        (String) valuMap.get(object));
            }
        }
        return convertMapToJson(updatedMap);

    }

    @Override
    public String updateColorDataToProductMetadata(Map<String, String> valueMap, String styleNumber,
            String colorNumber) throws Exception {
        String type = "color";
        String response = null;
        String updatedColorData = null;
        int pos = 0;
        if (productMasterFeedDao.findProductByStyleAndColor(styleNumber, colorNumber)
                .equalsIgnoreCase("NO RECORDS FOUND FOR GIVEN STYLE AND COLOR")) {
            valueMap.put("colorCode", colorNumber);
            updatedColorData = convertMapToJson(valueMap);
            LOG.debug("Update Request in JSON format.." + updatedColorData);
            response = productMasterFeedDao.saveColorMetaData(updatedColorData, styleNumber);
        } else {
            updatedColorData = convertMapToJsonForUpdate(valueMap, type, pos);
            LOG.debug("Update Request in JSON format.." + updatedColorData);
            response = productMasterFeedDao.updateColorMetaData(updatedColorData, styleNumber,
                    colorNumber);
        }

        return response;
    }

    @Override
    public String updateSizeDataToProductMetadata(Map<String, String> valueMap, String styleNumber,
            String colorNumber, String sizeCode) throws Exception {
        String type = "size";
        String response = null;
        String updatedSizeDatas = null;
        if (("No Product record found for given style, color and size")
                .equalsIgnoreCase(productMasterFeedDao.findProductByStyleColorAndSize(styleNumber,
                        colorNumber, sizeCode))) {
            valueMap.put("sizeCode", sizeCode);
            updatedSizeDatas = convertMapToJson(valueMap);
            LOG.debug("Update Request in JSON format.." + updatedSizeDatas);
            response = productMasterFeedDao.saveSizeMetaData(updatedSizeDatas, styleNumber,
                    colorNumber);
        } else {
            int pos = productMasterFeedDao.getIndexForSize(styleNumber, colorNumber, sizeCode);
            updatedSizeDatas = convertMapToJsonForUpdate(valueMap, type, pos);
            LOG.debug("Update Request in JSON format.." + updatedSizeDatas);
            response = productMasterFeedDao.updateSizeMetaData(updatedSizeDatas, styleNumber,
                    colorNumber, sizeCode);
        }

        return response;
    }

}
