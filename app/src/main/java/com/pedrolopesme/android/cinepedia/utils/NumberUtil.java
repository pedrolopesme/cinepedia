package com.pedrolopesme.android.cinepedia.utils;

import java.text.DecimalFormat;

/**
 * Utilitary class to handle numbers
 */
public class NumberUtil {

    public static String stringfy(Double val) {
        if (val != null) {
            DecimalFormat df = new DecimalFormat("#.#");
            return df.format(val);
        }
        return null;
    }

}
