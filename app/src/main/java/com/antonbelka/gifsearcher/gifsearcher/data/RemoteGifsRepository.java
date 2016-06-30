package com.antonbelka.gifsearcher.gifsearcher.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.antonbelka.gifsearcher.gifsearcher.data.model.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation to load gifs from the a data source.
 */
public class RemoteGifsRepository implements GifsRepository {

    private final static int COUNT = 10;

    @Override
    public void getGifs(@NonNull final LoadGifsCallback callback, int offset) {
        GiphyApi.getGiphyServiceApi().getGifs(COUNT, offset, GiphyApi.API_KEY).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                callback.onGifsLoaded(response.body().getData());
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                callback.onGifsLoaded(null);
                Log.e("Error", t.toString());
            }
        });
    }

    @Override
    public void searchGifs(@NonNull final LoadGifsCallback callback, String query, int offset, String rating) {
        GiphyApi.getGiphyServiceApi().searchGifs(query, COUNT, offset, rating, GiphyApi.API_KEY).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                callback.onGifsLoaded(response.body().getData());
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                callback.onGifsLoaded(null);
                Log.e("Error", t.toString());
            }
        });
    }
}
