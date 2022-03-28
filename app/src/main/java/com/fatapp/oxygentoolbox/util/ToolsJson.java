package com.fatapp.oxygentoolbox.util;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ToolsJson {
    @SerializedName("id")
    private String id;
    @SerializedName("content")
    private Content content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public static class Content {
        @SerializedName("version")
        private String version;
        @SerializedName("versionID")
        private Integer versionID;
        @SerializedName("mainVersion")
        private Integer mainVersion;
        @SerializedName("font")
        private String font;
        @SerializedName("icon")
        private String icon;
        @SerializedName("title")
        private Title title;
        @SerializedName("desc")
        private Desc desc;
        @SerializedName("buttons")
        private List<Buttons> buttons;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Integer getVersionID() {
            return versionID;
        }

        public void setVersionID(Integer versionID) {
            this.versionID = versionID;
        }

        public Integer getMainVersion() {
            return mainVersion;
        }

        public void setMainVersion(Integer mainVersion) {
            this.mainVersion = mainVersion;
        }

        public Title getTitle() {
            return title;
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public String getFont() {
            return font;
        }

        public void setFont(String font) {
            this.font = font;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Desc getDesc() {
            return desc;
        }

        public void setDesc(Desc desc) {
            this.desc = desc;
        }

        public List<Buttons> getButtons() {
            return buttons;
        }

        public void setButtons(List<Buttons> buttons) {
            this.buttons = buttons;
        }

        public static class Title implements Locales {
            @SerializedName("cn")
            private String cn;
            @SerializedName("en")
            private String en;

            @Override
            public String getCn() {
                return cn;
            }

            @Override
            public void setCn(String cn) {
                this.cn = cn;
            }

            @Override
            public String getEn() {
                return en;
            }

            @Override
            public void setEn(String en) {
                this.en = en;
            }
        }

        public static class Desc implements Locales {
            @SerializedName("cn")
            private String cn;
            @SerializedName("en")
            private String en;

            @Override
            public String getCn() {
                return cn;
            }

            @Override
            public void setCn(String cn) {
                this.cn = cn;
            }

            @Override
            public String getEn() {
                return en;
            }

            @Override
            public void setEn(String en) {
                this.en = en;
            }
        }

        public static class Buttons {
            @SerializedName("text")
            private Text text;
            @SerializedName("activity")
            private Integer activity;

            public Text getText() {
                return text;
            }

            public void setText(Text text) {
                this.text = text;
            }

            public Integer getActivity() {
                return activity;
            }

            public void setActivity(Integer activity) {
                this.activity = activity;
            }

            public static class Text implements Locales {
                @SerializedName("cn")
                private String cn;
                @SerializedName("en")
                private String en;

                @Override
                public String getCn() {
                    return cn;
                }

                @Override
                public void setCn(String cn) {
                    this.cn = cn;
                }

                @Override
                public String getEn() {
                    return en;
                }

                @Override
                public void setEn(String en) {
                    this.en = en;
                }
            }
        }
    }
}