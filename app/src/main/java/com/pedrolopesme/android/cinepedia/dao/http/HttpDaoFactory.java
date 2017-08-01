package com.pedrolopesme.android.cinepedia.dao.http;

import com.pedrolopesme.android.cinepedia.dao.DaoFactory;
import com.pedrolopesme.android.cinepedia.dao.MoviesDao;
import com.pedrolopesme.android.cinepedia.dao.TrailerDao;

public class HttpDaoFactory implements DaoFactory {

    private final String baseUrl;
    private final String apiKey;

    public HttpDaoFactory(final String baseUrl, final String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    @Override
    public MoviesDao getMoviesDao() {
        return new HttpMoviesDao(baseUrl, apiKey);
    }

    @Override
    public TrailerDao getTrailerDao() {
        return new HttpTrailerDao(baseUrl, apiKey);
    }

}
