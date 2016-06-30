package com.antonbelka.gifsearcher.gifsearcher.search;

import com.antonbelka.gifsearcher.gifsearcher.data.model.Gif;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface SearchContract {
    interface View {

        void setProgressIndicator(boolean active);

        void showGifs(List<Gif> gifs);

        void showMoreGifs(List<Gif> gifs);
    }

    interface UserActionsListener {

        void searchGifs(String query, String rating);

        void searchMoreGifs(String query, int offset, String rating);
    }
}
