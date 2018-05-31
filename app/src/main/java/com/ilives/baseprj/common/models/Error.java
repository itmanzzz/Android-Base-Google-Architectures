package com.ilives.baseprj.common.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.models
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 16:12
 * -------------^_^-------------
 **/
public class Error {
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    /**
     * error": { "email": ["メールアドレスは、有効なメールアドレス形式で指定してください"] }
     */
    @SerializedName("error")
    @Expose
    private JsonObject errorObj;

    public Error(JsonObject errorObj) {
        this.errorObj = errorObj;
    }


    public JsonObject getErrorObj() {
        return errorObj;
    }

    public void setErrorObj(JsonObject errorObj) {
        this.errorObj = errorObj;
    }

    /**
     * Checking message or object message response and give error message
     *
     * @return
     */
    public String getFirstMessage() {
        int first_index = 0;
        if (this.getErrorObj().has(KEY_EMAIL)) {
            return this.getErrorObj()
                    .getAsJsonArray(KEY_EMAIL)
                    .get(first_index).getAsString();
        }

        if (this.getErrorObj().has(KEY_PASSWORD)) {
            return this.getErrorObj()
                    .getAsJsonArray(KEY_PASSWORD)
                    .get(first_index).getAsString();
        }
        return "Unexpected error";
    }
}
