package com.pedrolopesme.android.cinepedia.parser;

import org.json.JSONObject;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class BaseParserUnitTest {

    @Test
    public void testParseDateNullJson() throws Exception {
        assertNull(BaseParser.parseDate(null, ""));
    }

    @Test
    public void testParseDateNullKeyName() throws Exception {
        assertNull(BaseParser.parseDate(new JSONObject(), null));
    }

    @Test
    public void testParseDateWithJsonWithoutGivenKey() throws Exception {
        JSONObject json = new JSONObject();
        json.put("myDate", "2017-01-01");
        assertNull(BaseParser.parseDate(json, "a"));
    }

    @Test
    public void testParseDate() throws Exception {
        JSONObject json = new JSONObject();
        json.put("myDate", "2017-01-01");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date generatedDate = BaseParser.parseDate(json, "myDate");

        Calendar c = Calendar.getInstance();
        c.set(2017, 0, 1);
        Date expectedDate = c.getTime();

        assertEquals(sdf.format(expectedDate), sdf.format(generatedDate));
    }
}
