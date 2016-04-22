package com.schawk.productmaster.feed.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schawk.productmaster.feed.dao.ProductMasterFeedDao;
import com.schawk.productmaster.feed.dao.impl.ProductMasterFeedDaoImpl;
import com.schawk.productmaster.feed.service.ProductMasterSearchService;

/**
 * This class search product details based on various input.
 * @author sharanya.ramamoorthy
 *
 */
@Service
public class ProductMasterSearchServiceImpl implements ProductMasterSearchService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductMasterFeedDaoImpl.class);
    @Autowired
    private ProductMasterFeedDao productMasterFeedDao;

    /**
     * Search the product with the specified color
     */
    @Override
    public String findProductByStyleAndColor(String styleNumber, String colorCode) {
        String searchResult = null;
        try {
            searchResult = productMasterFeedDao.findProductByStyleAndColor(styleNumber, colorCode);
        } catch (Exception e) {
            LOG.debug("Exception occurred while searching for product using style and color.");
            e.printStackTrace();
        }
        return searchResult;
    }

    /**
     * Search the product details of the given styleNumber and fields that
     * should be included in the query results.
     */
    @Override
    public String findProductByStyle(String styleNumber, String[] field) {
        String searchResult = null;
        try {
            searchResult = productMasterFeedDao.findProductByStyle(styleNumber, field);
        } catch (Exception e) {
            LOG.debug("Exception occurred while searching for product using style.");
            e.printStackTrace();
        }
        return searchResult;
    }

    /**
     * This is a refined search applicable only to specified fields
     */
    @Override
    public String findProductByFields(String columnName, String[] columnValues,
            String[] columnsToInclude) {
        String searchResult = null;
        String colorCodePrefix = "colors.color.";
        String sizeCodePrefix = "colors.color.sizes.size.";
        try {
            if (columnName.equalsIgnoreCase("colorCode")
                    || columnName.equalsIgnoreCase("colorDescription")) {
                columnName = colorCodePrefix.concat(columnName);
            } else if (columnName.equalsIgnoreCase("sizeCode")) {
                columnName = sizeCodePrefix.concat(columnName);
            }
            searchResult = productMasterFeedDao.findProductByFields(columnName, columnValues,
                    columnsToInclude);
        } catch (Exception e) {
            LOG.debug("Exception occurred while searching for product.");
            e.printStackTrace();
        }
        return searchResult;
    }

    /**
     * This is a global search applicable only to specified fields which are
     * given in text indexes
     * 
     * @param searchField
     * @throws Exception
     */
    @Override
    public String globalSearch(String searchField) throws Exception {

        if (searchField != null && searchField != "") {
            return productMasterFeedDao.globalSearch(searchField);
        } else {
            LOG.debug("Search field is empty");
            return "Please enter a text to search";
        }
    }

    /**
    * Search the product size with the specified size
    */
    @Override
    public String findProductByStyleColorAndSizes(String styleNumber, String colorCode, String size) {
        String searchResult = null;
        try {
            searchResult = productMasterFeedDao.findProductByStyleColorAndSizes(styleNumber,
                    colorCode, size);
        } catch (Exception e) {
            LOG.debug("Exception occurred while searching for product size using style and color");
            e.printStackTrace();
        }
        return searchResult;
    }

    /**
    * Search all product sizes based on style and color
    */
    @Override
    public String findProductByStyleColorAndSizes(String styleNumber, String colorCode) {
        String searchResult = null;
        try {
            searchResult = productMasterFeedDao.findProductByStyleColorAndSizes(styleNumber,
                    colorCode);
        } catch (Exception e) {
            LOG.debug(
                    "Exception occurred while searching for product all sizes using style and color");
            e.printStackTrace();
        }
        return searchResult;
    }

}
