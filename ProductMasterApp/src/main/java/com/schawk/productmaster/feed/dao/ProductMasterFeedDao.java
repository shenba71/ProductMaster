package com.schawk.productmaster.feed.dao;

import org.json.JSONArray;

public interface ProductMasterFeedDao {

    public String saveInputFeed(JSONArray jsonArray) throws Exception;

    public String searchSizeRecord(String styleNumber, String colorCode, String sizeCode)
            throws Exception;

    public String saveProductMetaDataStyle(String productMetaData) throws Exception;

    public String saveColorMetaData(String colorMetaData, String styleNumber) throws Exception;

    public String saveSizeMetaData(String sizeMetaData, String styleNumber, String colorNumber)
            throws Exception;

    public String updateColorMetaData(String colorMetaData, String styleNumber, String colorNumber)
            throws Exception;

    public String updateSizeMetaData(String sizeMetaData, String styleNumber, String colorNumber,
            String sizeCode) throws Exception;

    public String findProductByStyleAndColor(String styleNumber, String colorNumber)
            throws Exception;

    public int getIndexForSize(String styleNumber, String colorCode, String sizeCode)
            throws Exception;

    public String findProductByStyle(String styleNumber, String[] field) throws Exception;

    public String updateProductMetaDataStyle(String productMetaData) throws Exception;

    public String findProductByFields(String columnName, String[] columnValues,
            String[] columnsToInclude) throws Exception;

    public String globalSearch(String searchField) throws Exception;

    public String findProductByStyleColorAndSizes(String styleNumber, String colorCode,
            String sizeCode) throws Exception;

    public String findProductByStyleColorAndSizes(String styleNumber, String colorCode)
            throws Exception;

}
