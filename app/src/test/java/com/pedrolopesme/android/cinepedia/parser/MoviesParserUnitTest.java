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

public class MoviesParserUnitTest {

    @Test
    public void testParseGenreIdsNullJson() throws Exception {
        assertNull(MoviesParser.parseGenreIds(null));
    }

    @Test
    public void testParseGenreIdsEmptyJson() throws Exception {
        List<Integer> expectedGenreIds = new ArrayList<>();
        assertEquals(expectedGenreIds, MoviesParser.parseGenreIds(new JSONArray()));
    }

    @Test
    public void testParseGenreIdsJson() throws Exception {
        List<Integer> expectedGenreIds = new ArrayList<>();
        expectedGenreIds.add(1);
        expectedGenreIds.add(2);
        expectedGenreIds.add(3);

        JSONArray json = new JSONArray();
        json.put(1).put(2).put(3);
        assertEquals(expectedGenreIds, MoviesParser.parseGenreIds(json));
    }

    @Test
    public void testParseMovieNullJson() throws Exception {
        assertNull(MoviesParser.parseMovie(null));
    }

    @Test
    public void testParseMovieEmptyJson() throws Exception {
        assertNull(MoviesParser.parseMovie(new JSONObject()));
    }

    @Test
    public void testParseMovieJson() throws Exception {
        JSONObject json = generateJson(1, "Foo");
        Movie expectedMovie = generateMovie(1, "Foo");
        assertEquals(expectedMovie, MoviesParser.parseMovie(json));
    }

    @Test
    public void testParseMovieNullJsonList() throws Exception {
        assertNull(MoviesParser.parseList(null));
    }

    @Test
    public void testParseMovieEmptyJsonList() throws Exception {
        assertNull(MoviesParser.parseList(""));
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
        json.put(MoviesParser.JSON_ROOT, jsonMovies);

        List<Movie> generatedList = MoviesParser.parseList(json.toString());
        assertNotNull(generatedList);
        assertEquals(expectedMovies.size(), generatedList.size());
        assertEquals(expectedMovies.get(0), generatedList.get(0));
        assertEquals(expectedMovies.get(1), generatedList.get(1));
        assertEquals(expectedMovies.get(2), generatedList.get(2));
    }

    private Movie generateMovie(long id, String name) {
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
            json.put(MoviesParser.JSON_ID, id);
            json.put(MoviesParser.JSON_TITLE, name);
            json.put(MoviesParser.JSON_ORIGINAL_TITLE, name);
            json.put(MoviesParser.JSON_ORIGINAL_LANGUAGE, name);
            json.put(MoviesParser.JSON_ADULT, false);
            json.put(MoviesParser.JSON_BACKDROP, "backdrop.png");
            json.put(MoviesParser.JSON_GENRE_IDS, genreIds);
            json.put(MoviesParser.JSON_POSTER_PATH, "poster.png");
            json.put(MoviesParser.JSON_ORIGINAL_LANGUAGE, "EN");
            json.put(MoviesParser.JSON_OVERVIEW, "Foo bar baz");
            json.put(MoviesParser.JSON_POPULARITY, 1);
            json.put(MoviesParser.JSON_RELEASE_DATE, "2017-01-01");
            json.put(MoviesParser.JSON_VOTE_COUNT, 10);
            json.put(MoviesParser.JSON_VIDEO, true);
            json.put(MoviesParser.JSON_VOTE_AVG, 10);
            return json;
        } catch (Exception ex) {
            return null;
        }
    }
}
