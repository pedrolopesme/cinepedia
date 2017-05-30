package com.pedrolopesme.android.cinepedia.parser;

import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pedrolopesme on 28/05/17.
 */

public class BaseParser {

    public static String LOG_TAG = BaseParser.class.getSimpleName();

    public static Date parseDate(JSONObject json, String keyName) {
        try {
            if (json != null && keyName != null) {
                String val = json.getString(keyName);
                if (val != null) {
                    return new SimpleDateFormat("yyyy-MM-dd").parse(val);
                }
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to parse date with json" + json.toString() +
                    " and key " + keyName, ex);
        }
        return null;
    }

}
