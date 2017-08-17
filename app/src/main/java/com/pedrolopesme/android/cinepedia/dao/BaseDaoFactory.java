package com.pedrolopesme.android.cinepedia.dao;

import android.content.ContentResolver;

import com.pedrolopesme.android.cinepedia.dao.DaoFactory;
import com.pedrolopesme.android.cinepedia.dao.MoviesDao;
import com.pedrolopesme.android.cinepedia.dao.ReviewDao;
import com.pedrolopesme.android.cinepedia.dao.TrailerDao;
import com.pedrolopesme.android.cinepedia.dao.contentProvider.ContentProviderFavoriteDao;
import com.pedrolopesme.android.cinepedia.dao.http.HttpMoviesDao;
import com.pedrolopesme.android.cinepedia.dao.http.HttpReviewDao;
import com.pedrolopesme.android.cinepedia.dao.http.HttpTrailerDao;

public class BaseDaoFactory implements DaoFactory {

    private final String baseUrl;
    private final String apiKey;
    private final ContentResolver contentResolver;

    public BaseDaoFactory(final String baseUrl, final String apiKey, final ContentResolver contentResolver) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.contentResolver = contentResolver;
    }

    @Override
    public MoviesDao getMoviesDao() {
        return new HttpMoviesDao(baseUrl, apiKey);
    }

    @Override
    public TrailerDao getTrailerDao() {
        return new HttpTrailerDao(baseUrl, apiKey);
    }

    @Override
    public ReviewDao getReviewDao() {
        return new HttpReviewDao(baseUrl, apiKey);
    }

    @Override
    public FavoriteDao getFavoriteDao() {
        return new ContentProviderFavoriteDao(contentResolver);
    }

}
