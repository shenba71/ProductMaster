package com.schawk.productmaster.feed.dao;

import org.json.JSONArray;

public interface ProductMasterFeedDao {

    public String saveInputFeed(JSONArray jsonArray) throws Exception;

    /**
     * @param productMetaData
     * @return the inserted record as response
     * @throws Exception
     * Receives the input metadata for style and inserts in to DB
     */
    public String saveProductMetaDataStyle(String productMetaData) throws Exception;

    /**
     * @param colorMetaData
     * @param styleNumber
     * @return the inserted record as response
     * @throws Exception
     * Receives input colormetadata json and inserts into DB
     */
    public String saveColorMetaData(String colorMetaData, String styleNumber) throws Exception;

    /**
     * @param sizeMetaData
     * @param styleNumber
     * @param colorNumber
     * @return the inserted record as response
     * @throws Exception
     * Receives input sizemetadata json and inserts into DB
     */
    public String saveSizeMetaData(String sizeMetaData, String styleNumber, String colorNumber)
            throws Exception;

    /**
     * @param colorMetaData
     * @param styleNumber
     * @param colorNumber
     * @return the updated record as response
     * @throws Exception
     * Receives updated color metadata and update if record already
     * exists else creates new record
     */
    public String updateColorMetaData(String colorMetaData, String styleNumber, String colorNumber)
            throws Exception;

    /**
     * @param sizeMetaData
     * @param styleNumber
     * @param colorNumber
     * @param sizeCode
     * @return the updated record as response
     * @throws Exception
     * Receives updated size metadata and update if record already
     * exists else creates new record
     */
    public String updateSizeMetaData(String sizeMetaData, String styleNumber, String colorNumber,
            String sizeCode) throws Exception;

    /**
     * Get product metadata based on style and color
     * @param styleNumber
     * @param colorNumber
     * @return
     * @throws Exception
     */
    public String findProductByStyleAndColor(String styleNumber, String colorNumber)
            throws Exception;

    public int getIndexForSize(String styleNumber, String colorCode, String sizeCode)
            throws Exception;

    /**
     * Retrieve product metadata based on style and include the specified
     * columns
     * @param styleNumber
     * @param field
     * @return
     * @throws Exception
     */
    public String findProductByStyle(String styleNumber, String[] field) throws Exception;

    /**
     * @param productMetaData
     * @return the updated record as response
     * @throws Exception
     * Receives updated style metadata and update if record already
     * exists else creates new record
     */
    public String updateProductMetaDataStyle(String productMetaData) throws Exception;

    /**
     * This is a refined search applicable only to specified fields and returns
     * the required columns from json
     * @param columnName
     * @param columnValues
     * @param columnsToInclude
     * @return
     * @throws Exception
     */
    public String findProductByFields(String columnName, String[] columnValues,
            String[] columnsToInclude) throws Exception;

    /**
     * This is a global search applicable only to specified fields which are
     * given in text indexes
     * @param searchField
     * @throws Exception
     */
    public String globalSearch(String searchField) throws Exception;

    /**
     * Retrieve product size based on style, color and size
     * @param styleNumber
     * @param colorCode
     * @param sizeCode
     * @return
     * @throws Exception
     */
    public String findProductByStyleColorAndSize(String styleNumber, String colorCode,
            String sizeCode) throws Exception;

    /**
     * Retrieve product sizes based on style, color and size
     * @param styleNumber
     * @param colorCode
     * @return
     * @throws Exception
     */
    public String findProductSizesByStyleAndColor(String styleNumber, String colorCode)
            throws Exception;

}
