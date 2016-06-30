package com.antonbelka.gifsearcher.gifsearcher.main;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.antonbelka.gifsearcher.gifsearcher.data.GifsRepository;
import com.antonbelka.gifsearcher.gifsearcher.data.model.Gif;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link MainFragment}), retrieves the data and updates the
 * UI as required.
 */
public class MainPresenter implements MainContract.UserActionsListener {

    private final GifsRepository gifsRepository;

    private final MainContract.View mainView;

    public MainPresenter(
            @NonNull GifsRepository gifsRepository, @NonNull MainContract.View mainView) {
        this.gifsRepository = checkNotNull(gifsRepository, "gifsRepository cannot be null");
        this.mainView = checkNotNull(mainView, "mainView cannot be null!");
    }

    @Override
    public void loadGifs() {
        mainView.setProgressIndicator(true);

        gifsRepository.getGifs(new GifsRepository.LoadGifsCallback() {
            @Override
            public void onGifsLoaded(List<Gif> gifs) {
                mainView.setProgressIndicator(false);
                if (gifs != null) {
                    mainView.showGifs(gifs);
                }
            }
        }, 0);
    }

    @Override
    public void loadMoreGifs(int offset) {
        gifsRepository.getGifs(new GifsRepository.LoadGifsCallback() {
            @Override
            public void onGifsLoaded(List<Gif> gifs) {
                mainView.showMoreGifs(gifs);
            }
        }, offset);

    }

    @Override
    public void searchGifs(String query, boolean rating) {
        mainView.showSearchGifs(query, rating);
    }

    @Override
    public void setRating(MenuItem item) {
        mainView.setRating(item);
    }
}
