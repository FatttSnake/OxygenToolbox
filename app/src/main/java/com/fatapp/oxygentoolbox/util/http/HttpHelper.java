package com.fatapp.oxygentoolbox.util.http;

import android.app.Activity;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHelper {
    private final Activity activity;
    private String url;
    private ResponseListener responseListener;

    public HttpHelper(Activity activity, String url) {
        this.activity = activity;
        this.url = url;
    }

    public HttpHelper(Activity activity, String url, ResponseListener responseListener) {
        this.activity = activity;
        this.url = url;
        this.responseListener = responseListener;
    }

    public void request(String... args) {
        new Thread() {
            @Override
            public void run() {
                Log.d("args", Arrays.toString(args));
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(String.format(url, Arrays.asList(args).toArray())).build();
                try (Response response = okHttpClient.newCall(request).execute()) {
                    int code = response.code();
                    String body = Objects.requireNonNull(response.body()).string();
                    activity.runOnUiThread(() -> responseListener.onResponse(code, body));
                } catch (IOException e) {
                    activity.runOnUiThread(() -> responseListener.onFailure());
                }
            }
        }.start();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ResponseListener getResponseListener() {
        return responseListener;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }
}
