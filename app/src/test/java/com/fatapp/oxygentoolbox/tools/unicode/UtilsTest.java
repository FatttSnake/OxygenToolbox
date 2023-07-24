package com.fatapp.oxygentoolbox.tools.unicode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilsTest {
    @Test
    public void stringToAsciiTest() {
        assertEquals(Utils.stringToAscii("安卓"), "&#23433;&#21331;");
    }

    @Test
    public void asciiToStringTest() throws Exception {
        assertEquals(Utils.asciiToString("&#23433;&#21331;"), "安卓");
    }

    @Test
    public void stringToUnicodeTest() {
        assertEquals(Utils.stringToUnicode("安卓"), "\\u5b89\\u5353");
    }

    @Test
    public void unicodeToStringTest() throws Exception {
        assertEquals(Utils.unicodeToString("\\u5b89\\u5353"), "安卓");
    }
}
