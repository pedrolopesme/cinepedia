package com.pedrolopesme.android.cinepedia.parser;

import com.pedrolopesme.android.cinepedia.domain.Review;
import com.pedrolopesme.android.cinepedia.domain.Trailer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class ReviewsParserUnitTest {

    @Test
    public void testParseReviewNullJson() throws Exception {
        assertNull(ReviewsParser.parseReview(null));
    }

    @Test
    public void testParseReviewEmptyJson() throws Exception {
        assertNull(ReviewsParser.parseReview(new JSONObject()));
    }

    @Test
    public void testParseReviewJson() throws Exception {
        JSONObject json = generateJson("a1", "Foo");
        Review expectedReview = generateReview("a1", "Foo");
        assertEquals(expectedReview, ReviewsParser.parseReview(json));
    }

    @Test
    public void testParseReviewsNullJsonList() throws Exception {
        assertNull(ReviewsParser.parseList(null));
    }

    @Test
    public void testParseReviewsEmptyJsonList() throws Exception {
        assertNull(ReviewsParser.parseList(""));
    }

    @Test
    public void testParseReviewList() throws Exception {
        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(generateReview("a1", "Foo"));
        expectedReviews.add(generateReview("a2", "Bar"));
        expectedReviews.add(generateReview("a3", "Baz"));

        JSONArray jsonReviews = new JSONArray();
        jsonReviews.put(generateJson("a1", "Foo"));
        jsonReviews.put(generateJson("a2", "Bar"));
        jsonReviews.put(generateJson("a3", "Baz"));
        JSONObject json = new JSONObject();
        json.put(ReviewsParser.JSON_ROOT, jsonReviews);

        List<Review> generatedList = ReviewsParser.parseList(json.toString());
        assertNotNull(generatedList);
        assertEquals(expectedReviews.size(), generatedList.size());
        assertEquals(expectedReviews.get(0), generatedList.get(0));
        assertEquals(expectedReviews.get(1), generatedList.get(1));
        assertEquals(expectedReviews.get(2), generatedList.get(2));
    }

    private Review generateReview(String id, String content) {
        Review review = new Review();
        review.setId(id);
        review.setContent(content);
        review.setAuthor("John Doe");
        review.setUrl("http://johndoe.com");
        return review;
    }

    private JSONObject generateJson(String id, String content) {
        try {
            JSONObject json = new JSONObject();
            json.put(ReviewsParser.JSON_ID, id);
            json.put(ReviewsParser.JSON_CONTENT, content);
            json.put(ReviewsParser.JSON_AUTHOR, "John Doe");
            json.put(ReviewsParser.JSON_URL, "http://johndoe.com");
            return json;
        } catch (Exception ex) {
            return null;
        }
    }
}
