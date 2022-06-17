package com.giocrnj.androidcodingchallenge.ui.animation;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;

public class MyAnimation {
    Activity activity;
    public MyAnimation(Activity activity){
        this.activity = activity;
    }

    public void startActivity(Intent intent, View view){
        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(
                        activity,
                        Pair.create(view, view.getTransitionName()));
        activity.startActivity(intent, options.toBundle());
    }
}
