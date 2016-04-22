package com.schawk.productmaster.feed.service;

/**
 * 
 * @author sharanya.ramamoorthy
 *
 */
public interface ProductMasterSearchService {

    public String findProductByStyleAndColor(String styleNumber, String colorNumber);

    public String findProductByStyle(String styleNumber, String[] field);

    public String findProductByFields(String columnName, String[] columnValues,
            String[] columnsToInclude);

    public String globalSearch(String searchField) throws Exception;

    public String findProductByStyleColorAndSizes(String styleNumber, String colorCode, String size);

    public String findProductByStyleColorAndSizes(String styleNumber, String colorCode);
}
