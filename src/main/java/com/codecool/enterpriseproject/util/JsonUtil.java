package com.codecool.enterpriseproject.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonUtil {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }


}
