package com.schawk.productmaster.feed.service;

import java.util.Map;

/**
 * @author shenbagaganesh.param
 *
 */
public interface ProductMasterStagingService {

    public String saveInputFeed(String inputFeed);

    /**
     * @param productMap
     * @return the inserted record as response
     * @throws Exception
     * 
     * recieves style metadata as request params and converts that
     * into json and then pass it to DAO
     */
    public String saveStyleDataToProductMetaData(Map<String, String> productMap) throws Exception;

    /**
     * @param productJson
     * @return the inserted record as response
     * @throws Exception
     * 
     * recieves style metadata as json and then pass it to DAO
     */
    public String saveStyleDataToProductMetaData(String productJson) throws Exception;

    /**
     * @param valueMap
     * @param styleNumber
     * @return the inserted record as response
     * @throws Exception
     * 
     * recieves color metadata as request params and converts that
     *into json and then pass it to DAO
     */
    public String saveColorDataToProductMetadata(Map<String, String> valueMap, String styleNumber)
            throws Exception;

    /**
     * @param valueMap
     * @param styleNumber
     * @param colorNumber
     * @return the inserted record as response
     * @throws Exception
     * 
     * recieves size metadata as request params and converts that
     * into json and then pass it to DAO
     */
    public String saveSizeDataToProductMetadata(Map<String, String> valueMap, String styleNumber,
            String colorNumber) throws Exception;

    /**
     * @param valueMap
     * @param styleNumber
     * @param colorNumber
     * @return the updated record as response
     * @throws Exception
     * 
     *recieves the updated color datas as map and converts it to
     *json and pass it to DAO
     */
    public String updateColorDataToProductMetadata(Map<String, String> valueMap,
            String styleNumber, String colorNumber) throws Exception;

    /**
     * @param valueMap
     * @param styleNumber
     * @param colorNumber
     * @param sizeCode
     * @return the updated record as response
     * @throws Exception
     * 
     *recieves the updated size datas as map and converts it to
     * json and pass it to DAO
     */
    public String updateSizeDataToProductMetadata(Map<String, String> valueMap, String styleNumber,
            String colorNumber, String sizeCode) throws Exception;

    /**
     * @param colorData
     * @param styleNumber
     * @return the inserted record as response
     * @throws Exception
     * 
     * recieves style metadata as json and then pass it to DAO
     */
    public String saveColorDataToProductMetadata(String colorData, String styleNumber)
            throws Exception;

    /**
     * @param sizeDataJson
     * @param styleNumber
     * @param colorNumber
     * @return the inserted record as response
     * @throws Exception
     * 
     * recieves style metadata as json and then pass it to DAO
     */
    public String saveSizeDataToProductMetadata(String sizeDataJson, String styleNumber,
            String colorNumber) throws Exception;

    /**
     * @param productMap
     * @return the updated record as response
     * @throws Exception
     * 
     * recieves the updated style datas as map and converts it to
     *json and pass it to DAO
     */
    public String updateStyleDataToProductMetaData(Map<String, String> productMap) throws Exception;

}
