package com.pedrolopesme.android.cinepedia.dao;

public interface DaoFactory {

    MoviesDao getMoviesDao();

    TrailerDao getTrailerDao();

    ReviewDao getReviewDao();

    FavoriteDao getFavoriteDao();

}
