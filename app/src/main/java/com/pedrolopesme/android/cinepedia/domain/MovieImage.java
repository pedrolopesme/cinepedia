package com.pedrolopesme.android.cinepedia.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Movie Image Paths
 */
public class MovieImage implements Parcelable {

    private static String BASE_PATH = "http://image.tmdb.org/t/p/w";
    private String path;

    public enum Sizes {

        VERY_VERY_SMALL(92), VERY_SMALL(154), SMALL(185), NORMAL(342), BIG(500), BIGGEST(780);

        private Integer size;

        Sizes(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    public MovieImage(String path) {
        this.path = path;
    }

    private String getResized(Sizes size) {
        return BASE_PATH + size.getSize() + "/" + path;
    }

    public String getVeryVerySmall() {
        return getResized(Sizes.VERY_VERY_SMALL);
    }

    public String getVerySmall() {
        return getResized(Sizes.VERY_SMALL);
    }

    public String getSmall() {
        return getResized(Sizes.SMALL);
    }

    public String getNormal() {
        return getResized(Sizes.NORMAL);
    }

    public String getBig() {
        return getResized(Sizes.BIG);
    }

    public String getBiggest() {
        return getResized(Sizes.BIGGEST);
    }

    @Override
    public String toString() {
        return "MovieImage{" +
                "path='" + path + '\'' +
                '}';
    }

    // Parcelable garbage

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
    }

    protected MovieImage(Parcel in) {
        this.path = in.readString();
    }

    public static final Parcelable.Creator<MovieImage> CREATOR = new Parcelable.Creator<MovieImage>() {
        @Override
        public MovieImage createFromParcel(Parcel source) {
            return new MovieImage(source);
        }

        @Override
        public MovieImage[] newArray(int size) {
            return new MovieImage[size];
        }
    };
}
