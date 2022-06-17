package com.giocrnj.androidcodingchallenge.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.giocrnj.androidcodingchallenge.R;
import com.giocrnj.androidcodingchallenge.data.PicsumModel;
import com.giocrnj.androidcodingchallenge.ui.animation.MyAnimation;
import com.giocrnj.androidcodingchallenge.ui.picsum.PicsumViewDetailsActivity;

import org.json.JSONArray;

public class PicsumAdapter extends RecyclerView.Adapter<PicsumAdapter.MyViewHolder> {

    private final JSONArray picsumModels;
    private final Activity activity;
    private final MyAnimation myAnimation;

    public PicsumAdapter(Activity activity, JSONArray picsumModels){
        this.activity = activity;
        this.picsumModels = picsumModels;
        this.myAnimation = new MyAnimation(activity);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pictures, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PicsumModel picsumModel = new PicsumModel(picsumModels.optJSONObject(position));
        holder.txtAuthor.setText(picsumModel.author);
        Glide
                .with(activity)
                .load(picsumModel.download_url)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.imgPicsum);
        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(activity, PicsumViewDetailsActivity.class)
                    .putExtra("url", picsumModel.url)
                    .putExtra("download_url", picsumModel.download_url)
                    .putExtra("author", picsumModel.author);
            myAnimation.startActivity(intent, holder.imgPicsum);
        });
    }

    @Override
    public int getItemCount() {
        return picsumModels.length();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView txtAuthor;
        private final ImageView imgPicsum;
        private final CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtAuthor = itemView.findViewById(R.id.txtAuthor);
            this.imgPicsum = itemView.findViewById(R.id.imgPicsum);
            this.cardView = itemView.findViewById(R.id.cardView);

        }
    }


}
