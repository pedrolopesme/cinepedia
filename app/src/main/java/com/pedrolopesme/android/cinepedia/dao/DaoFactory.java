package com.pedrolopesme.android.cinepedia.dao;

public interface DaoFactory {

    MoviesDao getMoviesDao();

    TrailerDao getTrailerDao();

}
