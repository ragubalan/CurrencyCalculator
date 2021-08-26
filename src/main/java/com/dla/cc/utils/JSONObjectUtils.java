package com.dla.cc.utils;

import org.json.simple.JSONObject;

/**
 * Utilities class for JSONObject.
 */
public final class JSONObjectUtils {
    private JSONObjectUtils() {
    }

    public static String createErrorJson(final String code, final String message) {
        JSONObject errorJson = new JSONObject();
        errorJson.put("errorCode", code);
        errorJson.put("errorMessage", message);
        return JSONObject.toJSONString(errorJson);
    }
}
