package com.schawk.productmaster.feed.dao;

import java.util.List;

import org.json.JSONArray;


public interface ProductMasterFeedDao {

	public String saveInputFeed(JSONArray jsonArray) throws Exception;

	public String searchFeed(String style, String color, String size) throws Exception;

	public List<String> searchFeedByStyles(String[] styleNumbers) throws Exception;

}
