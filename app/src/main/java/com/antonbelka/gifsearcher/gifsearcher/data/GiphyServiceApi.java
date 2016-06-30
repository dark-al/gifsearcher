package com.antonbelka.gifsearcher.gifsearcher.data;

import com.antonbelka.gifsearcher.gifsearcher.data.model.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyServiceApi {

    @GET("v1/gifs/trending?")
    Call<Data> getGifs(@Query("limit") int limit,
                       @Query("offset") int offset,
                       @Query("api_key") String api_key);

    @GET("v1/gifs/search?")
    Call<Data> searchGifs(@Query("q") String q,
                          @Query("limit") int limit,
                          @Query("offset") int offset,
                          @Query("rating") String rating,
                          @Query("api_key") String api_key);

}
