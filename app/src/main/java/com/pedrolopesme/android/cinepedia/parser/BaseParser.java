package com.pedrolopesme.android.cinepedia.parser;

import android.util.Log;

import com.pedrolopesme.android.cinepedia.utils.DateUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Base Parser with common functions
 */

abstract class BaseParser {

    public static String LOG_TAG = BaseParser.class.getSimpleName();

    public static Date parseDate(JSONObject json, String keyName) {
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
