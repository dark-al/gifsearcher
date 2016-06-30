package com.antonbelka.gifsearcher.gifsearcher.data;

import android.support.annotation.NonNull;

import com.antonbelka.gifsearcher.gifsearcher.data.model.Gif;

import java.util.List;

/**
 * Main entry point for accessing gifs data.
 */
public interface GifsRepository {

    interface LoadGifsCallback {

        void onGifsLoaded(List<Gif> gifs);
    }

    void getGifs(@NonNull LoadGifsCallback callback, int offset);

    void searchGifs(@NonNull LoadGifsCallback callback, String query, int offset, String rating);
}