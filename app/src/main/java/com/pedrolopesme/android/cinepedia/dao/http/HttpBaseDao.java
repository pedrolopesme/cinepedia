package com.pedrolopesme.android.cinepedia.dao.http;

abstract class HttpBaseDao {

    private static String LOG_TAG = HttpBaseDao.class.getSimpleName();

    private final String baseUrl;
    private final String apiKey;

    HttpBaseDao(final String baseUrl, final String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    String getBaseUrl() {
        return baseUrl;
    }

    String getApiKey() {
        return apiKey;
    }
}
