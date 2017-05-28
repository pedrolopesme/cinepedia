package com.pedrolopesme.android.cinepedia.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pedrolopesme.android.cinepedia.R;

public class MovieDetailActivity extends AppCompatActivity {

    TextView mMovieNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mMovieNameTextView = (TextView) findViewById(R.id.tv_movie_name);
        String movie = getMovieDetails();
        if(movie != null){
            mMovieNameTextView.setText(movie);
        }
    }

    protected String getMovieDetails(){
        Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)){
            String movie = intent.getStringExtra(Intent.EXTRA_TEXT);
            return movie;
        }
        return null;
    }
}
