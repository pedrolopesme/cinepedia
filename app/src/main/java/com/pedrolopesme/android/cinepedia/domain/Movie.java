package com.pedrolopesme.android.cinepedia.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Movie implements Serializable {

    private int id;
    private MovieImage posterImage;
    private boolean adult;
    private String overview;
    private Date releaseDate;
    private List<Integer> genreIds;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private MovieImage backdropImage;
    private double popularity;
    private int voteCount;
    private boolean video;
    private double voteAverage;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovieImage getPoster() {
        return posterImage;
    }

    public void setPoster(MovieImage posterPath) {
        this.posterImage = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieImage getBackdrop() {
        return backdropImage;
    }

    public void setBackdrop(MovieImage backdropImage) {
        this.backdropImage = backdropImage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return id == movie.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public MovieImage getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(MovieImage posterImage) {
        this.posterImage = posterImage;
    }
}
