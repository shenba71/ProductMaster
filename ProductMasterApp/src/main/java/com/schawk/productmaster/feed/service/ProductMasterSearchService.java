package com.schawk.productmaster.feed.service;

import java.util.List;

public interface ProductMasterSearchService {

    public String searchProductDetails(String styleNumber, String color, String size);

    public List<String> searchProductDetailsbyStyles(String[] styleNumbers);

}
