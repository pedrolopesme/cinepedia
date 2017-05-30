package com.pedrolopesme.android.cinepedia.dao.http;

import android.util.Log;

import com.pedrolopesme.android.cinepedia.dao.MoviesDao;
import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.pedrolopesme.android.cinepedia.parser.MovieDBParser;
import com.pedrolopesme.android.cinepedia.utils.NetworkUtil;

import java.net.URL;
import java.util.List;

class HttpMoviesDao extends HttpBaseDao implements MoviesDao {

    protected static String LOG_TAG = HttpMoviesDao.class.getSimpleName();
    protected static String POPULAR_PATH = "popular";
    protected static String RATED_PATH = "top_rated";

    public HttpMoviesDao(String baseUrl, String apiKey) {
        super(baseUrl, apiKey);
    }

    /**
     * Return movies sorted by popularity
     *
     * @return list of movies
     */
    @Override
    public List<Movie> getPopular() {
        try {
            URL url = buildUrl(POPULAR_PATH);
            String jsonStr = NetworkUtil.getResponseFromHttpUrl(url);
            if (jsonStr != null && !jsonStr.trim().isEmpty()) {
                return MovieDBParser.parseList(jsonStr);
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to get popular movies", ex);
        }
        return null;
    }


    /**
     * Return movies sorted by rating
     *
     * @return list of movies
     */
    @Override
    public List<Movie> getTopRated() {
        try {
            URL url = buildUrl(RATED_PATH);
            String jsonStr = NetworkUtil.getResponseFromHttpUrl(url);
            if (jsonStr != null && !jsonStr.trim().isEmpty()) {
                return MovieDBParser.parseList(jsonStr);
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to get top rated movies", ex);
        }
        return null;
    }
}
