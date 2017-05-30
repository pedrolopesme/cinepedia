package com.pedrolopesme.android.cinepedia.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.adapters.MoviesRecyclerViewAdapter;
import com.pedrolopesme.android.cinepedia.clickListeners.MovieItemClickListener;
import com.pedrolopesme.android.cinepedia.dao.DaoFactory;
import com.pedrolopesme.android.cinepedia.dao.MoviesDao;
import com.pedrolopesme.android.cinepedia.dao.http.HttpDaoFactory;
import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.pedrolopesme.android.cinepedia.domain.Sorting;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {

    private static final int NUM_COLUMNS = 2;
    private DaoFactory daoFactory;

    // MoviesDao Recycler View
    private RecyclerView mMoviesRecyclerView;

    // MoviesDao Recycler View Adapter
    private MoviesRecyclerViewAdapter mMoviesRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String baseUrl = getString(R.string.moviedb_base_url);
        String apiKey = getString(R.string.moviedb_api_key);
        daoFactory = new HttpDaoFactory(baseUrl, apiKey);

        GridLayoutManager layoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        mMoviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(getApplicationContext(), this);

        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.rc_movies);
        mMoviesRecyclerView.setLayoutManager(layoutManager);
        mMoviesRecyclerView.setHasFixedSize(false);
        mMoviesRecyclerView.setAdapter(mMoviesRecyclerViewAdapter);
        refreshMoviesPopular();
    }

    @Override
    public void onMovieItemClick(String movieName) {
        openMovieDetailActivity(movieName);
    }

    private void refreshMoviesPopular() {
        new MoviesAsyncTask().execute(Sorting.POPULAR);
    }

    private void refreshMoviesTopRated() {
        new MoviesAsyncTask().execute(Sorting.TOP_RATED);
    }

    private void refreshMovies(final List<Movie> movies) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMoviesRecyclerViewAdapter.refresh(movies);
            }
        });
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

    /**
     * Movies fetcher async task
     */
    public class MoviesAsyncTask extends AsyncTask<Sorting, Void, Void> {

        private MoviesDao moviesDao = daoFactory.getMoviesDao();

        @Override
        protected Void doInBackground(Sorting... params) {
            Sorting sorting = params[0];
            refresh(sorting);
            return null;
        }

        private void refresh(Sorting sorting) {
            switch (sorting) {
                case POPULAR:
                    refreshMovies(moviesDao.getPopular());
                case TOP_RATED:
                    refreshMovies(moviesDao.getTopRated());
            }
        }
    }
}
