package com.schawk.productmaster.feed.service;

/**
 * This class search product details based on various input.
 * @author sharanya.ramamoorthy
 *
 */
public interface ProductMasterSearchService {

    /**
     * Search the product with the specified style and color.
     * @param styleNumber
     * @param colorNumber
     * @return
     * @throws Exception
     */
    public String findProductByStyleAndColor(String styleNumber, String colorNumber)
            throws Exception;

    /**
     * Search the product details of the given styleNumber and fields that should be included in the query results.
     * @param styleNumber
     * @param field
     * @return
     * @throws Exception
     */
    public String findProductByStyle(String styleNumber, String field) throws Exception;

    /**
     * This is a refined search applicable only to specified fields
     * @param columnName
     * @param columnValues
     * @param columnsToInclude
     * @return
     * @throws Exception
     */
    public String findProductByFields(String globalSearchFields, String fieldsToInclude)
            throws Exception;

    /**
     * This is a global search applicable only to specified fields which are
     * given in text indexes
     * @param searchField
     * @param fieldsToInclude 
     * @return
     * @throws Exception
     */
    public String globalSearch(String searchField, String fieldsToInclude) throws Exception;

    /**
     * Search the particular product size with the specified style, color and size
     * @param styleNumber
     * @param colorCode
     * @param sizeCode
     * @return
     * @throws Exception
     */
    public String findProductByStyleColorAndSize(String styleNumber, String colorCode,
            String sizeCode) throws Exception;

    /**
     * Search all product sizes based on given style and color
     * @param styleNumber
     * @param colorCode
     * @return
     * @throws Exception
     */
    public String findProductSizesByStyleAndColor(String styleNumber, String colorCode)
            throws Exception;

    /**
     * Search for products based on parameters
     * @param globalSearchFields
     * @param fieldsToInclude
     * @param isSearchFieldPresent 
     * @return
     * @throws Exception
     */
    public String findProducts(String globalSearchFields, String fieldsToInclude,
            String isSearchFieldPresent) throws Exception;

}
