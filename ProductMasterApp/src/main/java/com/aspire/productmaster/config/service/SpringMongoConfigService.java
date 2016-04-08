package com.aspire.productmaster.config.service;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;

@Service
public class SpringMongoConfigService {

	@Inject
	MongoProperties mongoProperties;

	public MongoTemplate getMongoTemplate() throws Exception {

		MongoTemplate mongoTemplate = new MongoTemplate(
				new MongoClient(mongoProperties.getHost(), mongoProperties.getPort()), mongoProperties.getDatabase());
		return mongoTemplate;

	}

}
