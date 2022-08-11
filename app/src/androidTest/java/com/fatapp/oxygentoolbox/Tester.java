package com.fatapp.oxygentoolbox;

import android.text.format.DateFormat;

import org.junit.Test;

public class Tester {
    @Test
    public void dateFormat() {
        System.out.println(DateFormat.format("HH:mm:ss dd MMM E", System.currentTimeMillis()));
    }
}
