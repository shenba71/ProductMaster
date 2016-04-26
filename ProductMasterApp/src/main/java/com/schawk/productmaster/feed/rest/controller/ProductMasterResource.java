package com.schawk.productmaster.feed.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoException;
import com.schawk.productmaster.feed.service.ProductMasterSearchService;
import com.schawk.productmaster.feed.service.ProductMasterStagingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author shenbagaganesh.param
 * 
 *Controller class for API endpoints of ProductMetaData MicroService
 *
 */
@RestController
@Api(value = "product", description = "Operations in ProductMaster Application")
@RequestMapping("/product")
public class ProductMasterResource {

    @Autowired
    private ProductMasterStagingService productMasterStagingService;

    @Autowired
    private ProductMasterSearchService productMasterSearchservice;

    private static final Logger LOG = LoggerFactory.getLogger(ProductMasterResource.class);

    /**
     * @param productJson
     * @return the inserted document in mongodb as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("store the style metadata recieved as JSON in MongoDB")
    public String saveProductMetaDataStyle(@RequestBody String productJson)
            throws MongoException, Exception {
        LOG.debug("JSONRequest for Style recieved!!!");
        return productMasterStagingService.saveStyleDataToProductMetaData(productJson);
    }

    /**
     * @param styleNumber
     * @param productName
     * @param productType
     * @param category
     * @param gender
     * @param productDescription
     * @param division
     * @param vendor
     * @return the inserted document in mongodb as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("store the style metadata recieved as request parameters in MongoDB")
    public String saveProductMetaDataStyle(
            @RequestParam(value = "style_number", required = true) String styleNumber,
            @RequestParam(value = "product_name", required = false) String productName,
            @RequestParam(value = "product_type", required = false) String productType,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "product_description", required = false) String productDescription,
            @RequestParam(value = "division", required = false) String division,
            @RequestParam(value = "vendor", required = false) String vendor) throws Exception {
        LOG.debug("Request For Style Recieved via Request Parameters...");
        Map<String, String> productMetaData = new HashMap<String, String>();
        productMetaData.put("styleNumber", styleNumber);
        if (!StringUtils.isEmpty(productName)) {
            productMetaData.put("productName", productName);
        }
        if (!StringUtils.isEmpty(productType)) {
            productMetaData.put("productType", productType);
        }
        if (!StringUtils.isEmpty(category)) {
            productMetaData.put("catagory", category);
        }
        if (!StringUtils.isEmpty(gender)) {
            productMetaData.put("gender", gender);
        }
        if (!StringUtils.isEmpty(productDescription)) {
            productMetaData.put("productDescription", productDescription);
        }
        if (!StringUtils.isEmpty(division)) {
            productMetaData.put("division", division);
        }
        if (!StringUtils.isEmpty(vendor)) {
            productMetaData.put("vendor", vendor);
        }

        return productMasterStagingService.saveStyleDataToProductMetaData(productMetaData);

    }

    @RequestMapping(value = "/styles", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("update the style metadata if already present else creates new style record")
    public String updateProductMetaDataStyle(
            @RequestParam(value = "style_number", required = true) String styleNumber,
            @RequestParam(value = "product_name", required = false) String productName,
            @RequestParam(value = "product_type", required = false) String productType,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "product_description", required = false) String productDescription,
            @RequestParam(value = "division", required = false) String division,
            @RequestParam(value = "vendor", required = false) String vendor) throws Exception {
        LOG.debug("Request For Style Recieved via Request Parameters...");
        Map<String, String> productMetaData = new HashMap<String, String>();
        productMetaData.put("styleNumber", styleNumber);
        if (!StringUtils.isEmpty(productName)) {
            productMetaData.put("productName", productName);
        }
        if (!StringUtils.isEmpty(productType)) {
            productMetaData.put("productType", productType);
        }
        if (!StringUtils.isEmpty(category)) {
            productMetaData.put("catagory", category);
        }
        if (!StringUtils.isEmpty(gender)) {
            productMetaData.put("gender", gender);
        }
        if (!StringUtils.isEmpty(productDescription)) {
            productMetaData.put("productDescription", productDescription);
        }
        if (!StringUtils.isEmpty(division)) {
            productMetaData.put("division", division);
        }
        if (!StringUtils.isEmpty(vendor)) {
            productMetaData.put("vendor", vendor);
        }

        return productMasterStagingService.updateStyleDataToProductMetaData(productMetaData);

    }

    /**
     * @param styleNumber
     * @param colorCode
     * @param colorDescription
     * @return the inserted document in mongodb as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("store the color metadata recieved as request parameters in MongoDB")
    public String saveProductMetaDataColor(@PathVariable("styleNumber") String styleNumber,
            @RequestParam(value = "color_code", required = true) String colorCode,
            @RequestParam(value = "color_description", required = false) String colorDescription)
                    throws Exception {
        LOG.debug("Color request recieved for Style " + styleNumber + " via request parameters.");
        Map<String, String> colorMetaData = new HashMap<String, String>();
        if (!StringUtils.isEmpty(colorCode)) {
            colorMetaData.put("colorCode", colorCode);
        }
        if (!StringUtils.isEmpty(colorDescription)) {
            colorMetaData.put("colorDescription", colorDescription);
        }
        return productMasterStagingService.saveColorDataToProductMetadata(colorMetaData,
                styleNumber);

    }

    /**
     * @param styleNumber
     * @param colorMetaDataJson
     * @return the inserted document in mongodb as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("store the color metadata recieved as JSON in MongoDB")
    public String saveProductMetaDataColor(@PathVariable("styleNumber") String styleNumber,
            @RequestBody String colorMetaDataJson) throws Exception {
        LOG.debug("Color request recieved for Style " + styleNumber + " as JSON.");
        return productMasterStagingService.saveColorDataToProductMetadata(colorMetaDataJson,
                styleNumber);

    }

    /**
     * @param styleNumber
     * @param colorNumber
     * @param colorDescription
     * @param colorValue
     * @return the updated document as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors/{colorNumber}", method = RequestMethod.PUT)
    @ApiOperation("update the color metadata if already present else creates new color record")
    public String updateProductMetaDataColor(@PathVariable("styleNumber") String styleNumber,
            @PathVariable("colorNumber") String colorNumber,
            @RequestParam(value = "color_description", required = false) String colorDescription)
                    throws Exception {
        LOG.debug("Update request for Style : " + styleNumber + " color : " + colorNumber);
        Map<String, String> colorMetaData = new HashMap<String, String>();
        if (!StringUtils.isEmpty(colorDescription)) {
            colorMetaData.put("colorDescription", colorDescription);
        }
        return productMasterStagingService.updateColorDataToProductMetadata(colorMetaData,
                styleNumber, colorNumber);

    }

    /**
     * @param styleNumber
     * @param colorNumber
     * @param sizeCode
     * @param upc
     * @param skuId
     * @return the updated document as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors/{colorNumber}/sizes/{sizeCode}", method = RequestMethod.PUT)
    @ApiOperation("update the size metadata if already present else creates new size record")
    public String updateProductMetaDataSize(@PathVariable("styleNumber") String styleNumber,
            @PathVariable("colorNumber") String colorNumber,
            @PathVariable("sizeCode") String sizeCode,
            @RequestParam(value = "upc", required = false) String upc,
            @RequestParam(value = "sku_id", required = false) String skuId) throws Exception {
        LOG.debug("Update request for size :" + styleNumber + " Color : " + colorNumber + " size : "
                + sizeCode);
        Map<String, String> sizeMetaData = new HashMap<String, String>();
        if (!StringUtils.isEmpty(upc)) {
            sizeMetaData.put("upc", upc);
        }
        if (!StringUtils.isEmpty(skuId)) {
            sizeMetaData.put("skuId", skuId);
        }
        return productMasterStagingService.updateSizeDataToProductMetadata(sizeMetaData,
                styleNumber, colorNumber, sizeCode);

    }

    /**
     * @param styleNumber
     * @param colorNumber
     * @param sizeCode
     * @param upc
     * @param skuId
     * @return the inserted document as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors/{colorNumber}/sizes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("store the size metadata recieved as request parameters in MongoDB")
    public String saveProductMetaDataSize(@PathVariable("styleNumber") String styleNumber,
            @PathVariable("colorNumber") String colorNumber,
            @RequestParam(value = "size_code", required = true) String sizeCode,
            @RequestParam(value = "upc", required = false) String upc,
            @RequestParam(value = "sku_id", required = false) String skuId) throws Exception {
        LOG.debug("Size request recieved for Style " + styleNumber + " Color" + colorNumber
                + " as request parameters.");
        Map<String, String> sizeMetaData = new HashMap<String, String>();
        sizeMetaData.put("sizeCode", sizeCode);
        if (!StringUtils.isEmpty(upc)) {
            sizeMetaData.put("upc", upc);
        }
        if (!StringUtils.isEmpty(skuId)) {
            sizeMetaData.put("skuId", skuId);
        }
        return productMasterStagingService.saveSizeDataToProductMetadata(sizeMetaData, styleNumber,
                colorNumber);
    }

    /**
     * @param styleNumber
     * @param colorNumber
     * @param sizeMetaDataJson
     * @return the inserted document as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors/{colorNumber}/sizes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("store the size metadata recieved as request parameters in MongoDB")
    public String saveProductMetaDataSize(@PathVariable("styleNumber") String styleNumber,
            @PathVariable("colorNumber") String colorNumber, @RequestBody String sizeMetaDataJson)
                    throws Exception {
        LOG.debug("Size request recieved for Style " + styleNumber + " Color" + colorNumber
                + " as JSON.");
        return productMasterStagingService.saveSizeDataToProductMetadata(sizeMetaDataJson,
                styleNumber, colorNumber);
    }

    /**
     * Search the product details of the given styleNumber and fields that
     * should be included in the query results. 
     * Example Input :/styles/12345?include=styleNumber,productName,colors 
     * Example Input :/styles/12345
     * 
     * @param styleNumber
     * @param fieldsToDisplay
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/styles/{styleNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("search and retrieves specified fields for given style number")
    public String findProductByStyle(@PathVariable("styleNumber") String styleNumber,
            @RequestParam(value = "include", required = false) String fieldsToDisplay)
                    throws Exception {
        LOG.info("StyleNumber : " + styleNumber + " Fields to include : " + fieldsToDisplay);

        String[] fieldsToInclude = null;
        if (StringUtils.isNotBlank(fieldsToDisplay)) {
            fieldsToInclude = fieldsToDisplay.split(",");
        }
        return productMasterSearchservice.findProductByStyle(styleNumber, fieldsToInclude);
    }

    /**
     * This is a q search which can be used by two ways a) Refind search for
     * specified fields Example Input :
     * /styles?q={styleNumber=12345,12346}&include=styleNumber,colors b) Global
     * search for specified fields which are mentioned in text indexes Example
     * Input : /styles?q=FOOTWEAR
     * @param globalSearchFields
     * @param fieldsToInclude
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/styles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("search and retrieves the list of products that matches the search criteria")
    public String findProductByFields(
            @RequestParam(value = "q", required = false) String globalSearchFields,
            @RequestParam(value = "include", required = false) String fieldsToInclude)
                    throws Exception {
        String response = "";
        LOG.info("Query field : " + globalSearchFields + " Fields to include : " + fieldsToInclude);

        if (StringUtils.isNotBlank(globalSearchFields)) {
            if (globalSearchFields.startsWith("{")) {
                response = productMasterSearchservice.findProductByFields(globalSearchFields,
                        fieldsToInclude);
            } else {
                // global search is case insensitive
                response = productMasterSearchservice.globalSearch(globalSearchFields,
                        fieldsToInclude);
            }
        }
        return response;
    }

    /**
     * Search the product with the specified color 
     * Example Input :/styles/12345/colors/000
     * @param styleNumber
     * @param colorCode
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors/{colorCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("search and retrieves the record matched for given style number and color")
    public String findProductByStyleAndColor(@PathVariable("styleNumber") String styleNumber,
            @PathVariable("colorCode") String colorCode) throws Exception {
        LOG.info("StyleNumber : " + styleNumber + " colorCode : " + colorCode);
        return productMasterSearchservice.findProductByStyleAndColor(styleNumber, colorCode);
    }

    /**
     * Search the product size with the specified size 
     * Example Input :/styles/831070/colors/501/sizes/M
     * @param styleNumber
     * @param colorCode
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors/{colorCode}/sizes/{sizeCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findProductByStyleColorAndSize(@PathVariable("styleNumber") String styleNumber,
            @PathVariable("colorCode") String colorCode, @PathVariable("sizeCode") String sizeCode)
                    throws Exception {
        LOG.info("StyleNumber : " + styleNumber + " colorCode : " + colorCode + " Size : "
                + sizeCode);
        return productMasterSearchservice.findProductByStyleColorAndSize(styleNumber, colorCode,
                sizeCode);
    }

    /**
     * Search all product sizes based on style and color 
     * Example Input : /styles/12345/colors/000/sizes
     * @param styleNumber
     * @param colorCode
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors/{colorCode}/sizes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findProductSizesByStyleAndColor(@PathVariable("styleNumber") String styleNumber,
            @PathVariable("colorCode") String colorCode) throws Exception {
        LOG.info("StyleNumber : " + styleNumber + " colorCode : " + colorCode);
        return productMasterSearchservice.findProductSizesByStyleAndColor(styleNumber, colorCode);
    }
}
