package com.antonbelka.gifsearcher.gifsearcher.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antonbelka.gifsearcher.gifsearcher.R;
import com.antonbelka.gifsearcher.gifsearcher.adapter.GifsAdapter;
import com.antonbelka.gifsearcher.gifsearcher.data.RemoteGifsRepository;
import com.antonbelka.gifsearcher.gifsearcher.data.model.Gif;
import com.antonbelka.gifsearcher.gifsearcher.listener.EndlessRecyclerViewScrollListener;
import com.antonbelka.gifsearcher.gifsearcher.util.Constant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchContract.View {

    private SearchContract.UserActionsListener actionsListener;

    private GifsAdapter listAdapter;

    private String query, rating;

    public SearchFragment() {
        // Requires empty public constructor
    }

    public static SearchFragment newInstance(String query, boolean rating) {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();

        bundle.putString(Constant.KEY_QUERY, query);
        bundle.putBoolean(Constant.KEY_RATING, rating);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listAdapter = new GifsAdapter(getContext(), new ArrayList<Gif>());
        actionsListener = new SearchPresenter(new RemoteGifsRepository(), this);

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
        query = getArguments().getString(Constant.KEY_QUERY);
        rating = "";

        try {
            query = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (getArguments().getBoolean(Constant.KEY_RATING) == true) {
            rating = "pg";
        }

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        LinearLayoutManager ll = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.gifs_list);

        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(ll);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(ll) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                actionsListener.searchMoreGifs(query, totalItemsCount, rating);
            }
        });

        // Pull-to-refresh
        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                actionsListener.searchGifs(query, rating);
            }
        });

        actionsListener.searchGifs(query, rating);

        return root;
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
}
