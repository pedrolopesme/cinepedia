package com.pedrolopesme.android.cinepedia.dao;

import com.pedrolopesme.android.cinepedia.domain.Movie;

import java.util.List;

public interface MoviesDao {

    List<Movie> getPopular();

    List<Movie> getTopRated();
}
