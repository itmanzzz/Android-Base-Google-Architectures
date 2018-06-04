package com.ilives.baseprj.common.util.facebook;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.util.facebook
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 6/4/18
 * ❖ Time: 10:46
 * -------------^_^-------------
 **/
public interface FbGraphCallback extends GraphRequest.GraphJSONObjectCallback {
    void onCompleted(JSONObject data, GraphResponse res);
}
