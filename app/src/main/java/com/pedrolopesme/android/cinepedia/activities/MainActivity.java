package com.pedrolopesme.android.cinepedia.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.adapters.MoviesRecyclerViewAdapter;
import com.pedrolopesme.android.cinepedia.clickListeners.MovieItemClickListener;
import com.pedrolopesme.android.cinepedia.dao.DaoFactory;
import com.pedrolopesme.android.cinepedia.dao.MoviesDao;
import com.pedrolopesme.android.cinepedia.dao.http.HttpDaoFactory;
import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.pedrolopesme.android.cinepedia.domain.Sorting;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {

    // Log tag description
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int NUM_COLUMNS_VERTICAL = 2;
    private static final int NUM_COLUMNS_HORIZONTAL = 3;
    private DaoFactory daoFactory;

    // MoviesDao Recycler View Adapter
    private MoviesRecyclerViewAdapter mMoviesRecyclerViewAdapter;

    // Layout manager
    GridLayoutManager layoutManager;

    // Default loading
    private ProgressBar mLoadingProgressBar;

    // Default Toast
    Toast mToast;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Creating Main Activity!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String baseUrl = getString(R.string.moviedb_base_url);
        String apiKey = getString(R.string.moviedb_api_key);
        daoFactory = new HttpDaoFactory(baseUrl, apiKey);

        layoutManager = new GridLayoutManager(this, NUM_COLUMNS_VERTICAL);
        mMoviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(getApplicationContext(), this);

        RecyclerView mMoviesRecyclerView = (RecyclerView) findViewById(R.id.rc_movies);
        mMoviesRecyclerView.setLayoutManager(layoutManager);
        mMoviesRecyclerView.setHasFixedSize(false);
        mMoviesRecyclerView.setAdapter(mMoviesRecyclerViewAdapter);

        mLoadingProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        refreshMoviesPopular();
        Log.d(LOG_TAG, "Main Activity created successfully!");
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        Log.d(LOG_TAG, "Main menu inflated successfully!");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item == null) {
            Log.d(LOG_TAG, "NULL item selected");
            return super.onOptionsItemSelected(null);
        }

        switch (item.getItemId()) {
            case R.id.action_sort_popular:
                Log.d(LOG_TAG, "Sort popular movies selected");
                refreshMoviesPopular();
                return true;
            case R.id.action_sort_rated:
                Log.d(LOG_TAG, "Sort top rated movies selected");
                refreshMoviesTopRated();
                return true;
            default:
                Log.w(LOG_TAG, "None menu option was identified");
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMovieItemClick(final Movie movie) {
        Log.d(LOG_TAG, "Movie clicked: " + movie);
        openMovieDetailActivity(movie);
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        if (newConfig != null) {
            Log.d(LOG_TAG, "Orientation changed");
            super.onConfigurationChanged(newConfig);
            int numColumns = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? NUM_COLUMNS_HORIZONTAL : NUM_COLUMNS_VERTICAL;
            layoutManager.setSpanCount(numColumns);
        }
    }

    /**
     * Triggers movies async task to get popular movies
     */
    private void refreshMoviesPopular() {
        Log.d(LOG_TAG, "Refreshing movies grid with popular titles");
        setTitle(R.string.main_menu_popular);
        new MoviesAsyncTask().execute(Sorting.POPULAR);
    }

    /**
     * Triggers movies async task to get top rated movies
     */
    private void refreshMoviesTopRated() {
        Log.d(LOG_TAG, "Refreshing movies grid with top rated");
        setTitle(R.string.main_menu_rated);
        new MoviesAsyncTask().execute(Sorting.TOP_RATED);
    }

    /**
     * Pass collected movies to movie's recycler view
     *
     * @param movies list
     */
    private void refreshMovies(final List<Movie> movies) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "Refreshing recycler view with movies found");
                mMoviesRecyclerViewAdapter.setMovies(movies);
            }
        });
    }

    /**
     * Open Movie Item Detail
     *
     * @param movie movie
     */
    protected void openMovieDetailActivity(final Movie movie) {
        Log.d(LOG_TAG, "Opening details for movie : " + movie);
        Context context = MainActivity.this;
        Class destination = MovieDetailActivity.class;
        Intent startActivityIntent = new Intent(context, destination);

        startActivityIntent.putExtra(Movie.class.getName(), movie);
        startActivity(startActivityIntent);
    }

    /**
     * Movies fetcher async task
     */
    private class MoviesAsyncTask extends AsyncTask<Sorting, Void, Boolean> {

        private MoviesDao moviesDao = daoFactory.getMoviesDao();

        @Override
        protected Boolean doInBackground(final Sorting... params) {
            try {
                Log.d(LOG_TAG, "Refreshing movies");
                Sorting sorting = params[0];
                refresh(sorting);
                return true;
            } catch (Exception ex) {
                Log.e(LOG_TAG, "Something bad has happened", ex);
                return false;
            }
        }

        /**
         * Get movies according to the sorting
         *
         * @param sorting strategy
         */
        private void refresh(final Sorting sorting) {
            if (sorting == null) {
                Log.e(LOG_TAG, "Really, needs to refresh using NULL sorter?");
                return;
            }

            switch (sorting) {
                case POPULAR:
                    Log.d(LOG_TAG, "Getting popular movies");
                    refreshMovies(moviesDao.getPopular());
                    break;
                case TOP_RATED:
                    Log.d(LOG_TAG, "Getting top rated movies");
                    refreshMovies(moviesDao.getTopRated());
                    break;
            }
        }

        @Override
        protected void onPreExecute() {
            Log.d(LOG_TAG, "Executing MoviesAsyncTask");
            mLoadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            Log.d(LOG_TAG, "Finishing MoviesAsyncTask execution");
            mLoadingProgressBar.setVisibility(View.INVISIBLE);
            if (!result) {
                Context context = getApplicationContext();
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(context, R.string.error_conectivity, Toast.LENGTH_LONG);
                mToast.show();
            }
        }
    }
}
