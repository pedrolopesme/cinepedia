package com.pedrolopesme.android.cinepedia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.pedrolopesme.android.cinepedia.utils.DateUtil;
import com.pedrolopesme.android.cinepedia.utils.NumberUtil;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    // Log tag description
    private final static String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    // Movie name
    TextView mMovieNameTextView;

    // Movie poster
    ImageView mMoviePosterTextView;

    // Movie release date
    TextView mMovieReleaseDateTextView;

    // Movie rating
    TextView mMovieRatingTextView;

    // Movie Synopsis
    TextView mMovieSynopsisTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Creating Movie Detail Activity!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        renderTitle();

        Movie movie = getMovie();
        mMovieNameTextView = (TextView) findViewById(R.id.tv_movie_name);
        mMoviePosterTextView = (ImageView) findViewById(R.id.iv_movie_poster);
        mMovieReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mMovieRatingTextView = (TextView) findViewById(R.id.tv_ratings);
        mMovieSynopsisTextView = (TextView) findViewById(R.id.tv_movie_synopsis);
        renderActivity(movie);
    }

    /**
     * Transforms title text and adding back arrow
     */
    protected void renderTitle() {
        Log.d(LOG_TAG, "Rendering title");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.title_moviedetail);
    }

    /**
     * Apply movie info into Views
     *
     * @param movie
     */
    protected void renderActivity(final Movie movie) {
        if (movie != null) {
            Log.d(LOG_TAG, "Applying movie info on Views");
            String rating = new StringBuilder()
                    .append(NumberUtil.stringfy(movie.getVoteAverage()))
                    .append("/10")
                    .toString();

            mMovieNameTextView.setText(movie.getOriginalTitle());
            mMovieReleaseDateTextView.setText(DateUtil.format(movie.getReleaseDate(), "yyyy"));
            mMovieRatingTextView.setText(rating);
            mMovieSynopsisTextView.setText(movie.getOverview());
            Picasso.with(getApplicationContext())
                    .load(movie.getPoster().getSmall())
                    .into(mMoviePosterTextView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "Closing activity");
        finish();
        return true;
    }

    /**
     * Extracts selected Movie from intent
     *
     * @return Movie movie
     */
    protected Movie getMovie() {
        Log.d(LOG_TAG, "Extracting movie from intent");
        Intent intent = getIntent();
        if (intent.hasExtra(Movie.class.getName())) {
            return (Movie) intent.getParcelableExtra(Movie.class.getName());
        }
        return null;
    }

}
