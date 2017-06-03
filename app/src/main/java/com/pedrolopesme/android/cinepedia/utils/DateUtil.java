package com.pedrolopesme.android.cinepedia.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class to handle date
 */
public class DateUtil {

    public static Date parse(String dt, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(dt);
    }

    public static String format(Date dt, String format) {
        return new SimpleDateFormat(format).format(dt);
    }

}
