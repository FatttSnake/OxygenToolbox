package com.fatapp.oxygentoolbox.util;

import com.google.gson.annotations.SerializedName;

public class LicenseJson {
    @SerializedName("content")
    private String content;
    @SerializedName("hash")
    private String hash;
    @SerializedName("internalHash")
    private String internalHash;
    @SerializedName("url")
    private String url;
    @SerializedName("spdxId")
    private String spdxId;
    @SerializedName("name")
    private String name;

    public String getContent() {
        return content;
    }

    public String getHash() {
        return hash;
    }

    public String getInternalHash() {
        return internalHash;
    }

    public String getUrl() {
        return url;
    }

    public String getSpdxId() {
        return spdxId;
    }

    public String getName() {
        return name;
    }
}
