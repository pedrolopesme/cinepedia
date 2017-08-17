package com.pedrolopesme.android.cinepedia.dao;

import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.pedrolopesme.android.cinepedia.domain.Trailer;

import java.util.List;

public interface FavoriteDao {

    void insert(final Movie movie);

    void delete(final long movieId);

    List<Movie> getMovies();

    boolean isFavorite(final long movieId);

}
