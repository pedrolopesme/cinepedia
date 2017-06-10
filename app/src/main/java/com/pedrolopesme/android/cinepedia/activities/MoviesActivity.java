package com.pedrolopesme.android.cinepedia.activities;


import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pedrolopesme.android.cinepedia.domain.Movie;

import java.util.List;

public abstract class MoviesActivity extends AppCompatActivity {

    public abstract void refreshMovies(final List<Movie> movies);

    public abstract ProgressBar getProgressBar();

    public abstract Toast getToast();

    public abstract void setToast(Toast toast);

}
