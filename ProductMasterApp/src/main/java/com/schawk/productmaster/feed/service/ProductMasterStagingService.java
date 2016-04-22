package com.schawk.productmaster.feed.service;

import java.util.Map;

/**
 * @author shenbagaganesh.param
 *
 */
public interface ProductMasterStagingService {

    public String saveInputFeed(String inputFeed);

    public String saveStyleDatasToProductMetaData(Map<String, String> productMap) throws Exception;

    public String saveStyleDatasToProductMetaData(String productJson) throws Exception;

    public String saveColorDatasToProductMetadata(Map<String, String> valueMap, String styleNumber)
            throws Exception;

    public String saveSizeDatasToProductMetadata(Map<String, String> valueMap, String styleNumber,
            String colorNumber) throws Exception;

    public String updateColorDatasToProductMetadata(Map<String, String> valueMap,
            String styleNumber, String colorNumber) throws Exception;

    public String updateSizeDatasToProductMetadata(Map<String, String> valueMap, String styleNumber,
            String colorNumber, String sizeCode) throws Exception;

    public String saveColorDatasToProductMetadata(String colorData, String styleNumber)
            throws Exception;

    public String saveSizeDatasToProductMetadata(String sizeDataJson, String styleNumber,
            String colorNumber) throws Exception;

    public String updateStyleDatasToProductMetaData(Map<String, String> productMap)
            throws Exception;
}
