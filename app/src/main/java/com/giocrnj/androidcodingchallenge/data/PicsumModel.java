package com.giocrnj.androidcodingchallenge.data;
import org.json.JSONObject;

public class PicsumModel {

    public String id;
    public String author;
    public String width;
    public String height;
    public String url;
    public String download_url;

    public PicsumModel(JSONObject picsumData) {
        this.id = picsumData.optString("id");
        this.author = picsumData.optString("author");
        this.width = picsumData.optString("width");
        this.height = picsumData.optString("height");
        this.url = picsumData.optString("url");
        this.download_url = picsumData.optString("download_url");
    }
}
