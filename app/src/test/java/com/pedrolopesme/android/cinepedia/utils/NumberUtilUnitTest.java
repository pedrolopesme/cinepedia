package com.pedrolopesme.android.cinepedia.utils;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class NumberUtilUnitTest {

    @Test
    public void testStringfyNullDouble() throws Exception {
        assertNull(NumberUtil.stringfy(null));
    }

    @Test
    public void testStringfyValidDouble() throws Exception {
        assertEquals("0", NumberUtil.stringfy(0.00));
        assertEquals("0", NumberUtil.stringfy(0.01));
        assertEquals("0.1", NumberUtil.stringfy(0.10));
        assertEquals("1", NumberUtil.stringfy(1.00));
        assertEquals("1", NumberUtil.stringfy(1.01));
        assertEquals("1.1", NumberUtil.stringfy(1.11));
    }
}
