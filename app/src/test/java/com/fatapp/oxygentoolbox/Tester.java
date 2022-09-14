package com.fatapp.oxygentoolbox;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Tester {
    @Test
    public void urlEncodeTest() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode(" ", StandardCharsets.UTF_8.toString()));
    }
}
