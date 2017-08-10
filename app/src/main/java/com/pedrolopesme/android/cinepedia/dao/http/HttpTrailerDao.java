package com.pedrolopesme.android.cinepedia.dao.http;

import android.net.Uri;
import android.util.Log;

import com.pedrolopesme.android.cinepedia.dao.TrailerDao;
import com.pedrolopesme.android.cinepedia.domain.Trailer;
import com.pedrolopesme.android.cinepedia.parser.TrailersParser;
import com.pedrolopesme.android.cinepedia.utils.NetworkUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public final class HttpTrailerDao extends HttpBaseDao implements TrailerDao {

    private static final String LOG_TAG = HttpTrailerDao.class.getSimpleName();

    private static final String TRAILERS_PATH = "videos";

    /**
     * Http Trailer Dao
     *
     * @param baseUrl url
     * @param apiKey  api key
     */
    public HttpTrailerDao(final String baseUrl, final String apiKey) {
        super(baseUrl, apiKey);
    }

    /**
     * Return trailers of a Movie
     *
     * @return list of trailers
     */
    @Override
    public List<Trailer> get(long movieId) {
        try {
            Log.d(LOG_TAG, "Getting movie trailers");
            URL url = buildUrl(movieId);
            String jsonStr = NetworkUtil.getResponseFromHttpUrl(url);
            if (jsonStr != null && !jsonStr.trim().isEmpty()) {
                return TrailersParser.parseList(jsonStr);
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to get movie's trailer", ex);
        }
        return null;
    }

    private URL buildUrl(final long movieId) {
        try {
            Log.d(LOG_TAG, "Building URL to  " + movieId);
            Uri uri = Uri.parse(getBaseUrl())
                    .buildUpon()
                    .appendPath(String.valueOf(movieId))
                    .appendPath(TRAILERS_PATH)
                    .appendQueryParameter("api_key", getApiKey())
                    .build();
            Log.d(LOG_TAG, "URL Built to path " + uri.toString());
            return new URL(uri.toString());
        } catch (MalformedURLException ex) {
            Log.e(LOG_TAG, "It was impossible to build URL with movie id: " + movieId, ex);
        }
        return null;
    }
}
