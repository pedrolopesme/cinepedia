package com.pedrolopesme.android.cinepedia.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.domain.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    TextView mMovieNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mMovieNameTextView = (TextView) findViewById(R.id.tv_movie_name);
        Movie movie = getMovie();
        if (movie != null) {
            mMovieNameTextView.setText(movie.getTitle());
        }
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
