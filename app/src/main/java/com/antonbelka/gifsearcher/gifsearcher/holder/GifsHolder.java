package com.antonbelka.gifsearcher.gifsearcher.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.antonbelka.gifsearcher.gifsearcher.R;

public class GifsHolder extends RecyclerView.ViewHolder {

    public ProgressBar progress;
    public ImageView image;
    public TextView rating, date, trended;

    public GifsHolder(View itemView) {
        super(itemView);

        progress = (ProgressBar) itemView.findViewById(R.id.gif_progress);
        image = (ImageView) itemView.findViewById(R.id.gif_image);
        rating = (TextView) itemView.findViewById(R.id.gif_rating);
        date = (TextView) itemView.findViewById(R.id.gif_date);
        trended = (TextView) itemView.findViewById(R.id.gif_trended);
    }
}
