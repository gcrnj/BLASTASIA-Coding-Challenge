package com.giocrnj.androidcodingchallenge.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.giocrnj.androidcodingchallenge.R;

import org.json.JSONArray;
import org.json.JSONException;

public class PicsumSharedPref {

    Activity activity;
    SharedPreferences sharedPreferences;
    JSONArray previouslyLoaded = new JSONArray();
    String loadedKey;
    int latestPage;
    String pageKey;

    public PicsumSharedPref(Activity activity) {
        this.activity = activity;
        this.sharedPreferences = activity.getSharedPreferences(activity.getString(R.string.picsumPrefKey), Context.MODE_PRIVATE);
        this.loadedKey = activity.getString(R.string.loadedKey);
        this.pageKey = activity.getString(R.string.pageKey);
        try {
            this.previouslyLoaded = new JSONArray(sharedPreferences.getString(loadedKey, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.latestPage = sharedPreferences.getInt(pageKey, 1);
    }

    public void setPreviousLoaded(String newValue){
        sharedPreferences.edit().putString(loadedKey, newValue).apply();
    }

    public String getPreviouslyLoaded(){
        return sharedPreferences.getString(loadedKey, "");
    }

    public void setLatestPage(int latestPage){
        sharedPreferences.edit().putInt(pageKey, latestPage).apply();
    }

    public int getLatestPage(){
        return sharedPreferences.getInt(pageKey, 1);
    }
}
