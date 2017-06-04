package com.pedrolopesme.android.cinepedia.parser;

import android.util.Log;

import com.pedrolopesme.android.cinepedia.utils.DateUtil;

import org.json.JSONObject;

import java.util.Date;

/**
 * Base Parser with common functions
 */

abstract class BaseParser {

    private static String LOG_TAG = BaseParser.class.getSimpleName();

    /**
     * Gets a date string from a JSON object and return its Date object.
     * Uses date format : yyyy-MM-dd
     *
     * @param json    element
     * @param keyName identifier
     * @return date
     */
     static Date parseDate(final JSONObject json, final String keyName) {
        try {
            if (json != null && keyName != null && json.has(keyName)) {
                String val = json.getString(keyName);
                if (val != null) {
                    return DateUtil.parse(val, "yyyy-MM-dd");
                }
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to parse date with json" + json.toString() +
                    " and key " + keyName, ex);
        }
        return null;
    }
}
