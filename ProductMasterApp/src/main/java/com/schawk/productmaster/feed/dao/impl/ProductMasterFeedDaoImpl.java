package com.schawk.productmaster.feed.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkUpdateRequestBuilder;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteRequestBuilder;
import com.mongodb.BulkWriteResult;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import com.schawk.productmaster.feed.dao.ProductMasterFeedDao;
import com.schawk.productmaster.web.rest.errors.CustomMongoException;

/**
 * @author shenbagaganesh.param
 *
 */
@Repository
public class ProductMasterFeedDaoImpl implements ProductMasterFeedDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(ProductMasterFeedDaoImpl.class);

    private static final String COLLECTION_NAME = "product_master";
    private static final String PRODUCT_STYLE = "styleNumber";
    private static final String PRODUCT_COLOR = "colors.color.colorCode";
    private static final String PRODUCT_SIZE = "colors.color.sizes.size.sizeCode";

    /* private MongoTemplate mongoTemplate; */

    @Override
    public String saveInputFeed(JSONArray inputFeed) {
        String response = null;
        try {
            DBCollection collection = mongoTemplate.getDb().getCollection(COLLECTION_NAME);

            // Get BulkWriteOperation by accessing the mongodb

            BulkWriteOperation bulkWriteOperation = collection.initializeUnorderedBulkOperation();

            // perform the upsert operation in the loop to add objects for bulk
            // execution
            for (int i = 0; i < inputFeed.length(); i++) {

                String product = inputFeed.getJSONObject(i).getString("Product style");
                String color = inputFeed.getJSONObject(i).getString("Product color");
                DBObject obj = (DBObject) JSON.parse(inputFeed.getJSONObject(i).toString());
                BasicDBObject object = new BasicDBObject();
                object.append("$set", obj);

                // get a bulkWriteRequestBuilder by issuing find on the
                // ProductStyle and Product color

                BulkWriteRequestBuilder bulkWriteRequestBuilder = bulkWriteOperation
                        .find(new BasicDBObject("Product style", product).append("Product color",
                                color));

                // get hold of upsert operation from bulkWriteRequestBuilder

                BulkUpdateRequestBuilder updateReq = bulkWriteRequestBuilder.upsert();
                updateReq.update(object);
            }
            // execute bulk operation on mycol collection
            LOG.debug("Started inserting the json Inputs");
            BulkWriteResult result = bulkWriteOperation.execute();
            LOG.debug("Number of inserted documents=====>" + result.getInsertedCount());
            LOG.debug("Number of updated documents=====>" + result.getModifiedCount());
            LOG.debug("Number of Matched documents=====>" + result.getMatchedCount());
            LOG.debug("Number of Upserted documents=====>"
                    + (result.getUpserts() == null ? 0 : result.getUpserts().size()));
            LOG.debug("Completed insertion of inputs..");
            response = "No Of Inserted Documents"
                    + (result.getUpserts() == null ? 0 : result.getUpserts().size()) + "   "
                    + "No Of Updated Documents" + result.getModifiedCount();
        } catch (Exception e) {
            e.printStackTrace();
            response = "Insertion Failed" + e.getMessage();
        }

        return response;
    }

    @Override
    public String saveProductMetaDataStyle(String productMetaData) throws Exception {
        String response = null;
        String styleNumber = null;
        try {
            LOG.debug("Inside saveProductMetaDataStyle");
            styleNumber = getValueFromJson(productMetaData, PRODUCT_STYLE);
            DBCollection collection = mongoTemplate.getDb().getCollection(COLLECTION_NAME);
            DBObject styleObject = (DBObject) JSON.parse(productMetaData);
            collection.insert(styleObject);
            response = findProductByStyle(styleNumber, null);
            LOG.debug("Response==" + response);
        } catch (MongoException mongoException) {
            throw new CustomMongoException(styleNumber, mongoException.getCode(),
                    mongoException.getMessage());
        }

        return response;

    }

    @Override
    public String updateProductMetaDataStyle(String productMetaData) throws Exception {
        LOG.debug("Inside Update Style datas");
        String styleNumber = getValueFromJson(productMetaData, PRODUCT_STYLE);
        DBCollection collection = mongoTemplate.getDb().getCollection(COLLECTION_NAME);
        DBObject productObject = (DBObject) JSON.parse(productMetaData);
        BasicDBObject styleObject = new BasicDBObject("$set", productObject);
        collection.update(new BasicDBObject("styleNumber", styleNumber), styleObject, true, false);
        return findProductByStyle(styleNumber, null);

    }

    @Override
    public String saveColorMetaData(String colorMetaData, String styleNumber) throws Exception {
        String response = null;
        LOG.debug("Inside save color Meta data values");
        DBCollection collection = mongoTemplate.getDb().getCollection(COLLECTION_NAME);
        String colorNumber = getValueFromJson(colorMetaData, "colorCode");
        DBObject obj = (DBObject) JSON.parse(colorMetaData);
        BasicDBObject colorMetaDataObject = new BasicDBObject("color", obj);
        // forming query object for find operation
        BasicDBObject styleObject = new BasicDBObject("styleNumber", styleNumber);
        BasicDBObject colorObject = new BasicDBObject("colors", colorMetaDataObject);
        BasicDBObject objectToInsert = new BasicDBObject();
        objectToInsert.append("$addToSet", colorObject).append("$setOnInsert", styleObject);
        // insertion will happen only if record not present for given color
        if (findProductByStyleAndColor(styleNumber, colorNumber).length() == 0) {
            collection.update(styleObject, objectToInsert, true, false);
            response = findProductByStyle(styleNumber, null);
        } else {
            response = "Record already exists";
        }
        return response;
    }

    @Override
    public String saveSizeMetaData(String sizeMetaData, String styleNumber, String colorNumber)
            throws Exception {
        String response = null;
        LOG.debug("Save Size metedata values");
        DBCollection collection = mongoTemplate.getDb().getCollection(COLLECTION_NAME);
        String sizeCode = getValueFromJson(sizeMetaData, "sizeCode");
        DBObject obj = (DBObject) JSON.parse(sizeMetaData);
        BasicDBObject sizeMetaDataObject = new BasicDBObject("size", obj);
        BasicDBObject queryObject = new BasicDBObject("styleNumber", styleNumber).append(
                "colors.color.colorCode", colorNumber);
        BasicDBObject sizeObject = new BasicDBObject("colors.$.color.sizes", sizeMetaDataObject);
        // If style and color record was not already present, then wil create
        // style and color record before inserting size metadata
        if ((findProductByStyle(styleNumber, null).length() == 0)
                || findProductByStyleAndColor(styleNumber, colorNumber).length() == 0) {
            BasicDBObject colorObject = new BasicDBObject("colorCode", colorNumber);
            saveColorMetaData(colorObject.toString(), styleNumber);
        }
        BasicDBObject object = new BasicDBObject();
        object.append("$addToSet", sizeObject);
        // If a size record was not present, then allow to insert
        if (findProductByStyleColorAndSize(styleNumber, colorNumber, sizeCode).length() == 0) {
            collection.update(queryObject, object, true, false);
            response = findProductByStyle(styleNumber, null);
            ;
        } else {
            response = "SIZE RECORD ALREADY EXISTS";
        }

        return response;

    }

    @Override
    public String updateColorMetaData(String colorMetaData, String styleNumber, String colorNumber)
            throws Exception {
        DBCollection collection = mongoTemplate.getDb().getCollection(COLLECTION_NAME);
        DBObject obj = (DBObject) JSON.parse(colorMetaData);
        BasicDBObject queryObject = new BasicDBObject("styleNumber", styleNumber).append(
                "colors.color.colorCode", colorNumber);
        BasicDBObject colorObject = new BasicDBObject("$set", obj);
        collection.update(queryObject, colorObject, true, false);
        return findProductByStyle(styleNumber, null);

    }

    @Override
    public String updateSizeMetaData(String sizeMetaData, String styleNumber, String colorNumber,
            String sizeCode) throws Exception {
        DBCollection collection = mongoTemplate.getDb().getCollection(COLLECTION_NAME);
        DBObject obj = (DBObject) JSON.parse(sizeMetaData);

        BasicDBObject queryObject = new BasicDBObject("styleNumber", styleNumber).append(
                "colors.color.colorCode", colorNumber);
        BasicDBObject colorObject = new BasicDBObject("$set", obj);
        WriteResult res = collection.update(queryObject, colorObject, true, false);
        return res.toString();

    }

    @Override
    public int getIndexForSize(String styleNumber, String colorCode, String sizeCode)
            throws Exception {
        String result = findProductByStyleAndColor(styleNumber, colorCode);
        JSONObject productJson = new JSONObject(result);
        JSONArray sizeArray = productJson.getJSONArray("colors").getJSONObject(0)
                .getJSONObject("color").getJSONArray("sizes");
        int position = 0;
        for (int i = 0; i < sizeArray.length(); i++) {
            JSONObject objects = sizeArray.getJSONObject(i);
            if (objects.getJSONObject("size").getString("sizeCode").equalsIgnoreCase(sizeCode)) {
                position = i;
            }

        }
        return position;

    }

    @Override
    public String findProductByFields(String columnName, String[] columnValues,
            List<String> columnsToInclude) throws Exception {
        LOG.info("Search for multiple style numbers and display specified columns");
        String results = "";
        Query query = new Query();
        query.addCriteria(Criteria.where(columnName).in(columnValues));

        // columns which are included would be displayed
        if (columnsToInclude != null) {
            for (String FieldName : columnsToInclude) {
                query.fields().include(FieldName);
            }
        }
        LOG.debug("Query : " + query);
        List<String> searchResults = mongoTemplate.find(query, String.class, COLLECTION_NAME);

        if (CollectionUtils.isEmpty(searchResults) == false) {
            results = searchResults.toString();
        }
        return results;
    }

    @Override
    public String findProductByStyleAndColor(String styleNumber, String colorNumber)
            throws Exception {
        LOG.info("Search for style numbers and color");
        String results = "";
        Query query = new Query();
        query.addCriteria(Criteria.where(PRODUCT_STYLE).is(styleNumber).and(PRODUCT_COLOR)
                .is(colorNumber));

        // columns which are included would be displayed
        query.fields().include("catagory").include("styleNumber").include("gender")
                .include("productName").include("productType").include("colors.$");

        // uncomment below line to get color without sizes and comment the above
        // line
        // query.fields().exclude("colors.color.sizes");
        LOG.debug("Query : " + query);

        String searchResult = mongoTemplate.findOne(query, String.class, COLLECTION_NAME);
        if (StringUtils.isEmpty(searchResult) == false) {
            results = searchResult.toString();
        }
        return results;
    }

    @Override
    public String findProductByStyle(String styleNumber, List<String> field) throws Exception {
        LOG.info("Search for style numbers");
        String results = "";
        Query query = new Query();
        query.addCriteria(Criteria.where(PRODUCT_STYLE).is(styleNumber));

        // columns which are included would be displayed
        if (field != null) {
            for (String key : field) {
                query.fields().include(key);
            }
        }
        LOG.debug("Query : " + query);

        String searchResult = mongoTemplate.findOne(query, String.class, COLLECTION_NAME);
        if (StringUtils.isEmpty(searchResult) == false) {
            results = searchResult.toString();
        }
        return results;
    }

    @Override
    public String globalSearch(String searchField, List<String> columnsToInclude) throws Exception {
        LOG.info("Inside globalSearch method");
        String searchResult = "";
        List<String> resultList = new ArrayList<String>();
        DBCollection collection = mongoTemplate.getDb().getCollection(COLLECTION_NAME);
        DBObject searchQuery = QueryBuilder.start().text(searchField).get();

        BasicDBObject fieldsToInclude = new BasicDBObject();
        // columns which are included would be displayed
        if (columnsToInclude != null) {
            for (String FieldName : columnsToInclude) {
                fieldsToInclude.put(FieldName, 1);
            }
        }

        DBCursor productCursor = collection.find(searchQuery, fieldsToInclude);

        while (productCursor.hasNext()) {
            String resultString = productCursor.next().toString();
            LOG.debug("Results :" + resultString);
            resultList.add(resultString);
            LOG.debug("Results size" + resultList.size());
        }

        if (CollectionUtils.isEmpty(resultList) == false) {
            searchResult = resultList.toString();
        }
        return searchResult;
    }

    /**
     * @param jsonInput
     * @param keyValue
     * @return the value of the given key in json string
     * @throws JSONException
     */
    private String getValueFromJson(String jsonInput, String keyValue) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonInput);
        return jsonObject.getString(keyValue);
    }

    @Override
    public String findProductByStyleColorAndSize(String styleNumber, String colorCode,
            String sizeCode) throws Exception {
        LOG.info("Search for size by given style numbers, color and sizeCode");
        String results = "";
        String searchResult = null;

        List<AggregationOperation> list = new ArrayList<AggregationOperation>();
        // Deconstructs an array field from the input documents to output a
        // document for each element
        list.add(Aggregation.unwind("colors"));
        list.add(Aggregation.unwind("colors.color.sizes"));
        // Filters the documents to pass only the documents that match the
        // specified condition
        list.add(Aggregation.match(Criteria.where(PRODUCT_STYLE).is(styleNumber)));
        list.add(Aggregation.match(Criteria.where(PRODUCT_COLOR).is(colorCode)));
        list.add(Aggregation.match(Criteria.where(PRODUCT_SIZE).is(sizeCode)));
        list.add(Aggregation.group("_id", "styleNumber").push("colors").as("colors"));
        // Passes the documents with only the specified fields
        list.add(Aggregation.project("_id", "styleNumber", "colors"));
        Aggregation aggregate = Aggregation.newAggregation(list);
        List<String> searchResults = mongoTemplate.aggregate(aggregate, COLLECTION_NAME,
                String.class).getMappedResults();

        if (CollectionUtils.isEmpty(searchResults) == false) {
            searchResult = searchResults.get(0).toString();
        }

        LOG.debug("searchResult : " + searchResult);
        if (StringUtils.isEmpty(searchResult) == false) {
            results = searchResult;
        }
        return results;
    }

    @Override
    public String findProductSizesByStyleAndColor(String styleNumber, String colorCode)
            throws Exception {
        LOG.info("Search for all sizes of product using style numbers and color");
        String results = "";
        String searchResult = null;
        List<AggregationOperation> list = new ArrayList<AggregationOperation>();
        // Deconstructs an array field from the input documents to output a
        // document for each element
        list.add(Aggregation.unwind("colors"));
        // Filters the documents to pass only the documents that match the
        // specified condition
        list.add(Aggregation.match(Criteria.where(PRODUCT_STYLE).is(styleNumber)));
        list.add(Aggregation.match(Criteria.where(PRODUCT_COLOR).is(colorCode)));
        list.add(Aggregation.group("_id", "styleNumber").push("colors").as("colors"));
        // Passes the documents with only the specified fields
        list.add(Aggregation.project("_id", "styleNumber", "colors"));
        Aggregation aggregate = Aggregation.newAggregation(list);
        List<String> searchResults = mongoTemplate.aggregate(aggregate, COLLECTION_NAME,
                String.class).getMappedResults();
        if (CollectionUtils.isEmpty(searchResults) == false) {
            searchResult = searchResults.get(0).toString();
        }

        LOG.debug("searchResult : " + searchResult);
        if (StringUtils.isEmpty(searchResult) == false) {
            results = searchResult.toString();
        }
        return results;
    }

}
