package com.pedrolopesme.android.cinepedia.utils;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class NumberUtilUnitTest {

    @Test
    public void testStringifyNullDouble() throws Exception {
        assertNull(NumberUtil.stringify(null));
    }

    @Test
    public void testStringifyValidDouble() throws Exception {
        assertEquals("0", NumberUtil.stringify(0.00));
        assertEquals("0", NumberUtil.stringify(0.01));
        assertEquals("0.1", NumberUtil.stringify(0.10));
        assertEquals("1", NumberUtil.stringify(1.00));
        assertEquals("1", NumberUtil.stringify(1.01));
        assertEquals("1.1", NumberUtil.stringify(1.11));
    }
}
