package com.pedrolopesme.android.cinepedia.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Utilitary class to handle numbers
 */
public class NumberUtil {

    public static String stringfy(Double val) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(val);
    }

}
