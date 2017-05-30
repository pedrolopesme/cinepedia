package com.pedrolopesme.android.cinepedia.dao.http;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class HttpBaseDao {

    private static String LOG_TAG = HttpBaseDao.class.getSimpleName();
    protected final String baseUrl;
    protected final String apiKey;

    public HttpBaseDao(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    protected URL buildUrl(String path) {
        try {
            Uri uri = Uri.parse(getBaseUrl())
                    .buildUpon()
                    .appendPath(path)
                    .appendQueryParameter("api_key", getApiKey())
                    .build();
            return new URL(uri.toString());
        } catch (MalformedURLException ex) {
            Log.e(LOG_TAG, "It was impossible to build URL with path: " + path, ex);
        }
        return null;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }
}
