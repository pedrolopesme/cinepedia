package com.pedrolopesme.android.cinepedia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.pedrolopesme.android.cinepedia.utils.DateUtil;
import com.pedrolopesme.android.cinepedia.utils.NumberUtil;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.title_moviedetail);
    }

    /**
     * Apply movie info into Views
     *
     * @param movie
     */
    protected void renderActivity(Movie movie) {
        if (movie != null) {
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
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    /**
     * Extracts selected Movie from intent
     *
     * @return Movie movie
     */
    protected Movie getMovie() {
        Intent intent = getIntent();
        if (intent.hasExtra(Movie.class.getName())) {
            return (Movie) intent.getParcelableExtra(Movie.class.getName());
        }
        return null;
    }

}
