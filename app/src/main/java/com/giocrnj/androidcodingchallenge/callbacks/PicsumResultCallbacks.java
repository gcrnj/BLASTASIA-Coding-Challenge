package com.giocrnj.androidcodingchallenge.callbacks;

import org.json.JSONArray;
import org.json.JSONException;

public interface PicsumResultCallbacks {
    void onSuccess(JSONArray result);
    void onError(String error) throws JSONException;
}
