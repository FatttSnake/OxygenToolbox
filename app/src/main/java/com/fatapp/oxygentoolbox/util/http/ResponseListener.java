package com.fatapp.oxygentoolbox.util.http;

public interface ResponseListener {
    void onResponse(int code, String responseBody);

    void onFailed();
}
