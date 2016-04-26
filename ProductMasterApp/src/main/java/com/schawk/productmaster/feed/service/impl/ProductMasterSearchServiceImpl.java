package com.schawk.productmaster.feed.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schawk.productmaster.feed.dao.ProductMasterFeedDao;
import com.schawk.productmaster.feed.dao.impl.ProductMasterFeedDaoImpl;
import com.schawk.productmaster.feed.service.ProductMasterSearchService;

/**
 * @author sharanya.ramamoorthy
 *
 */
@Service
public class ProductMasterSearchServiceImpl implements ProductMasterSearchService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductMasterFeedDaoImpl.class);
    private static final String COLOR_CODE = "colorCode";
    private static final String SIZE_CODE = "sizeCode";
    private static final String COLOR_CODE_PREFIX = "colors.color.";
    private static final String SIZE_CODE_PREFIX = "colors.color.sizes.size.";

    @Autowired
    private ProductMasterFeedDao productMasterFeedDao;

    @Override
    public String findProductByStyleAndColor(String styleNumber, String colorCode)
            throws Exception {
        return productMasterFeedDao.findProductByStyleAndColor(styleNumber, colorCode);
    }

    @Override
    public String findProductByStyle(String styleNumber, String[] field) throws Exception {
        return productMasterFeedDao.findProductByStyle(styleNumber, field);
    }

    @Override
    public String findProductByFields(String columnName, String[] columnValues,
            String[] columnsToInclude) throws Exception {

        //If the provided input simply contains colorCode/sizeCode then append appropriate column hierarchy based on DB
        if (COLOR_CODE.equalsIgnoreCase(columnName)) {
            columnName = COLOR_CODE_PREFIX.concat(columnName);
        } else if (SIZE_CODE.equalsIgnoreCase(columnName)) {
            columnName = SIZE_CODE_PREFIX.concat(columnName);
        }

        return productMasterFeedDao.findProductByFields(columnName, columnValues, columnsToInclude);
    }

    @Override
    public String globalSearch(String searchField) throws Exception {
        return productMasterFeedDao.globalSearch(searchField);
    }

    @Override
    public String findProductByStyleColorAndSize(String styleNumber, String colorCode, String size)
            throws Exception {
        return productMasterFeedDao.findProductByStyleColorAndSize(styleNumber, colorCode, size);
    }

    @Override
    public String findProductSizesByStyleAndColor(String styleNumber, String colorCode)
            throws Exception {
        return productMasterFeedDao.findProductSizesByStyleAndColor(styleNumber, colorCode);
    }
}
