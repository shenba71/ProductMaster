package com.schawk.productmaster.feed.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoException;
import com.schawk.productmaster.feed.service.ProductMasterSearchService;
import com.schawk.productmaster.feed.service.ProductMasterStagingService;
import com.schawk.productmaster.web.rest.errors.MissingParameterException;
import com.schawk.productmaster.web.rest.util.ProductMasterRestUtil;

/**
 * @author shenbagaganesh.param
 * 
 *Controller class for API endpoints of ProductMetaData MicroService
 *
 */
@RestController
@Api(value = "ProductMasterResource", description = "Operations in ProductMaster Application")
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
    public String saveProductMetaDataStyle(@RequestBody String productJson) throws MongoException,
            Exception {
        LOG.debug("JSONRequest for Style recieved!!!");
        return productMasterStagingService.saveStyleDataToProductMetaData(productJson);
    }

    /**
     * @param map of request parameters
     * @return the inserted document in mongodb as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("store the style metadata recieved as request parameters in MongoDB")
    public String saveProductMetaDataStyle(@RequestParam Map<String, String> params)
            throws Exception {
        LOG.debug("Request For Style Recieved via Request Parameters...");
        Map<String, String> productMetaData = new HashMap<String, String>();
        productMetaData = ProductMasterRestUtil.checkAndconvertParamsToCamelCase(params);
        if (!productMetaData.containsKey("styleNumber")) {
            throw new MissingParameterException("Request parameter should contain Style Number");
        }
        return productMasterStagingService.saveStyleDataToProductMetaData(productMetaData);

    }

    /**
     * @param map of request parameters
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/styles", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("update the style metadata if already present else creates new style record")
    public String updateProductMetaDataStyle(@RequestParam Map<String, String> params)
            throws Exception {
        LOG.debug("Request For Style Recieved via Request Parameters...");
        Map<String, String> productMetaData = null;
        productMetaData = ProductMasterRestUtil.checkAndconvertParamsToCamelCase(params);
        if (!CollectionUtils.isEmpty(productMetaData)
                && !productMetaData.containsKey("styleNumber")) {
            throw new MissingParameterException("Request parameter should contain Style Number");
        }
        return productMasterStagingService.updateStyleDataToProductMetaData(productMetaData);

    }

    /**
     * @param styleNumber
     * @param map of request parameters
     * @return the inserted document in mongodb as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("store the color metadata recieved as request parameters in MongoDB")
    public String saveProductMetaDataColor(@PathVariable("styleNumber") String styleNumber,
            @RequestParam Map<String, String> params) throws Exception {
        LOG.debug("Color request recieved for Style " + styleNumber + " via request parameters.");
        Map<String, String> colorMetaData = new HashMap<String, String>();
        colorMetaData = ProductMasterRestUtil.checkAndconvertParamsToCamelCase(params);
        if (!colorMetaData.containsKey("colorCode")) {
            throw new MissingParameterException("Request parameter should contain colorCode");
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
     * @param map of request parameters
     * @return the updated document as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors/{colorNumber}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("update the color metadata if already present else creates new color record")
    public String updateProductMetaDataColor(@PathVariable("styleNumber") String styleNumber,
            @PathVariable("colorNumber") String colorNumber,
            @RequestParam Map<String, String> params) throws Exception {
        LOG.debug("Update request for Style : " + styleNumber + " color : " + colorNumber);
        Map<String, String> colorMetaData = new HashMap<String, String>();
        colorMetaData = ProductMasterRestUtil.checkAndconvertParamsToCamelCase(params);
        return productMasterStagingService.updateColorDataToProductMetadata(colorMetaData,
                styleNumber, colorNumber);

    }

    /**
     * @param styleNumber
     * @param colorNumber
     * @param sizeCode
     * @param map of request parameters
     * @return the updated document as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors/{colorNumber}/sizes/{sizeCode}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("update the size metadata if already present else creates new size record")
    public String updateProductMetaDataSize(@PathVariable("styleNumber") String styleNumber,
            @PathVariable("colorNumber") String colorNumber,
            @PathVariable("sizeCode") String sizeCode, @RequestParam Map<String, String> params)
            throws Exception {
        LOG.debug("Update request for size :" + styleNumber + " Color : " + colorNumber
                + " size : " + sizeCode);
        Map<String, String> sizeMetaData = new HashMap<String, String>();
        sizeMetaData = ProductMasterRestUtil.checkAndconvertParamsToCamelCase(params);
        return productMasterStagingService.updateSizeDataToProductMetadata(sizeMetaData,
                styleNumber, colorNumber, sizeCode);

    }

    /**
     * @param styleNumber
     * @param colorNumber
     * @param map of request parameters
     * @return the inserted document as response
     * @throws Exception
     */
    @RequestMapping(value = "/styles/{styleNumber}/colors/{colorNumber}/sizes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("store the size metadata recieved as request parameters in MongoDB")
    public String saveProductMetaDataSize(@PathVariable("styleNumber") String styleNumber,
            @PathVariable("colorNumber") String colorNumber,
            @RequestParam Map<String, String> params) throws Exception {
        LOG.debug("Size request recieved for Style " + styleNumber + " Color" + colorNumber
                + " as request parameters.");
        Map<String, String> sizeMetaData = new HashMap<String, String>();
        sizeMetaData = ProductMasterRestUtil.checkAndconvertParamsToCamelCase(params);
        if (!sizeMetaData.containsKey("sizeCode")) {
            throw new MissingParameterException("Request parameter should contain sizeCode");
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
        return productMasterSearchservice.findProductByStyle(styleNumber, fieldsToDisplay);
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
            @RequestParam(value = "q", required = true) String globalSearchFields,
            @RequestParam(value = "include", required = false) String fieldsToInclude)
                    throws Exception {
        LOG.info("Query field : " + globalSearchFields + " Fields to include : " + fieldsToInclude);
        
        if (StringUtils.isNotBlank(globalSearchFields)) {
        	       	
        	String isSearchFieldPresent = String.valueOf(globalSearchFields.startsWith("{") && globalSearchFields.endsWith("}"));
        	return productMasterSearchservice.findProducts(globalSearchFields, fieldsToInclude, isSearchFieldPresent);		  
        	
        } else {
        	throw new InvalidParameterException();
        }
                    
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
