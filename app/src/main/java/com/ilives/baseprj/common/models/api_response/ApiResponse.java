package com.ilives.baseprj.common.models.api_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ilives.baseprj.common.models.Error;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.models.api_response
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 17:02
 * -------------^_^-------------
 **/
public class ApiResponse<Model> {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Model data;
    @SerializedName("error")
    @Expose
    private Error error;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Model getData() {
        return data;
    }

    public void setData(Model data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
