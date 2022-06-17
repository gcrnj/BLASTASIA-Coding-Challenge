package com.giocrnj.androidcodingchallenge.ui.picsum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.giocrnj.androidcodingchallenge.R;
import com.giocrnj.androidcodingchallenge.callbacks.PicsumResultCallbacks;
import com.giocrnj.androidcodingchallenge.data.PicsumModel;
import com.giocrnj.androidcodingchallenge.requests.MyOkHttp;

import org.json.JSONArray;
import org.json.JSONException;

public class PicsumViewDetailsActivity extends AppCompatActivity {

    Intent intent;
    String author, url, downloadUrl;
    ImageView imgPicsum;
    TextView txtAuthor, txtUrl, txtDownloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picsum_view_details);

        intent = getIntent();
        if (!intent.hasExtra("url") ||!intent.hasExtra("download_url") || !intent.hasExtra("author")) {
            Toast.makeText(this, getString(R.string.no_current_data), Toast.LENGTH_SHORT).show();
            finish();
        }
        author = intent.getStringExtra("author");
        url = intent.getStringExtra("url");
        downloadUrl = intent.getStringExtra("download_url");
        imgPicsum = findViewById(R.id.imgPicsum);
        txtUrl = findViewById(R.id.txtUrl);
        txtDownloadUrl = findViewById(R.id.txtDownloadUrl);
        txtAuthor = findViewById(R.id.txtAuthor);

        //Set image and text
        initiateActivity();
        //Set clicks
        initiateClicks();
    }

    private void initiateClicks() {
        txtUrl.setOnClickListener(view -> openInBrowser(url));
        txtDownloadUrl.setOnClickListener(view -> openInBrowser(downloadUrl));
    }

    private void initiateActivity() {
        txtAuthor.setText(author);
        txtUrl.setText(getString(R.string.link_underlined, url));
        txtDownloadUrl.setText(getString(R.string.link_underlined, downloadUrl));
        Glide
                .with(this)
                .load(downloadUrl)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .into(imgPicsum);
    }

    private void openInBrowser(String passedURL){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(passedURL));
        startActivity(browserIntent);
    }
}