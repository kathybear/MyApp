package com.ypf.myapp.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ypf on 2016/1/11.
 */
public class JSONUtil {
    public static JSONArray joinJSONArrayFirst(JSONObject mData, JSONArray array) {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(mData);
            for (int i = 0 ,len = array.length(); i < len; i++) {
                JSONObject obj1 = (JSONObject) array.get(i);
                jsonArray.put(obj1);
            }
        } catch (Exception ignored) {
        }
        return jsonArray;
    }

    public static JSONArray joinJSONArray(JSONArray mData, JSONArray array) {
        try {
            int len = array.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj1 = (JSONObject) array.get(i);
                mData.put(obj1);
            }
        } catch (Exception ignored) {
        }
        return mData;
    }

    public static JSONArray delJSONArrayItem(JSONArray array, JSONObject jsonItem) {
        JSONArray mData = new JSONArray();
        try {
            int len = array.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj1 = (JSONObject) array.get(i);
                if(!obj1.equals(jsonItem))
                    mData.put(obj1);
            }
        } catch (Exception ignored) {
        }
        return mData;
    }

    /**
     * 将JSON字符串转换为Map
     */
    public static Map<String, Object> getMap(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            @SuppressWarnings("unchecked")
            Iterator<String> keyInter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyInter.hasNext()) {
                key = keyInter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getStringFromJson(JSONObject jsonObject, String fieldName, String defaultValue)
    {
        try {
            String v = jsonObject.getString(fieldName);
            if(StringUtil.isEmpty(v))
                return defaultValue;
            else
                return v;
        } catch (Exception ignored) {
        }
        return defaultValue;
    }

    public static String getStringFromJson(String jsonstr, String fieldName, String defaultValue)
    {
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            String v = jsonObject.getString(fieldName);
            if(StringUtil.isEmpty(v))
                return defaultValue;
            else
                return v;
        } catch (Exception ignored) {
        }
        return defaultValue;
    }

    public static int getIntFromJson(JSONObject jsonObject, String fieldName, int defaultValue)
    {
        try {
            String v = jsonObject.getString(fieldName);
            if(StringUtil.isEmpty(v))
                return defaultValue;
            else
                return jsonObject.getInt(fieldName);
        } catch (Exception ignored) {
        }
        return defaultValue;
    }

    public static int getIntFromJson(String jsonstr, String fieldName, int defaultValue)
    {
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            String v = jsonObject.getString(fieldName);
            if(StringUtil.isEmpty(v))
                return defaultValue;
            else
                return jsonObject.getInt(fieldName);
        } catch (Exception ignored) {
        }
        return defaultValue;
    }

    public static double getDoubleFromJson(JSONObject jsonObject, String fieldName, double defaultValue)
    {
        try {
            String v = jsonObject.getString(fieldName);
            if(StringUtil.isEmpty(v))
                return defaultValue;
            else
                return jsonObject.getDouble(fieldName);
        } catch (Exception ignored) {
        }
        return defaultValue;
    }

    public static double getDoubleFromJson(String jsonstr, String fieldName, double defaultValue)
    {
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            String v = jsonObject.getString(fieldName);
            if(StringUtil.isEmpty(v))
                return defaultValue;
            else
                return jsonObject.getDouble(fieldName);
        } catch (Exception ignored) {
        }
        return defaultValue;
    }

    public static boolean getBoolFromJson(JSONObject jsonObject, String fieldName)
    {
        try {
            String v = jsonObject.getString(fieldName);
            return !StringUtil.isEmpty(v) && jsonObject.getBoolean(fieldName);
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean getBoolFromJson(String jsonstr, String fieldName)
    {
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            String v = jsonObject.getString(fieldName);
            return !StringUtil.isEmpty(v) && jsonObject.getBoolean(fieldName);
        } catch (Exception ignored) {
        }
        return false;
    }
}
