package com.pedrolopesme.android.cinepedia.dao.http;

import android.net.Uri;
import android.util.Log;

import com.pedrolopesme.android.cinepedia.dao.MoviesDao;
import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.pedrolopesme.android.cinepedia.parser.MoviesParser;
import com.pedrolopesme.android.cinepedia.utils.NetworkUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public final class HttpMoviesDao extends HttpBaseDao implements MoviesDao {

    private static final String LOG_TAG = HttpMoviesDao.class.getSimpleName();

    // Popular url path
    private static final String POPULAR_PATH = "popular";

    // Top rated url path
    private static final String RATED_PATH = "top_rated";

    /**
     * Http Movies Dao
     *
     * @param baseUrl url
     * @param apiKey  api key
     */
    public HttpMoviesDao(final String baseUrl, final String apiKey) {
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
            Log.d(LOG_TAG, "Getting popular movies");
            URL url = buildUrl(POPULAR_PATH);
            String jsonStr = NetworkUtil.getResponseFromHttpUrl(url);
            if (jsonStr != null && !jsonStr.trim().isEmpty()) {
                return MoviesParser.parseList(jsonStr);
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
            Log.d(LOG_TAG, "Getting rated movies");
            URL url = buildUrl(RATED_PATH);
            String jsonStr = NetworkUtil.getResponseFromHttpUrl(url);
            if (jsonStr != null && !jsonStr.trim().isEmpty()) {
                return MoviesParser.parseList(jsonStr);
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to get top rated movies", ex);
        }
        return null;
    }

    private URL buildUrl(final String path) {
        try {
            Log.d(LOG_TAG, "Building URL to path " + path);
            Uri uri = Uri.parse(getBaseUrl())
                    .buildUpon()
                    .appendPath(path)
                    .appendQueryParameter("api_key", getApiKey())
                    .build();
            Log.d(LOG_TAG, "URL Built to path " + uri.toString());
            return new URL(uri.toString());
        } catch (MalformedURLException ex) {
            Log.e(LOG_TAG, "It was impossible to build URL with path: " + path, ex);
        }
        return null;
    }

}
