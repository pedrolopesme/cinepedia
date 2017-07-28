package com.pedrolopesme.android.cinepedia.domain;

import android.os.Parcel;
import android.os.Parcelable;

public final class Trailer implements Parcelable {

    public enum Type {

        TRAILER("Trailer"), TEASER("Teaser"), CLIP("Clip"), FEATURETTE("Featurette");

        private String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static Type parse(String val) {
            switch (val) {
                case "Trailer":
                    return TRAILER;
                case "Teaser":
                    return TEASER;
                case "Clip":
                    return CLIP;
                case "Featurette":
                    return FEATURETTE;
                default:
                    return null;
            }
        }
    }

    private String id;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private long size;
    private Type type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trailer trailer = (Trailer) o;

        return id.equals(trailer.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


    @Override
    public String toString() {
        return "Trailer{" +
                "id='" + id + '\'' +
                ", iso_639_1='" + iso_639_1 + '\'' +
                ", iso_3166_1='" + iso_3166_1 + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", size=" + size +
                ", type=" + type +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.iso_639_1);
        dest.writeString(this.iso_3166_1);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeLong(this.size);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    public Trailer() {
    }

    protected Trailer(Parcel in) {
        this.id = in.readString();
        this.iso_639_1 = in.readString();
        this.iso_3166_1 = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readLong();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            return new Trailer(source);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}
