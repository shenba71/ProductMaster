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

    /*
     * (non-Javadoc)
     * 
     * @see com.schawk.productmaster.feed.service.ProductMasterStagingService#
     * saveStyleDatasToProductMetaData(java.util.Map)
     * 
     * recieves style metedata details as map and then converts that into json
     * and pass it to dao layer
     */
    @Override
    public String saveStyleDatasToProductMetaData(Map<String, String> productMap) throws Exception {
        String productInputJson = convertMapToJson(productMap);
        LOG.debug("Request in JSON format.." + productInputJson);
        return productMasterFeedDao.saveProductMetaDataStyle(productInputJson);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.schawk.productmaster.feed.service.ProductMasterStagingService#
     * updateStyleDatasToProductMetaData(java.util.Map)
     * 
     * recieves style metedata details, styleNumber to update as map and then
     * converts that into json and pass it to dao layer
     */
    @Override
    public String updateStyleDatasToProductMetaData(Map<String, String> productMap)
            throws Exception {
        String productInputJson = convertMapToJson(productMap);
        LOG.debug("Request in JSON format.." + productInputJson);
        return productMasterFeedDao.updateProductMetaDataStyle(productInputJson);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.schawk.productmaster.feed.service.ProductMasterStagingService#
     * saveStyleDatasToProductMetaData(java.lang.String)
     * 
     * recieves style metedata details as JSON and pass it to dao layer
     */
    @Override
    public String saveStyleDatasToProductMetaData(String productJson) throws Exception {
        return productMasterFeedDao.saveProductMetaDataStyle(productJson);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.schawk.productmaster.feed.service.ProductMasterStagingService#
     * saveColorDatasToProductMetadata(java.util.Map, java.lang.String)
     * 
     * recieves color metedata details as map , styleNumber and then converts
     * map into json and pass it to dao layer
     */
    @Override
    public String saveColorDatasToProductMetadata(Map<String, String> valueMap, String styleNumber)
            throws Exception {
        String colorMetadataJson = convertMapToJson(valueMap);
        LOG.debug("Request in JSON format.." + colorMetadataJson);
        return productMasterFeedDao.saveColorMetaData(colorMetadataJson, styleNumber);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.schawk.productmaster.feed.service.ProductMasterStagingService#
     * saveColorDatasToProductMetadata(java.lang.String, java.lang.String)
     * 
     * recieves color metedata details as JSON , styleNumber and pass it to dao
     * layer
     */
    @Override
    public String saveColorDatasToProductMetadata(String colorData, String styleNumber)
            throws Exception {
        return productMasterFeedDao.saveColorMetaData(colorData, styleNumber);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.schawk.productmaster.feed.service.ProductMasterStagingService#
     * saveSizeDatasToProductMetadata(java.util.Map, java.lang.String,
     * java.lang.String)
     * 
     * recieves size metedata details as map , styleNumber, colorNumber and then
     * converts map into json and pass it to dao layer
     */
    @Override
    public String saveSizeDatasToProductMetadata(Map<String, String> valueMap, String styleNumber,
            String colorNumber) throws Exception {
        String sizeMetadataJson = convertMapToJson(valueMap);
        LOG.debug("Request in JSON format.." + sizeMetadataJson);
        return productMasterFeedDao.saveSizeMetaData(sizeMetadataJson, styleNumber, colorNumber);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.schawk.productmaster.feed.service.ProductMasterStagingService#
     * saveSizeDatasToProductMetadata(java.lang.String, java.lang.String,
     * java.lang.String)
     * 
     * recieves color metedata details as JSON , styleNumber, colorNumber and
     * then converts map into json and pass it to dao layer
     */
    @Override
    public String saveSizeDatasToProductMetadata(String sizeDataJson, String styleNumber,
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
            if (type.equalsIgnoreCase("color")) {
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

    /*
     * (non-Javadoc)
     * 
     * @see com.schawk.productmaster.feed.service.ProductMasterStagingService#
     * updateColorDatasToProductMetadata(java.util.Map, java.lang.String,
     * java.lang.String)
     * 
     * recieves color metedata details as map , styleNumber, colorNumber and
     * pass it to dao layer based on the record.
     * 
     * If color record already present, then update else insert.
     */
    @Override
    public String updateColorDatasToProductMetadata(Map<String, String> valueMap,
            String styleNumber, String colorNumber) throws Exception {
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

    /*
     * (non-Javadoc)
     * 
     * @see com.schawk.productmaster.feed.service.ProductMasterStagingService#
     * updateSizeDatasToProductMetadata(java.util.Map, java.lang.String,
     * java.lang.String, java.lang.String)
     * 
     * recieves size metedata details as map , styleNumber, colorNumber and pass
     * it to dao layer based on the record.
     * 
     * If size record present then update , else insert.
     */
    @Override
    public String updateSizeDatasToProductMetadata(Map<String, String> valueMap, String styleNumber,
            String colorNumber, String sizeCode) throws Exception {
        String type = "size";
        String response = null;
        String updatedSizeDatas = null;
        if (productMasterFeedDao.searchSizeRecord(styleNumber, colorNumber, sizeCode)
                .equalsIgnoreCase("NO SIZE RECORDS")) {
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
