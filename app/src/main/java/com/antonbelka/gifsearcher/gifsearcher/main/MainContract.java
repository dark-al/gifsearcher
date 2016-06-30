package com.antonbelka.gifsearcher.gifsearcher.main;

import android.view.MenuItem;

import com.antonbelka.gifsearcher.gifsearcher.data.model.Gif;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MainContract {
    interface View {

        void setProgressIndicator(boolean active);

        void showGifs(List<Gif> gifs);

        void showMoreGifs(List<Gif> gifs);

        void showSearchGifs(String query, boolean rating);

        void setRating(MenuItem item);
    }

    interface UserActionsListener {

        void loadGifs();

        void loadMoreGifs(int offset);

        void searchGifs(String query, boolean rating);

        void setRating(MenuItem item);
    }
}
