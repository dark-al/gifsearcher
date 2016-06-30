package com.antonbelka.gifsearcher.gifsearcher.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.antonbelka.gifsearcher.gifsearcher.R;
import com.antonbelka.gifsearcher.gifsearcher.util.Constant;

public class SearchActivity extends AppCompatActivity {

    private String query;
    private boolean rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        query = getIntent().getStringExtra(Constant.KEY_QUERY);
        rating = getIntent().getBooleanExtra(Constant.KEY_RATING, false);

        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // The title of the view will be the term the user searched for
            ab.setDisplayShowTitleEnabled(true);
            ab.setTitle(query);

            ab.setDisplayHomeAsUpEnabled(true);
        }

        if (null == savedInstanceState) {
            initFragment(SearchFragment.newInstance(query, rating));
        }
    }

    private void initFragment(Fragment searchFragment) {
        // Add the NotesFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, searchFragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
