package com.pedrolopesme.android.cinepedia.parser;

import android.util.Log;

import com.pedrolopesme.android.cinepedia.domain.Review;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Reviews Json Parser
 */
final public class ReviewsParser extends BaseParser {

    private static final String LOG_TAG = ReviewsParser.class.getSimpleName();
    static final String JSON_ROOT = "results";
    static final String JSON_ID = "id";
    static final String JSON_AUTHOR = "author";
    static final String JSON_CONTENT = "content";
    static final String JSON_URL = "url";

    /**
     * Parsers a list of reviews.
     *
     * @param reviewsList json string with reviews
     * @return List<Review>
     */
    public static List<Review> parseList(final String reviewsList) {
        try {
            List<Review> reviews = new ArrayList<>();
            JSONObject rootJson = new JSONObject(reviewsList);
            JSONArray results = rootJson.getJSONArray(JSON_ROOT);
            if (results != null) {
                for (int c = 0; c < results.length(); c++) {
                    JSONObject reviewsJson = (JSONObject) results.get(c);
                    Review review = parseReview(reviewsJson);
                    if (review != null) {
                        reviews.add(review);
                    }
                }
            }
            return reviews;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to parse reviews", ex);
        }
        return null;
    }

    /**
     * Parse a review from JSONObject and transforms it in a review
     *
     * @param json jsonObject containing a review
     * @return Review
     */
    static Review parseReview(final JSONObject json) {
        try {
            if (json != null && json.has(JSON_ID)) {
                String id = json.getString(JSON_ID);
                String author = json.getString(JSON_AUTHOR);
                String content = json.getString(JSON_CONTENT);
                String url = json.getString(JSON_URL);

                Review review = new Review();
                review.setId(id);
                review.setAuthor(author);
                review.setContent(content);
                review.setUrl(url);
                return review;
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to parse review" + json.toString(), ex);
        }
        return null;
    }
}
