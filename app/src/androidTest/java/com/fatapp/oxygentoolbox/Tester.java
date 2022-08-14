package com.fatapp.oxygentoolbox;

import android.text.format.DateFormat;
import android.util.Log;

import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tester {
    @Test
    public void dateFormatTest() {
        System.out.println(DateFormat.format("HH:mm:ss dd MMM E", System.currentTimeMillis()));
    }

    @Test
    public void translationTest() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://translate.google.com/translate_a/single?client=gtx&dt=t&dj=1&ie=UTF-8&sl=auto&tl=zh_TW&q=calculate%26%26").build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            Log.d("Translation", Objects.requireNonNull(response.body()).string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
