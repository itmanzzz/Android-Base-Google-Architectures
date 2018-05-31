package com.ilives.baseprj.data.core;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;


public class JSONRequestBodyBuilder {

    private JSONObject mJsonObject = new JSONObject();

    public JSONRequestBodyBuilder addParams(String key, Object value) {
        if (key != null && value != null) {
            try {
                mJsonObject.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public RequestBody create() {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), mJsonObject.toString());
    }

    public JSONObject get () {
        return mJsonObject;
    }

    public JSONRequestBodyBuilder assignJsonString(String json) {
        JSONObject data = null;
        try {
            data = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if( data != null)
            mJsonObject = data;
        return this;
    }

    public JSONRequestBodyBuilder assignJsonObject(JSONObject object) {
        if (object != null)
            mJsonObject = object;
        return this;
    }

    public JSONRequestBodyBuilder removeParams(String key) {
        mJsonObject.remove(key);
        return this;
    }
}
