package com.cookandroid.blahblar;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoListViewHolder extends RecyclerView.ViewHolder {

    public ImageView img;
    public TextView name;

    public VideoListViewHolder(View itemView) {
        super(itemView);

        img = (ImageView) itemView.findViewById(R.id.video_img);
        name = (TextView) itemView.findViewById(R.id.video_name);

    }
}