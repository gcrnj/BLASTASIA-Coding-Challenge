package com.giocrnj.androidcodingchallenge.requests;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.giocrnj.androidcodingchallenge.callbacks.PicsumResultCallbacks;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyOkHttp {

    private final Activity activity;
    private final ProgressBar progressBar;

    public MyOkHttp(Activity activity, ProgressBar progressBar) {
        this.activity = activity;
        this.progressBar = progressBar;
    }

    private final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(7, TimeUnit.SECONDS)
            .readTimeout(7, TimeUnit.SECONDS)
            .writeTimeout(7, TimeUnit.SECONDS)
            .build();

    public void start(String url, PicsumResultCallbacks picsumResultCallbacks) {
        //By default, the method is GET
        String method = "GET";
        Request request = new Request.Builder()
                .url(url)
                .method(method, null)
                .build();
        Thread thread = new Thread(() -> {
            // call send message here
            try {
                Response response = client.newCall(request).execute();
                activity.runOnUiThread(() -> {
                    try {
                        if (response.isSuccessful()) {
                            String responseBody = response.body().string();
                            Log.d("responseBody", responseBody);
                            //Return success
                            picsumResultCallbacks.onSuccess(new JSONArray(responseBody));
                        } else {
                            //Return error
                            picsumResultCallbacks.onError(response.message());
                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        //Dismiss Progressbar
                        progressBar.setVisibility(View.GONE);
                        //Get Message
                        String message = e.getMessage() == null ? "Something went wrong" : e.getMessage();
                        //Show Error
                        try {
                            picsumResultCallbacks.onError(message);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                    }

                });

            } catch (Exception e) {
                //Dismiss Progressbar
                progressBar.setVisibility(View.GONE);
                //Get Message
                String message = e.getMessage() == null ? "Something went wrong" : e.getMessage();
                //Show Error
                try {
                    picsumResultCallbacks.onError(message);
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }

        });
        //Start the request
        thread.start();


    }

}
