package com.pedrolopesme.android.cinepedia.utils;

import java.text.DecimalFormat;

/**
 * Utilitary class to handle numbers
 */
public class NumberUtil {

    /**
     * Transforms a double into a String, cropping unnecessary decimals.
     *
     * @param val number
     * @return strign
     */
    public static String stringfy(final Double val) {
        if (val != null) {
            DecimalFormat df = new DecimalFormat("#.#");
            return df.format(val);
        }
        return null;
    }

}
