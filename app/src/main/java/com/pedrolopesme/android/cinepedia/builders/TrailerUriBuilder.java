package com.pedrolopesme.android.cinepedia.builders;

import android.net.Uri;

import com.pedrolopesme.android.cinepedia.domain.Trailer;

final public class TrailerUriBuilder {

    private static String DEFAULT_VIDEO_BASE_URL = "http://www.youtube.com/watch?v=";

    public static Uri build(Trailer trailer) {
        if (trailer != null && trailer.getKey() != null) {
            return Uri.parse(DEFAULT_VIDEO_BASE_URL + trailer.getKey());
        }
        return null;
    }

}
