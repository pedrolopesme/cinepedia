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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    // Log tag description
    private final static String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    // Movie name
    @BindView(R.id.tv_movie_name)
    TextView mMovieNameTextView;

    // Movie poster
    @BindView(R.id.iv_movie_poster)
    ImageView mMoviePosterTextView;

    // Movie release date
    @BindView(R.id.tv_release_date)
    TextView mMovieReleaseDateTextView;

    // Movie rating
    @BindView(R.id.tv_ratings)
    TextView mMovieRatingTextView;

    // Movie Synopsis
    @BindView(R.id.tv_movie_synopsis)
    TextView mMovieSynopsisTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Creating Movie Detail Activity!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        renderTitle();

        ButterKnife.bind(this);

        Movie movie = getMovie();
        renderActivity(movie);
    }

    /**
     * Transforms title text and adding back arrow
     */
    private void renderTitle() {
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
     * @param movie selected
     */
    private void renderActivity(final Movie movie) {
        if (movie != null) {
            Log.d(LOG_TAG, "Applying movie info on Views");
            String rating = NumberUtil.stringify(movie.getVoteAverage()) + "/10";

            mMovieNameTextView.setText(movie.getOriginalTitle());
            mMovieReleaseDateTextView.setText(DateUtil.format(movie.getReleaseDate(), "yyyy"));
            mMovieRatingTextView.setText(rating);
            mMovieSynopsisTextView.setText(movie.getOverview());
            Picasso.with(getApplicationContext())
                    .load(movie.getPoster().getSmall())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
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
    private Movie getMovie() {
        Log.d(LOG_TAG, "Extracting movie from intent");
        Intent intent = getIntent();
        if (intent.hasExtra(Movie.class.getName())) {
            return (Movie) intent.getParcelableExtra(Movie.class.getName());
        }
        return null;
    }

}
