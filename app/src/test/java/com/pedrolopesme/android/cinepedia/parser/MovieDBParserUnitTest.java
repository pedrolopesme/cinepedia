package com.pedrolopesme.android.cinepedia.parser;

import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.pedrolopesme.android.cinepedia.domain.MovieImage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class MovieDBParserUnitTest {

    @Test
    public void testParseGenreIdsNullJson() throws Exception {
        assertNull(MovieDBParser.parseGenreIds(null));
    }

    @Test
    public void testParseGenreIdsEmptyJson() throws Exception {
        List<Integer> expectedGenreIds = new ArrayList<>();
        assertEquals(expectedGenreIds, MovieDBParser.parseGenreIds(new JSONArray()));
    }

    @Test
    public void testParseGenreIdsJson() throws Exception {
        List<Integer> expectedGenreIds = new ArrayList<>();
        expectedGenreIds.add(1);
        expectedGenreIds.add(2);
        expectedGenreIds.add(3);

        JSONArray json = new JSONArray();
        json.put(1).put(2).put(3);
        assertEquals(expectedGenreIds, MovieDBParser.parseGenreIds(json));
    }

    @Test
    public void testParseMovieNullJson() throws Exception {
        assertNull(MovieDBParser.parseMovie(null));
    }

    @Test
    public void testParseMovieEmptyJson() throws Exception {
        assertNull(MovieDBParser.parseMovie(new JSONObject()));
    }

    @Test
    public void testParseMovieJson() throws Exception {
        JSONObject json = generateJson(1, "Foo");
        Movie expectedMovie = generateMovie(1, "Foo");
        assertEquals(expectedMovie, MovieDBParser.parseMovie(json));
    }

    @Test
    public void testParseMovieNullJsonList() throws Exception {
        assertNull(MovieDBParser.parseList(null));
    }

    @Test
    public void testParseMovieEmptyJsonList() throws Exception {
        assertNull(MovieDBParser.parseList(""));
    }

    @Test
    public void testParseMovieList() throws Exception {
        List<Movie> expectedMovies = new ArrayList<>();
        expectedMovies.add(generateMovie(1, "Foo"));
        expectedMovies.add(generateMovie(2, "Bar"));
        expectedMovies.add(generateMovie(3, "Baz"));

        JSONArray jsonMovies = new JSONArray();
        jsonMovies.put(generateJson(1, "Foo"));
        jsonMovies.put(generateJson(2, "Bar"));
        jsonMovies.put(generateJson(3, "Baz"));
        JSONObject json = new JSONObject();
        json.put(MovieDBParser.JSON_ROOT, jsonMovies);

        List<Movie> generatedList = MovieDBParser.parseList(json.toString());
        assertNotNull(generatedList);
        assertEquals(expectedMovies.size(), generatedList.size());
        assertEquals(expectedMovies.get(0), generatedList.get(0));
        assertEquals(expectedMovies.get(1), generatedList.get(1));
        assertEquals(expectedMovies.get(2), generatedList.get(2));
    }

    private Movie generateMovie(int id, String name) {
        List<Integer> genreIds = new ArrayList<>();
        genreIds.add(1);
        genreIds.add(2);
        genreIds.add(3);

        Calendar c = Calendar.getInstance();
        c.set(2017, 0, 1);

        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(name);
        movie.setOriginalTitle(name);
        movie.setAdult(false);
        movie.setBackdrop(new MovieImage("backdrop.png"));
        movie.setGenreIds(genreIds);
        movie.setPoster(new MovieImage("poster.png"));
        movie.setOriginalLanguage("EN");
        movie.setOverview("Foo bar baz");
        movie.setPopularity(1);
        movie.setReleaseDate(c.getTime());
        movie.setVoteCount(10);
        movie.setVideo(true);
        movie.setVoteAverage(10);
        return movie;
    }

    private JSONObject generateJson(int id, String name) {
        try {
            JSONArray genreIds = new JSONArray();
            genreIds.put(1);
            genreIds.put(2);
            genreIds.put(3);

            JSONObject json = new JSONObject();
            json.put(MovieDBParser.JSON_ID, id);
            json.put(MovieDBParser.JSON_TITLE, name);
            json.put(MovieDBParser.JSON_ORIGINAL_TITLE, name);
            json.put(MovieDBParser.JSON_ORIGINAL_LANGUAGE, name);
            json.put(MovieDBParser.JSON_ADULT, false);
            json.put(MovieDBParser.JSON_BACKDROP, "backdrop.png");
            json.put(MovieDBParser.JSON_GENRE_IDS, genreIds);
            json.put(MovieDBParser.JSON_POSTER_PATH, "poster.png");
            json.put(MovieDBParser.JSON_ORIGINAL_LANGUAGE, "EN");
            json.put(MovieDBParser.JSON_OVERVIEW, "Foo bar baz");
            json.put(MovieDBParser.JSON_POPULARITY, 1);
            json.put(MovieDBParser.JSON_RELEASE_DATE, "2017-01-01");
            json.put(MovieDBParser.JSON_VOTE_COUNT, 10);
            json.put(MovieDBParser.JSON_VIDEO, true);
            json.put(MovieDBParser.JSON_VOTE_AVG, 10);
            return json;
        } catch (Exception ex) {
            return null;
        }
    }
}
