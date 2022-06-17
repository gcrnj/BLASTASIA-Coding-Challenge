package com.giocrnj.androidcodingchallenge.ui.picsum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import com.giocrnj.androidcodingchallenge.R;
import com.giocrnj.androidcodingchallenge.adapter.PicsumAdapter;
import com.giocrnj.androidcodingchallenge.callbacks.PicsumResultCallbacks;
import com.giocrnj.androidcodingchallenge.data.PicsumSharedPref;
import com.giocrnj.androidcodingchallenge.data.PicsumViewModel;
import com.giocrnj.androidcodingchallenge.requests.MyOkHttp;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONArray;
import org.json.JSONException;

public class PicsumActivity extends AppCompatActivity {

    RecyclerView picsumDataRecyclerview;
    MaterialButton btnPrev, btnNext;
    PicsumViewModel picsumViewModel;
    MyOkHttp myOkHttp;
    RecyclerView.LayoutManager manager;
    ProgressBar progress_horizontal;
    PicsumSharedPref picsumSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picsum);

        //Set views
        picsumDataRecyclerview = findViewById(R.id.picsumDataRecyclerview);
        btnPrev = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        progress_horizontal = findViewById(R.id.progress_horizontal);
        myOkHttp = new MyOkHttp(this, progress_horizontal);
        picsumViewModel = new ViewModelProvider(this).get(PicsumViewModel.class);
        manager = new LinearLayoutManager(getApplicationContext());
        picsumSharedPref = new PicsumSharedPref(this);
        picsumViewModel.currentPage = picsumSharedPref.getLatestPage();

        progress_horizontal.setVisibility(View.GONE);
        //Button clicks
        incrementClicks();

        //Listen to data change
        listen();

        //start request
        startRequest(getString(R.string.picsumListURL) + "?page=" + picsumViewModel.currentPage);
    }

    private void incrementClicks() {
        btnNext.setOnClickListener(view -> {
            picsumViewModel.currentPage += 1;
            picsumViewModel.url.setValue(getString(R.string.picsumListURL) + "?page=" + picsumViewModel.currentPage);
        });

        btnPrev.setOnClickListener(view -> {
            picsumViewModel.currentPage = picsumViewModel.currentPage == 1 ? 1 : picsumViewModel.currentPage - 1; //Less 1 if not 0
            picsumViewModel.url.setValue(getString(R.string.picsumListURL) + "?page=" + picsumViewModel.currentPage);
        });
    }

    private void listen() {
        picsumViewModel.adapter.observe(this, this::setAdapter);

        picsumViewModel.url.observe(this, this::startRequest);
    }

    private void setAdapter(PicsumAdapter picsumAdapter){
        if(picsumAdapter.getItemCount() == 0){
            btnPrev.performClick(); //Go back because there is no current value, meaning that current request is the last page.
            return;
        }
        picsumDataRecyclerview.setAdapter(picsumAdapter);
        picsumDataRecyclerview.setLayoutManager(manager);
        picsumDataRecyclerview.setItemAnimator(new DefaultItemAnimator());
        progress_horizontal.setVisibility(View.GONE);
    }

    private void startRequest(String url) {
        progress_horizontal.setVisibility(View.VISIBLE);
        myOkHttp.start(url, new PicsumResultCallbacks() {
            @Override
            public void onSuccess(JSONArray result) {
                //Set Adapter
                PicsumAdapter picsumAdapter = new PicsumAdapter(PicsumActivity.this, result);
                picsumViewModel.adapter.setValue(picsumAdapter); //Change the adapter's value to update it
                //Set value in shared pref
                picsumSharedPref.setPreviousLoaded(result.toString());
                picsumSharedPref.setLatestPage(picsumViewModel.currentPage);
            }

            @Override
            public void onError(String error) throws JSONException {
                //Set Adapter by last result
                PicsumAdapter picsumAdapter = new PicsumAdapter(PicsumActivity.this, new JSONArray(picsumSharedPref.getPreviouslyLoaded()));
                setAdapter(picsumAdapter);
                Snackbar.make(getWindow().getDecorView().getRootView(), error, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}