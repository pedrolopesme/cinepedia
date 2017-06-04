package com.pedrolopesme.android.cinepedia.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class to handle date
 */
public class DateUtil {

    /**
     * Parses a string into a date object
     *
     * @param dt     date
     * @param format to apply
     * @return date
     * @throws ParseException
     */
    public static Date parse(final String dt, final String format) throws ParseException {
        if (dt != null && format != null)
            return new SimpleDateFormat(format).parse(dt);
        return null;
    }

    /**
     * Transforms a Date object into it's string representation
     *
     * @param dt     object
     * @param format pattern
     * @return string
     */
    public static String format(final Date dt, final String format) {
        if (dt != null && format != null)
            return new SimpleDateFormat(format).format(dt);
        return null;
    }

}
