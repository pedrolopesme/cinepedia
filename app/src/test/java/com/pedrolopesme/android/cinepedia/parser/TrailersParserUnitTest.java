package com.pedrolopesme.android.cinepedia.parser;

import com.pedrolopesme.android.cinepedia.domain.Trailer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class TrailersParserUnitTest {

    @Test
    public void testParseTrailerTypeNullJson() throws Exception {
        assertNull(TrailersParser.parseTrailerType(null));
    }

    @Test
    public void testParseTrailerTypeEmptyJson() throws Exception {
        assertNull(TrailersParser.parseTrailerType(new JSONObject()));
    }

    @Test
    public void testParseInvalidTrailerTypeJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TrailersParser.JSON_TYPE, "dummy");
        assertNull(TrailersParser.parseTrailerType(jsonObject));
    }

    @Test
    public void testParseValidTrailerTypeJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TrailersParser.JSON_TYPE, Trailer.Type.CLIP.getType());
        assertEquals(Trailer.Type.CLIP, TrailersParser.parseTrailerType(jsonObject));

        jsonObject.put(TrailersParser.JSON_TYPE, Trailer.Type.FEATURETTE.getType());
        assertEquals(Trailer.Type.FEATURETTE, TrailersParser.parseTrailerType(jsonObject));

        jsonObject.put(TrailersParser.JSON_TYPE, Trailer.Type.TEASER.getType());
        assertEquals(Trailer.Type.TEASER, TrailersParser.parseTrailerType(jsonObject));

        jsonObject.put(TrailersParser.JSON_TYPE, Trailer.Type.TRAILER.getType());
        assertEquals(Trailer.Type.TRAILER, TrailersParser.parseTrailerType(jsonObject));
    }

    @Test
    public void testParseTrailerNullJson() throws Exception {
        assertNull(TrailersParser.parseTrailer(null));
    }

    @Test
    public void testParseTrailerEmptyJson() throws Exception {
        assertNull(TrailersParser.parseTrailer(new JSONObject()));
    }

    @Test
    public void testParseTrailerJson() throws Exception {
        JSONObject json = generateJson("a1", "Foo");
        Trailer expectedMovie = generateTrailer("a1", "Foo");
        assertEquals(expectedMovie, TrailersParser.parseTrailer(json));
    }

    @Test
    public void testParseTrailersNullJsonList() throws Exception {
        assertNull(TrailersParser.parseList(null));
    }

    @Test
    public void testParseTrailersEmptyJsonList() throws Exception {
        assertNull(TrailersParser.parseList(""));
    }

    @Test
    public void testParseMovieList() throws Exception {
        List<Trailer> expectedTrailers = new ArrayList<>();
        expectedTrailers.add(generateTrailer("a1", "Foo"));
        expectedTrailers.add(generateTrailer("a2", "Bar"));
        expectedTrailers.add(generateTrailer("a3", "Baz"));

        JSONArray jsonTrailers = new JSONArray();
        jsonTrailers.put(generateJson("a1", "Foo"));
        jsonTrailers.put(generateJson("a2", "Bar"));
        jsonTrailers.put(generateJson("a3", "Baz"));
        JSONObject json = new JSONObject();
        json.put(MoviesParser.JSON_ROOT, jsonTrailers);

        List<Trailer> generatedList = TrailersParser.parseList(json.toString());
        assertNotNull(generatedList);
        assertEquals(expectedTrailers.size(), generatedList.size());
        assertEquals(expectedTrailers.get(0), generatedList.get(0));
        assertEquals(expectedTrailers.get(1), generatedList.get(1));
        assertEquals(expectedTrailers.get(2), generatedList.get(2));
    }

    private Trailer generateTrailer(String id, String name) {
        Trailer trailer = new Trailer();
        trailer.setId(id);
        trailer.setName(name);
        trailer.setIso_639_1("en");
        trailer.setIso_3166_1("EN");
        trailer.setKey("key");
        trailer.setSite("site");
        trailer.setSize(100L);
        trailer.setType(Trailer.Type.CLIP);
        return trailer;
    }

    private JSONObject generateJson(String id, String name) {
        try {
            JSONObject json = new JSONObject();
            json.put(TrailersParser.JSON_ID, id);
            json.put(TrailersParser.JSON_NAME, name);
            json.put(TrailersParser.JSON_ISO_639_1, "en");
            json.put(TrailersParser.JSON_ISO_3166_1, "EN");
            json.put(TrailersParser.JSON_KEY, "key");
            json.put(TrailersParser.JSON_SITE, "site");
            json.put(TrailersParser.JSON_SIZE, 100L);
            json.put(TrailersParser.JSON_TYPE, Trailer.Type.CLIP.getType());
            return json;
        } catch (Exception ex) {
            return null;
        }
    }
}
