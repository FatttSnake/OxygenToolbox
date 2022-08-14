package com.fatapp.oxygentoolbox;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Tester {
    @Test
    public void urlEncodeTest() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode(" ", StandardCharsets.UTF_8.toString()));
    }

    @Test
    public void buildConfigTest() {
        assertEquals(BuildConfig.API_APP_ID, "123456789");
        assertEquals(BuildConfig.API_APP_SECRET, "abcdefg");
    }
}
