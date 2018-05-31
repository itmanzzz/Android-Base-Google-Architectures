package com.ilives.baseprj.common.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("message")
    @Expose
    private List<String> message;

    public Error(int code, String... errors) {
        this.code = code;
        message = new ArrayList<>();
        if (errors != null) {
            for (String error:errors) {
                message.add(error);
            }
        }
    }

    /**
     *
     * @return
     * The code
     */
    public int getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The message
     */
    public List<String> getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getFirstMessage() {
        if (message != null && !message.isEmpty()) {
            return message.get(0);
        }
        return "Unexpected error";
    }
}
