package com.schawk.productmaster.feed.dao;

import org.json.JSONArray;

public interface ProductMasterFeedDao {

	public String saveInputFeed(JSONArray jsonArray) throws Exception;

	public String searchSizeRecord(String styleNumber, String colorCode,
			String sizeCode) throws Exception;

	/**
	 * @param productMetaData
	 * @return the inserted record as response
	 * @throws Exception
	 * 
	 *             recieves the input metadata for style and inserts in to DB
	 */
	public String saveProductMetaDataStyle(String productMetaData)
			throws Exception;

	/**
	 * @param colorMetaData
	 * @param styleNumber
	 * @return the inserted record as response
	 * @throws Exception
	 * 
	 *             recieves input colormetadata json and inserts into DB
	 */
	public String saveColorMetaData(String colorMetaData, String styleNumber)
			throws Exception;

	/**
	 * @param sizeMetaData
	 * @param styleNumber
	 * @param colorNumber
	 * @return the inserted record as response
	 * @throws Exception
	 * 
	 *             recieves input sizemetadata json and inserts into DB
	 */
	public String saveSizeMetaData(String sizeMetaData, String styleNumber,
			String colorNumber) throws Exception;

	/**
	 * @param colorMetaData
	 * @param styleNumber
	 * @param colorNumber
	 * @return the updated record as response
	 * @throws Exception
	 * 
	 *             recieves updated color metadata and update if record already
	 *             exists else creates new record
	 */
	public String updateColorMetaData(String colorMetaData, String styleNumber,
			String colorNumber) throws Exception;

	/**
	 * @param sizeMetaData
	 * @param styleNumber
	 * @param colorNumber
	 * @param sizeCode
	 * @return the updated record as response
	 * @throws Exception
	 * 
	 *             recieves updated size metadata and update if record already
	 *             exists else creates new record
	 */
	public String updateSizeMetaData(String sizeMetaData, String styleNumber,
			String colorNumber, String sizeCode) throws Exception;

	public String findProductByStyleAndColor(String styleNumber,
			String colorNumber) throws Exception;

	public int getIndexForSize(String styleNumber, String colorCode,
			String sizeCode) throws Exception;

	public String findProductByStyle(String styleNumber, String[] field)
			throws Exception;

	/**
	 * @param productMetaData
	 * @return the updated record as response
	 * @throws Exception
	 * 
	 *             recieves updated style metadata and update if record already
	 *             exists else creates new record
	 */
	public String updateProductMetaDataStyle(String productMetaData)
			throws Exception;

	public String findProductByFields(String columnName, String[] columnValues,
			String[] columnsToInclude) throws Exception;

	public String globalSearch(String searchField) throws Exception;

	public String findProductByStyleColorAndSizes(String styleNumber,
			String colorCode, String sizeCode) throws Exception;

	public String findProductByStyleColorAndSizes(String styleNumber,
			String colorCode) throws Exception;

}
