package com.pedrolopesme.android.cinepedia.dao.http;

import com.pedrolopesme.android.cinepedia.dao.DaoFactory;
import com.pedrolopesme.android.cinepedia.dao.MoviesDao;

public class HttpDaoFactory implements DaoFactory {

    private final String baseUrl;
    private final String apiKey;

    public HttpDaoFactory(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    @Override
    public MoviesDao getMoviesDao() {
        return new HttpMoviesDao(baseUrl, apiKey);
    }

}
