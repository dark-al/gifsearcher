package com.antonbelka.gifsearcher.gifsearcher.search;

import android.support.annotation.NonNull;

import com.antonbelka.gifsearcher.gifsearcher.data.GifsRepository;
import com.antonbelka.gifsearcher.gifsearcher.data.model.Gif;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link SearchFragment}), retrieves the data and updates the
 * UI as required.
 */
public class SearchPresenter implements SearchContract.UserActionsListener{

    private final GifsRepository gifsRepository;

    private final SearchContract.View searchView;

    public SearchPresenter(
            @NonNull GifsRepository gifsRepository, @NonNull SearchContract.View searchView) {
        this.gifsRepository = checkNotNull(gifsRepository, "gifsRepository cannot be null");
        this.searchView = checkNotNull(searchView, "searchView cannot be null!");
    }

    @Override
    public void searchGifs(String query, String rating) {
        searchView.setProgressIndicator(true);

        gifsRepository.searchGifs(new GifsRepository.LoadGifsCallback() {
            @Override
            public void onGifsLoaded(List<Gif> gifs) {
                searchView.setProgressIndicator(false);
                searchView.showGifs(gifs);
            }
        }, query, 0, rating);
    }

    @Override
    public void searchMoreGifs(String query, int offset, String rating) {
        gifsRepository.searchGifs(new GifsRepository.LoadGifsCallback() {
            @Override
            public void onGifsLoaded(List<Gif> gifs) {
                searchView.showMoreGifs(gifs);
            }
        }, query, offset, rating);
    }
}
