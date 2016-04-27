package com.schawk.productmaster.web.rest.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.schawk.productmaster.util.StringUtil;
import com.schawk.productmaster.web.rest.errors.MissingParameterException;

/**
 * Utility class for operations in controller
 * @author shenbagaganesh.param
 *
 */
public class ProductMasterRestUtil {

    /**
     * recieves the map of request parameters and returns the map with camelCase keys
     * 
     * @param params
     * @return mapWithCamelCaseKeys
     * @throws Exception
     */
    public static Map<String, String> checkAndconvertParamsToCamelCase(Map<String, String> params)
            throws Exception {
        Map<String, String> mapWithCamelCaseKeys = new HashMap<String, String>();
        if (CollectionUtils.isEmpty(params)) {
            throw new MissingParameterException("Parameters Cannot be Empty");
        } else {
            params.forEach((key, value) -> {
                mapWithCamelCaseKeys.put(StringUtil.getCamelCase(key), value);
            });

        }
        return mapWithCamelCaseKeys;
    }

}
