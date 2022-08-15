package com.fatapp.oxygentoolbox.tools.translation;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.tools.translation.util.ResponseListener;
import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.fatapp.oxygentoolbox.util.ToolsList;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.StringJoiner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final String TOOL_NAME = ToolsList.getToolName(getClass().getName());
    private final String URL_YOU_DAO = "http://fanyi.youdao.com/translate?&doctype=json&type=%s&i=%s";

    private final String LANGUAGE_CHINESE = "ZH_CN";
    private final String LANGUAGE_ENGLISH = "EN";
    private final String LANGUAGE_JAPANESE = "JA";
    private final String LANGUAGE_KOREAN = "KR";
    private final String[] LANGUAGE_ITEMS = {LANGUAGE_CHINESE, LANGUAGE_ENGLISH, LANGUAGE_JAPANESE, LANGUAGE_KOREAN};
    private final String[] CHINESE_ITEMS = {ResourceUtil.getString(R.string.tool_translation_language_chinese),
            ResourceUtil.getString(R.string.tool_translation_language_english),
            ResourceUtil.getString(R.string.tool_translation_language_japanese),
            ResourceUtil.getString(R.string.tool_translation_language_korean)};
    private final String[] OTHER_ITEMS = {ResourceUtil.getString(R.string.tool_translation_language_chinese)};

    private String languageFrom = LANGUAGE_CHINESE;
    private String languageTo = LANGUAGE_ENGLISH;

    private ConstraintLayout constraintLayoutRoot;
    private Toolbar toolbar;
    private TextView textViewLanguageFrom;
    private ImageView imageViewSwap;
    private TextView textViewLanguageTo;
    private EditText editTextFrom;
    private TextView textViewTo;
    private ImageView imageViewTranslate;
    private ProgressBar progressBarInTranslation;

    private void initView() {
        constraintLayoutRoot = findViewById(R.id.constraint_layout_root);
        toolbar = findViewById(R.id.toolbar);
        textViewLanguageFrom = findViewById(R.id.text_view_language_from);
        imageViewSwap = findViewById(R.id.image_view_swap);
        textViewLanguageTo = findViewById(R.id.text_view_language_to);
        editTextFrom = findViewById(R.id.edit_text_from);
        textViewTo = findViewById(R.id.edit_text_to);
        imageViewTranslate = findViewById(R.id.image_view_translate);
        progressBarInTranslation = findViewById(R.id.progress_bar_in_translation);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_translation);

        initView();
        initLayout();
        initLanguageChoose();
        initTranslation();
    }

    private void initTranslation() {
        editTextFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                imageViewTranslate.setEnabled(charSequence.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        textViewTo.setOnFocusChangeListener((view, b) -> ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editTextFrom.getWindowToken(), 0));

        imageViewTranslate.setEnabled(false);
        imageViewTranslate.setOnClickListener(view -> translate());
    }

    private void initLanguageChoose() {
        textViewLanguageFrom.setOnClickListener(view -> new MaterialAlertDialogBuilder(this)
                .setTitle(ResourceUtil.getString(R.string.tool_translation_choose_language))
                .setItems(languageTo.equals(LANGUAGE_CHINESE) ? CHINESE_ITEMS : OTHER_ITEMS, (dialogInterface, i) -> {
                    textViewLanguageFrom.setText(CHINESE_ITEMS[i]);
                    languageFrom = LANGUAGE_ITEMS[i];
                })
                .show());

        textViewLanguageTo.setOnClickListener(view -> new MaterialAlertDialogBuilder(this)
                .setTitle(ResourceUtil.getString(R.string.tool_translation_choose_language))
                .setItems(languageFrom.equals(LANGUAGE_CHINESE) ? CHINESE_ITEMS : OTHER_ITEMS, (dialogInterface, i) -> {
                    textViewLanguageTo.setText(CHINESE_ITEMS[i]);
                    languageTo = LANGUAGE_ITEMS[i];
                })
                .show());

        imageViewSwap.setOnClickListener(view -> {
            AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageViewSwap.getDrawable();
            animatedVectorDrawable.start();
            String temp = languageFrom;
            languageFrom = languageTo;
            languageTo = temp;
            temp = textViewLanguageFrom.getText().toString();
            textViewLanguageFrom.setText(textViewLanguageTo.getText());
            textViewLanguageTo.setText(temp);
        });
    }

    private void initLayout() {
        constraintLayoutRoot.setPadding(0, ResourceUtil.getStatusBarHeight(getWindow(), getApplicationContext()), 0, 0);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(TOOL_NAME);
    }

    private void translate() {
        editTextFrom.setEnabled(false);
        imageViewTranslate.setEnabled(false);
        progressBarInTranslation.setVisibility(View.VISIBLE);

        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editTextFrom.getWindowToken(), 0);
        translateYouDao(languageFrom, languageTo, editTextFrom.getText().toString(), new ResponseListener() {
            @Override
            public void onResponse(int code, String responseBody) {
                if (code == 200) {
                    try {
                        final JSONArray partArray = new JSONObject(responseBody).getJSONArray("translateResult");
                        final StringJoiner resultJoiner = new StringJoiner("\n");
                        for (int i = 0; i < partArray.length(); i++) {
                            final JSONArray sentenceArray = partArray.getJSONArray(i);
                            final StringJoiner sentenceJoiner = new StringJoiner(new JSONObject(responseBody).getString("type").toLowerCase().startsWith("zh") ? " " : "");
                            for (int j = 0; j < sentenceArray.length(); j++) {
                                sentenceJoiner.add(sentenceArray.getJSONObject(j).getString("tgt"));
                            }
                            resultJoiner.add(sentenceJoiner.toString());
                        }
                        textViewTo.setText(resultJoiner.toString());
                        editTextFrom.setEnabled(true);
                        imageViewTranslate.setEnabled(true);
                        progressBarInTranslation.setVisibility(View.INVISIBLE);
                    } catch (JSONException e) {
                        onFailed();
                    }
                } else {
                    onFailed();
                }
            }

            @Override
            public void onFailed() {
                textViewTo.setText(null);
                editTextFrom.setEnabled(true);
                imageViewTranslate.setEnabled(true);
                progressBarInTranslation.setVisibility(View.INVISIBLE);
                Snackbar.make(constraintLayoutRoot, "翻译失败", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void translateYouDao(String from, String to, String text, @NonNull ResponseListener responseListener) {
        if (from.equals(to)) {
            responseListener.onFailed();
            return;
        }

        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                try {
                    Request request = new Request.Builder().url(String.format(URL_YOU_DAO, from.toUpperCase() + 2 + to.toUpperCase(), URLEncoder.encode(text, StandardCharsets.UTF_8.toString()))).build();
                    try (Response response = okHttpClient.newCall(request).execute()) {
                        int code = response.code();
                        String body = Objects.requireNonNull(response.body()).string();
                        runOnUiThread(() -> responseListener.onResponse(code, body));
                    } catch (IOException e) {
                        runOnUiThread(responseListener::onFailed);
                    }
                } catch (UnsupportedEncodingException e) {
                    runOnUiThread(responseListener::onFailed);
                }
            }
        }.start();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
