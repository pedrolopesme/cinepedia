package com.pedrolopesme.android.cinepedia.utils;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class DateUtilUnitTest {

    @Test
    public void testParseNullDate() throws Exception {
        assertNull(DateUtil.parse(null, ""));
    }

    @Test
    public void testParseNullFormat() throws Exception {
        assertNull(DateUtil.parse("2017-01-01", null));
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidDate() throws Exception {
        assertNull(DateUtil.parse("AAAAAA", "yyyy-MM-dd"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseInvalidFormat() throws Exception {
        assertNull(DateUtil.parse("2017-01-01", "ABCDEF)(@$"));
    }

    @Test
    public void testParseValidDateAndFormat() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Calendar c = Calendar.getInstance();
        c.set(2017, 0, 1);
        String expectedDate = sdf.format(c.getTime());
        String generatedDate = sdf.format(DateUtil.parse("2017-01-01", "yyyy-MM-dd"));
        assertEquals(expectedDate, generatedDate);
    }
}
