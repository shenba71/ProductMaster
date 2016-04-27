package com.schawk.productmaster.feed.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schawk.productmaster.feed.dao.ProductMasterFeedDao;
import com.schawk.productmaster.feed.dao.impl.ProductMasterFeedDaoImpl;
import com.schawk.productmaster.feed.service.ProductMasterSearchService;
import com.schawk.productmaster.util.StringUtil;

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
    public String findProductByStyleAndColor(String styleNumber, String colorCode) throws Exception {
        return productMasterFeedDao.findProductByStyleAndColor(styleNumber, colorCode);
    }

    @Override
    public String findProductByStyle(String styleNumber, String field) throws Exception {
        List<String> fieldsToInclude = null;
        if (StringUtils.isNotBlank(field)) {
            for (String string : field.split(",")) {
                fieldsToInclude.add(StringUtil.getCamelCase(string));
            }

        }

        return productMasterFeedDao.findProductByStyle(styleNumber, fieldsToInclude);
    }

    @Override
    public String findProductByFields(String globalSearchFields, String fieldsToInclude)
            throws Exception {

        String globalFields = globalSearchFields.replaceAll("(\\{|\\})", "");
        String[] searchFields = globalFields.split("=");
        String response = null;

        //check if array size is two since the input would be q={styleNumber=12345,12346}
        String columnName = null;
        String columnValue = null;
        if (searchFields.length == 2) {
            columnName = StringUtil.getCamelCase(searchFields[0]);
            columnValue = searchFields[1];
        }

        LOG.debug("Global search for field : " + columnName + " and value :" + columnValue);
        String[] columnValues = null;
        if (StringUtils.isNotBlank(columnValue)) {
            columnValues = columnValue.split(",");
        }

        List<String> columnsToInclude = null;
        // check if include fields are present
        if (StringUtils.isNotBlank(fieldsToInclude)) {
            for (String string : fieldsToInclude.split(",")) {
                columnsToInclude.add(StringUtil.getCamelCase(string));
            }
        }

        //If the provided input simply contains colorCode/sizeCode then append appropriate column hierarchy based on DB
        if (COLOR_CODE.equalsIgnoreCase(columnName)) {
            columnName = COLOR_CODE_PREFIX.concat(columnName);
        } else if (SIZE_CODE.equalsIgnoreCase(columnName)) {
            columnName = SIZE_CODE_PREFIX.concat(columnName);
        }

        if (StringUtils.isNotBlank(columnName) && columnValues != null) {
            response = productMasterFeedDao.findProductByFields(columnName, columnValues,
                    columnsToInclude);
        }
        return response;
    }

    @Override
    public String globalSearch(String searchField, String fieldsToInclude) throws Exception {
        List<String> columnsToInclude = null;
        // check if include fields are present
        if (StringUtils.isNotBlank(fieldsToInclude)) {
            for (String string : fieldsToInclude.split(",")) {
                columnsToInclude.add(StringUtil.getCamelCase(string));
            }
        }
        return productMasterFeedDao.globalSearch(searchField, columnsToInclude);
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
