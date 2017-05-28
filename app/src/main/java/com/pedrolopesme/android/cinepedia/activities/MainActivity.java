package com.pedrolopesme.android.cinepedia.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.adapters.MoviesRecyclerViewAdapter;
import com.pedrolopesme.android.cinepedia.clickListeners.MovieItemClickListener;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {

    private static final int NUM_LIST_ITEMS = 100;
    private static final int NUM_COLUMNS = 2;

    // Movies Recycler View
    private RecyclerView mMoviesRecyclerView;

    // Movies Recycler View Adapter
    private MoviesRecyclerViewAdapter mMoviesRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayoutManager layoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        mMoviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(NUM_LIST_ITEMS, this);

        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.rc_movies);
        mMoviesRecyclerView.setLayoutManager(layoutManager);
        mMoviesRecyclerView.setHasFixedSize(false);
        mMoviesRecyclerView.setAdapter(mMoviesRecyclerViewAdapter);
    }

    @Override
    public void onMovieItemClick(String movieName) {
        openMovieDetailActivity(movieName);
    }

    /**
     * Open Movie Item Detail
     *
     * @param movieName
     */
    protected void openMovieDetailActivity(String movieName) {
        Context context = MainActivity.this;
        Class destination = MovieDetailActivity.class;
        Intent startActivityIntent = new Intent(context, destination);

        startActivityIntent.putExtra(Intent.EXTRA_TEXT, movieName);
        startActivity(startActivityIntent);
    }
}
