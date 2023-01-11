package com.fatapp.oxygentoolbox;

import static org.junit.Assert.assertEquals;

import com.fatapp.oxygentoolbox.util.HexConversionUtils;

import org.junit.Test;

public class MethodTest {
    @Test
    public void decimalToBaseTest() {
        assertEquals(HexConversionUtils.decimalToBase("2.2", 2), "10.00110011001100110011001100110011001100110011001100");
    }
}
