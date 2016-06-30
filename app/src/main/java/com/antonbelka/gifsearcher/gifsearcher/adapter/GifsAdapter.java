package com.antonbelka.gifsearcher.gifsearcher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antonbelka.gifsearcher.gifsearcher.R;
import com.antonbelka.gifsearcher.gifsearcher.data.model.Gif;
import com.antonbelka.gifsearcher.gifsearcher.holder.GifsHolder;
import com.antonbelka.gifsearcher.gifsearcher.util.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GifsAdapter extends RecyclerView.Adapter<GifsHolder> {

    private Context context;
    private List<Gif> gifs;

    public GifsAdapter(Context context, List<Gif> gifs) {
        this.context = context;
        setList(gifs);
    }

    @Override
    public GifsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View gifView = inflater.inflate(R.layout.item_gif, parent, false);

        return new GifsHolder(gifView);
    }

    @Override
    public void onBindViewHolder(final GifsHolder holder, int position) {
        Gif gif = gifs.get(position);

        holder.rating.setText(context.getString(R.string.rating) + gif.getRating());
        holder.date.setText(context.getString(R.string.date) + gif.getImportDate());

        if (!gif.getTrendingDatetime().equals(Constant.DEFAULT_TIME))
            holder.trended.setText(R.string.trended);

        Glide.with(holder.image.getContext())
                .load(gif.getUrl())
//                .asBitmap()
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GifDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progress.setVisibility(View.INVISIBLE);

                        return false;
                    }
                })
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }

    public void addItems(List<Gif> items) {
        gifs.addAll(items);
        notifyDataSetChanged();
    }

    public void replaceData(List<Gif> gifs) {
        setList(gifs);
        notifyDataSetChanged();
    }

    public void setList(List<Gif> gifs) {
        this.gifs = checkNotNull(gifs);
    }
}