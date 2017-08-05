package com.pedrolopesme.android.cinepedia.dao.http;

import android.net.Uri;
import android.util.Log;

import com.pedrolopesme.android.cinepedia.dao.ReviewDao;
import com.pedrolopesme.android.cinepedia.domain.Review;
import com.pedrolopesme.android.cinepedia.domain.Trailer;
import com.pedrolopesme.android.cinepedia.parser.ReviewsParser;
import com.pedrolopesme.android.cinepedia.parser.TrailersParser;
import com.pedrolopesme.android.cinepedia.utils.NetworkUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

final class HttpReviewDao extends HttpBaseDao implements ReviewDao {

    private static final String LOG_TAG = HttpReviewDao.class.getSimpleName();

    private static final String REVIEWS_PATH = "reviews";

    /**
     * Http Review Dao
     *
     * @param baseUrl url
     * @param apiKey  api key
     */
    HttpReviewDao(final String baseUrl, final String apiKey) {
        super(baseUrl, apiKey);
    }

    /**
     * Return reviews of a Movie
     *
     * @return list of reviews
     */
    @Override
    public List<Review> get(int movieId) {
        try {
            Log.d(LOG_TAG, "Getting movie reviews");
            URL url = buildUrl(movieId);
            String jsonStr = NetworkUtil.getResponseFromHttpUrl(url);
            if (jsonStr != null && !jsonStr.trim().isEmpty()) {
                return ReviewsParser.parseList(jsonStr);
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to get movie's reviews", ex);
        }
        return null;
    }

    private URL buildUrl(final int movieId) {
        try {
            Log.d(LOG_TAG, "Building URL to  " + movieId);
            Uri uri = Uri.parse(getBaseUrl())
                    .buildUpon()
                    .appendPath(String.valueOf(movieId))
                    .appendPath(REVIEWS_PATH)
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
