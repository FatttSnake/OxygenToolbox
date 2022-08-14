package com.fatapp.oxygentoolbox.tools.translation.util;

public interface ResponseListener {
    void onResponse(int code, String responseBody);

    void onFailed();
}
