package com.fatapp.oxygentoolbox.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ToolsList {
    private static List<Tool> toolList = new ArrayList<>();

    public static void init(InputStream file) throws IOException {
        int i;
        StringBuilder jsonStringBuilder = new StringBuilder();

        while ((i = file.read()) != -1) {
            jsonStringBuilder.append((char) i);
        }
        file.close();
        List<ToolsJson> toolsJsonList = new Gson().fromJson(jsonStringBuilder.toString(), new TypeToken<List<ToolsJson>>(){}.getType());

        for (ToolsJson toolsJson : toolsJsonList) {
            Tool tool = new Tool();
            tool.setFont(toolsJson.getContent().getFont());
            tool.setIcon(toolsJson.getContent().getIcon());
            tool.setFoldLayoutTitle(toolsJson.getContent().getTitle().getCn());
            for (ToolsJson.Content.Buttons cButton : toolsJson.getContent().getButtons()) {
                Button button = new Button();
                button.setText(cButton.getText().getCn());
                button.setActivity(cButton.getActivity());
                tool.buttonList.add(button);
            }
            toolList.add(tool);
        }
    }

    public static List<Tool> getToolList() {
        return toolList;
    }

    public static void setToolList(List<Tool> toolList) {
        ToolsList.toolList = toolList;
    }

    public static class Tool {
        private String font;
        private String icon;
        private String foldLayoutTitle;
        private List<Button> buttonList = new ArrayList<>();

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

        public String getFoldLayoutTitle() {
            return foldLayoutTitle;
        }

        public void setFoldLayoutTitle(String foldLayoutTitle) {
            this.foldLayoutTitle = foldLayoutTitle;
        }

        public List<Button> getButtonList() {
            return buttonList;
        }

        public void setButtonList(List<Button> buttonList) {
            this.buttonList = buttonList;
        }
    }

    public static class Button {
        private String text;
        private Integer activity;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Integer getActivity() {
            return activity;
        }

        public void setActivity(Integer activity) {
            this.activity = activity;
        }
    }
}
