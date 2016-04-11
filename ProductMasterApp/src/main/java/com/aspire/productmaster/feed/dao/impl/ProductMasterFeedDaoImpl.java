package com.aspire.productmaster.feed.dao.impl;

import java.util.List;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.aspire.productmaster.config.service.SpringMongoConfigService;
import com.aspire.productmaster.feed.dao.ProductMasterFeedDao;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkUpdateRequestBuilder;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteRequestBuilder;
import com.mongodb.BulkWriteResult;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Repository
public class ProductMasterFeedDaoImpl implements ProductMasterFeedDao {

	@Autowired
	private SpringMongoConfigService springMongoConfigService;

	@Autowired
	private MongoProperties mongoProperties;

	private final Logger log = LoggerFactory.getLogger(ProductMasterFeedDaoImpl.class);

	private static final String COLLECTION_NAME = "ProductMaster";

	private MongoTemplate mongoTemplate;

	@Override
	public String saveInputFeed(JSONArray inputFeed) {
		String response = null;
		try {
			mongoTemplate = springMongoConfigService.getMongoTemplate();
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
						.find(new BasicDBObject("Product style", product).append("Product color", color));

				// get hold of upsert operation from bulkWriteRequestBuilder

				BulkUpdateRequestBuilder updateReq = bulkWriteRequestBuilder.upsert();
				updateReq.update(object);
			}
			// execute bulk operation on mycol collection
			log.debug("Started inserting the json Inputs");
			BulkWriteResult result = bulkWriteOperation.execute();
			log.debug("Number of inserted documents=====>" + result.getInsertedCount());
			log.debug("Number of updated documents=====>" + result.getModifiedCount());
			log.debug("Number of Matched documents=====>" + result.getMatchedCount());
			log.debug("Number of Upserted documents=====>" + (result.getUpserts()==null?0:result.getUpserts().size()));
			log.debug("Completed insertion of inputs..");
			response = "No.Of Inserted Documents"+(result.getUpserts()==null?0:result.getUpserts().size())+"   "+"No.Of Updated Documents"+result.getModifiedCount();
		} catch (Exception e) {
			e.printStackTrace();
			response = "Insertion Failed";
		}

		return response;
	}

	@Override
	public String searchFeed(String style, String color, String size) throws Exception {
		Query query = new Query();
		String results = null;
		query.addCriteria(Criteria.where("Product style").is(style));
		if (color != null && !color.isEmpty()) {
			query.addCriteria(Criteria.where("Product color").is(color));
		}
		if (size != null && !size.isEmpty()) {
			query.addCriteria(Criteria.where("Product size").is(size));
		}
		log.debug("Search Query" + query);
		mongoTemplate = springMongoConfigService.getMongoTemplate();
		List<String> searchResults = mongoTemplate.find(query, String.class, COLLECTION_NAME);
		if (CollectionUtils.isEmpty(searchResults) == false) {
			results = searchResults.toString();
		}
		else{
			results = "No Records Found";
		}
		return results;
	}

	@Override
	public List<String> searchFeedByStyles(String[] styleNumbers) throws Exception {
		log.debug("Search for multiple style numbers");
		Query query = new Query();
		query.addCriteria(Criteria.where("Product style").in(styleNumbers));
		mongoTemplate = springMongoConfigService.getMongoTemplate();
		List<String> jsonFeeds = mongoTemplate.find(query, String.class, COLLECTION_NAME);
		return jsonFeeds;
	}

}
