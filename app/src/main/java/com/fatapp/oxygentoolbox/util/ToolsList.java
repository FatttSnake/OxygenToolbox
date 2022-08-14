package com.fatapp.oxygentoolbox.util;

import androidx.annotation.NonNull;

import com.fatapp.oxygentoolbox.util.json.ToolsJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ToolsList {
    private static List<Tool> toolList = new ArrayList<>();

    public static void init(InputStream file) throws IOException {
        toolList.clear();
        String str;
        StringBuilder jsonStringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(file);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        while ((str = bufferedReader.readLine()) != null) {
            jsonStringBuilder.append(str);
        }
        file.close();
        List<ToolsJson> toolsJsonList = new Gson().fromJson(jsonStringBuilder.toString(), new TypeToken<List<ToolsJson>>() {
        }.getType());

        for (ToolsJson toolsJson : toolsJsonList) {
            Tool tool = new Tool();
            tool.setFont(toolsJson.getContent().getFont());
            tool.setIcon(toolsJson.getContent().getIcon());
            tool.setFoldLayoutTitle(getLocale(toolsJson.getContent().getTitle()));
            for (ToolsJson.Content.Buttons cButton : toolsJson.getContent().getButtons()) {
                Button button = new Button();
                button.setText(getLocale(cButton.getText()));
                button.setActivity(cButton.getActivity());
                tool.buttonList.add(button);
            }
            toolList.add(tool);
        }
    }

    private static String getLocale(Locales strings) {
        if (SharedPreferencesUtils.getPreferenceLocale().getLanguage().equals("zh")) {
            return strings.getCn();
        }
        return strings.getEn();
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
        private String activity;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }
    }

    public static String getToolName(@NonNull String className) {
        for (Tool tool : toolList) {
            for (Button button : tool.getButtonList()) {
                if (button.getActivity().equals(className)) {
                    return button.getText();
                }
            }
        }
        return null;
    }
}
