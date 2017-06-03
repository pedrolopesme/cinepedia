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

    private static String LOG_TAG = MovieDBParser.class.getSimpleName();

    /**
     * Parsers a list of movies. Good for popular or top rated API responses.
     *
     * @param moviesList json string with movies
     * @return List<Movie>
     */
    public static List<Movie> parseList(String moviesList) {
        try {
            List<Movie> movies = new ArrayList<>();
            JSONObject rootJson = new JSONObject(moviesList);
            JSONArray results = rootJson.getJSONArray("results");
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
     * @param movieJson jsonobject containing a movie
     * @return Movie
     */
    public static Movie parseMovie(JSONObject movieJson) {
        try {
            if (movieJson != null) {
                String posterPath = movieJson.getString("poster_path");
                boolean adult = movieJson.getBoolean("adult");
                String overview = movieJson.getString("overview");
                int id = movieJson.getInt("id");
                String originalTitle = movieJson.getString("original_title");
                String originalLanguage = movieJson.getString("original_language");
                String title = movieJson.getString("title");
                String backdropPath = movieJson.getString("backdrop_path");
                double popularity = movieJson.getDouble("popularity");
                int voteCount = movieJson.getInt("vote_count");
                boolean video = movieJson.getBoolean("video");
                double vote_average = movieJson.getDouble("vote_average");
                List<Integer> genreIds = parseGenreIds(movieJson.getJSONArray("genre_ids"));
                Date releaseDate = parseDate(movieJson, "release_date");

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
            Log.e(LOG_TAG, "It was impossible to parse movie" + movieJson.toString(), ex);
        }
        return null;
    }

    /**
     * Parses a list of genreIds from jsonArray and transform them in List of integers
     *
     * @param genresJson jsonarray containing a list of genre ids
     * @return List of Integers
     */
    public static List<Integer> parseGenreIds(JSONArray genresJson) {
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
