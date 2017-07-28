package com.pedrolopesme.android.cinepedia.parser;

import android.util.Log;

import com.pedrolopesme.android.cinepedia.domain.Trailer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Trailer Json Parser
 */
public class TrailersParser extends BaseParser {

    private static final String LOG_TAG = TrailersParser.class.getSimpleName();
    static final String JSON_ROOT = "results";
    static final String JSON_ID = "id";
    static final String JSON_ISO_639_1 = "iso_639_1";
    static final String JSON_ISO_3166_1 = "iso_3166_1";
    static final String JSON_KEY = "key";
    static final String JSON_NAME = "name";
    static final String JSON_SITE = "site";
    static final String JSON_SIZE = "size";
    static final String JSON_TYPE = "type";

    /**
     * Parsers a list of trailers.
     *
     * @param trailerList json string with trailers
     * @return List<Trailers>
     */
    public static List<Trailer> parseList(final String trailerList) {
        try {
            List<Trailer> trailers = new ArrayList<>();
            JSONObject rootJson = new JSONObject(trailerList);
            JSONArray results = rootJson.getJSONArray(JSON_ROOT);
            if (results != null) {
                for (int c = 0; c < results.length(); c++) {
                    JSONObject trailerJson = (JSONObject) results.get(c);
                    Trailer trailer = parseTrailer(trailerJson);
                    if (trailer != null) {
                        trailers.add(trailer);
                    }
                }
            }
            return trailers;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to parse trailers", ex);
        }
        return null;
    }

    /**
     * Parse a trailer from JSONObject and transforms it in a Trailer
     *
     * @param json jsonObject containing a trailer
     * @return Trailer
     */
    static Trailer parseTrailer(final JSONObject json) {
        try {
            if (json != null && json.has(JSON_ID)) {
                String id = json.getString(JSON_ID);
                String iso_639_1 = json.getString(JSON_ISO_639_1);
                String iso_3166_1 = json.getString(JSON_ISO_3166_1);
                String key = json.getString(JSON_KEY);
                String name = json.getString(JSON_NAME);
                String site = json.getString(JSON_SITE);
                long size = json.getLong(JSON_SIZE);
                Trailer.Type type = parseTrailerType(json);


                Trailer trailer = new Trailer();
                trailer.setId(id);
                trailer.setIso_639_1(iso_639_1);
                trailer.setIso_3166_1(iso_3166_1);
                trailer.setKey(key);
                trailer.setName(name);
                trailer.setSite(site);
                trailer.setSize(size);
                trailer.setType(type);
                return trailer;
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to parse trailer" + json.toString(), ex);
        }
        return null;
    }

    /**
     * Parsers a jsonString containing a Trailer Type
     *
     * @param trailerTypeJson jsonArray containing a trailer type
     * @return trailer type
     */
    static Trailer.Type parseTrailerType(final JSONObject trailerTypeJson) {
        try {
            if (trailerTypeJson != null) {
                String typeStr = trailerTypeJson.getString(JSON_TYPE);
                return Trailer.Type.parse(typeStr);
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "It was impossible to parse trailer type json" + trailerTypeJson.toString(), ex);
        }
        return null;
    }

}
