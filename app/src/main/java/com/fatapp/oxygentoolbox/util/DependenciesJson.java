package com.fatapp.oxygentoolbox.util;

import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class DependenciesJson {

    @SerializedName("metadata")
    private MetadataDTO metadata;
    @SerializedName("libraries")
    private List<LibrariesDTO> libraries;
    @SerializedName("licenses")
    @JsonAdapter(RawStringJsonAdapter.class)
    private String licenses;

    public MetadataDTO getMetadata() {
        return metadata;
    }

    public List<LibrariesDTO> getLibraries() {
        return libraries;
    }

    public String getLicenses() {
        return licenses;
    }

    public static class MetadataDTO {
        @SerializedName("generated")
        private String generated;

        public String getGenerated() {
            return generated;
        }
    }

    public static class LibrariesDTO {
        @SerializedName("uniqueId")
        private String uniqueId;
        @SerializedName("funding")
        private List<?> funding;
        @SerializedName("developers")
        private List<DevelopersDTO> developers;
        @SerializedName("artifactVersion")
        private String artifactVersion;
        @SerializedName("description")
        private String description;
        @SerializedName("name")
        private String name;
        @SerializedName("licenses")
        private List<String> licenses;
        @SerializedName("scm")
        private ScmDTO scm;
        @SerializedName("website")
        private String website;

        public String getUniqueId() {
            return uniqueId;
        }

        public List<?> getFunding() {
            return funding;
        }

        public List<DevelopersDTO> getDevelopers() {
            return developers;
        }

        public String getDevelopersStr() {
            StringJoiner stringJoiner = new StringJoiner(", ");
            for (DevelopersDTO developer : getDevelopers()) {
                stringJoiner.add(developer.getName());
            }

            return stringJoiner.toString();
        }

        public String getArtifactVersion() {
            return artifactVersion;
        }

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public List<String> getLicenses() {
            return licenses;
        }

        public ScmDTO getScm() {
            return scm;
        }

        public String getWebsite() {
            return website;
        }

        public static class ScmDTO {
            @SerializedName("connection")
            private String connection;
            @SerializedName("url")
            private String url;

            public String getConnection() {
                return connection;
            }

            public String getUrl() {
                return url;
            }
        }

        public static class DevelopersDTO {
            @SerializedName("organisationUrl")
            private String organisationUrl;
            @SerializedName("name")
            private String name;

            public String getOrganisationUrl() {
                return organisationUrl;
            }

            public String getName() {
                return name;
            }
        }
    }
    public static class RawStringJsonAdapter extends TypeAdapter<String> {

        @Override
        public void write(JsonWriter out, String value) throws IOException {
            out.jsonValue(value);
        }

        @Override
        public String read(JsonReader in) {
            return JsonParser.parseReader(in).toString();
        }
    }


}
