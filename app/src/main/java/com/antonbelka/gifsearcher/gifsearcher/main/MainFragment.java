package com.antonbelka.gifsearcher.gifsearcher.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.antonbelka.gifsearcher.gifsearcher.R;
import com.antonbelka.gifsearcher.gifsearcher.adapter.GifsAdapter;
import com.antonbelka.gifsearcher.gifsearcher.data.RemoteGifsRepository;
import com.antonbelka.gifsearcher.gifsearcher.data.model.Gif;
import com.antonbelka.gifsearcher.gifsearcher.listener.EndlessRecyclerViewScrollListener;
import com.antonbelka.gifsearcher.gifsearcher.search.SearchActivity;
import com.antonbelka.gifsearcher.gifsearcher.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements MainContract.View {

    private MainContract.UserActionsListener actionsListener;

    private GifsAdapter listAdapter;

    public MainFragment() {
        // Requires empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listAdapter = new GifsAdapter(getContext(), new ArrayList<Gif>());
        actionsListener = new MainPresenter(new RemoteGifsRepository(), this);

        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        LinearLayoutManager ll = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.gifs_list);

        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(ll);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(ll) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                actionsListener.loadMoreGifs(totalItemsCount);
            }
        });

        // Pull-to-refresh
        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                actionsListener.loadGifs();
            }
        });

        actionsListener.loadGifs();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final MenuItem ratingItem = menu.findItem(R.id.action_rating);
        ratingItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                actionsListener.setRating(item);

                return true;
            }
        });

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                actionsListener.searchGifs(query, ratingItem.isChecked());

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void setProgressIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showGifs(List<Gif> gifs) {
        listAdapter.replaceData(gifs);
    }

    @Override
    public void showMoreGifs(List<Gif> gifs) {
        listAdapter.addItems(gifs);
    }

    @Override
    public void showSearchGifs(String query, boolean rating) {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString(Constant.KEY_QUERY, query);
        bundle.putBoolean(Constant.KEY_RATING, rating);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void setRating(MenuItem item) {
        if (!item.isChecked())
            item.setChecked(true);
        else
            item.setChecked(false);
    }
}
