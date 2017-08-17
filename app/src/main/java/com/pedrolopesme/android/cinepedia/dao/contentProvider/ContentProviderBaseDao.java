package com.pedrolopesme.android.cinepedia.dao.contentProvider;

import android.content.ContentResolver;

abstract class ContentProviderBaseDao {

    private static String LOG_TAG = ContentProviderBaseDao.class.getSimpleName();

    private final ContentResolver contentResolver;

    ContentProviderBaseDao(final ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    ContentResolver getContentResolver() {
        return contentResolver;
    }
}
