package com.pedrolopesme.android.cinepedia.parser;

import android.util.Log;

import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.pedrolopesme.android.cinepedia.domain.MovieImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Movie DB Json Parser
 */
public class MovieDBParser extends BaseParser {

    private static final String LOG_TAG = MovieDBParser.class.getSimpleName();
    static final String JSON_ROOT = "results";
    static final String JSON_ID = "id";
    static final String JSON_POSTER_PATH = "poster_path";
    static final String JSON_ADULT = "adult";
    static final String JSON_OVERVIEW = "overview";
    static final String JSON_ORIGINAL_TITLE = "original_title";
    static final String JSON_ORIGINAL_LANGUAGE = "original_language";
    static final String JSON_TITLE = "title";
    static final String JSON_BACKDROP = "backdrop_path";
    static final String JSON_POPULARITY = "popularity";
    static final String JSON_VOTE_COUNT = "vote_count";
    static final String JSON_VIDEO = "video";
    static final String JSON_VOTE_AVG = "vote_average";
    static final String JSON_GENRE_IDS = "genre_ids";
    static final String JSON_RELEASE_DATE = "release_date";

    /**
     * Parsers a list of movies. Good for popular or top rated API responses.
     *
     * @param moviesList json string with movies
     * @return List<Movie>
     */
    public static List<Movie> parseList(final String moviesList) {
        try {
            List<Movie> movies = new ArrayList<>();
            JSONObject rootJson = new JSONObject(moviesList);
            JSONArray results = rootJson.getJSONArray(JSON_ROOT);
            if (results != null) {
                for (int c = 0; c < results.length(); c++) {
                    JSONObject movieJson = (JSONObject) results.get(c);
                    Movie movie = parseMovie(movieJson);
                    if (movie != null) {
                        movies.add(movie);
                    }
                }
            }
            return movies;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to parse movies", ex);
        }
        return null;
    }

    /**
     * Parse a movie from JSONObject and transforms it in a Movie
     *
     * @param json jsonObject containing a movie
     * @return Movie
     */
     static Movie parseMovie(final JSONObject json) {
        try {
            if (json != null && json.has(JSON_ID)) {
                int id = json.getInt(JSON_ID);
                String posterPath = json.getString(JSON_POSTER_PATH);
                boolean adult = json.getBoolean(JSON_ADULT);
                String overview = json.getString(JSON_OVERVIEW);
                String originalTitle = json.getString(JSON_ORIGINAL_TITLE);
                String originalLanguage = json.getString(JSON_ORIGINAL_LANGUAGE);
                String title = json.getString(JSON_TITLE);
                String backdropPath = json.getString(JSON_BACKDROP);
                double popularity = json.getDouble(JSON_POPULARITY);
                int voteCount = json.getInt(JSON_VOTE_COUNT);
                boolean video = json.getBoolean(JSON_VIDEO);
                double vote_average = json.getDouble(JSON_VOTE_AVG);
                List<Integer> genreIds = parseGenreIds(json.getJSONArray(JSON_GENRE_IDS));
                Date releaseDate = parseDate(json, JSON_RELEASE_DATE);

                Movie movie = new Movie();
                movie.setAdult(adult);
                movie.setBackdrop(new MovieImage(backdropPath));
                movie.setGenreIds(genreIds);
                movie.setId(id);
                movie.setPoster(new MovieImage(posterPath));
                movie.setOriginalLanguage(originalLanguage);
                movie.setOriginalTitle(originalTitle);
                movie.setOverview(overview);
                movie.setPopularity(popularity);
                movie.setReleaseDate(releaseDate);
                movie.setTitle(title);
                movie.setVoteCount(voteCount);
                movie.setVideo(video);
                movie.setVoteAverage(vote_average);
                return movie;
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to parse movie" + json.toString(), ex);
        }
        return null;
    }

    /**
     * Parses a list of genreIds from jsonArray and transform them in List of integers
     *
     * @param genresJson jsonArray containing a list of genre ids
     * @return List of Integers
     */
    static List<Integer> parseGenreIds(final JSONArray genresJson) {
        try {
            if (genresJson != null) {
                List<Integer> genres = new ArrayList<>();
                for (int c = 0; c < genresJson.length(); c++) {
                    Integer genre = (Integer) genresJson.get(c);
                    if (genre != null) {
                        genres.add(genre);
                    }
                }
                return genres;
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to parse genres json" + genresJson.toString(), ex);
        }
        return null;
    }

}
