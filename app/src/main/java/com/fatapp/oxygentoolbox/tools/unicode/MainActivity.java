package com.fatapp.oxygentoolbox.tools.unicode;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.fatapp.oxygentoolbox.util.tool.BaseActivityNormal;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends BaseActivityNormal {
    private final List<String> BASE_LIST = List.of(ResourceUtil.getString(R.string.tool_unicode_text),
            ResourceUtil.getString(R.string.tool_unicode_unicode),
            ResourceUtil.getString(R.string.tool_unicode_ascii));

    private int baseFrom = 0;
    private int baseTo = 1;
    private boolean isFromText = true;

    private TextView textViewBaseFrom;
    private ImageView imageViewSwap;
    private TextView textViewBaseTo;
    private EditText editTextFrom;
    private TextView textViewTo;
    private ImageView imageViewConvert;

    private void initView() {
        textViewBaseFrom = findViewById(R.id.text_view_base_from);
        imageViewSwap = findViewById(R.id.image_view_swap);
        textViewBaseTo = findViewById(R.id.text_view_base_to);
        editTextFrom = findViewById(R.id.edit_text_from);
        textViewTo = findViewById(R.id.text_view_to);
        imageViewConvert = findViewById(R.id.image_view_convert);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.loadView(this, R.layout.activity_tool_unicode);

        initView();
        initChoose();
        initConverter();
    }

    private void initChoose() {
        textViewBaseFrom.setOnClickListener(v -> new MaterialAlertDialogBuilder(this)
                .setTitle(ResourceUtil.getString(R.string.tool_common_choose))
                .setItems(isFromText ?
                                List.of(BASE_LIST.get(0)).toArray(String[]::new) :
                                List.of(BASE_LIST.get(1), BASE_LIST.get(2)).toArray(String[]::new),
                        (dialog, which) -> {
                            textViewBaseFrom.setText(isFromText ? BASE_LIST.get(0) : BASE_LIST.get(which + 1));
                            baseFrom = isFromText ? 0 : (which + 1);
                            if (textViewTo.getText().length() > 0) {
                                convert();
                            }
                        })
                .show());
        textViewBaseTo.setOnClickListener(v -> new MaterialAlertDialogBuilder(this)
                .setTitle(ResourceUtil.getString(R.string.tool_common_choose))
                .setItems(isFromText ?
                        List.of(BASE_LIST.get(1), BASE_LIST.get(2)).toArray(String[]::new):
                        List.of(BASE_LIST.get(0)).toArray(String[]::new),
                        (dialog, which) -> {
                            textViewBaseTo.setText(isFromText ? BASE_LIST.get(which + 1) : BASE_LIST.get(0));
                            baseTo = isFromText ? (which + 1) : 0;
                            if (textViewTo.getText().length() > 0) {
                                convert();
                            }
                        })
                .show());
        imageViewSwap.setOnClickListener(v -> {
            AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageViewSwap.getDrawable();
            animatedVectorDrawable.start();
            isFromText = !isFromText;
            {
                int temp = baseFrom;
                baseFrom = baseTo;
                baseTo = temp;
            }
            {
                String temp = textViewBaseFrom.getText().toString();
                textViewBaseFrom.setText(textViewBaseTo.getText());
                textViewBaseTo.setText(temp);
            }
            editTextFrom.setText(null);
            textViewTo.setText(null);
        });
    }

    private void initConverter() {
        editTextFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imageViewConvert.setEnabled(s.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        textViewTo.setOnFocusChangeListener((view, b) -> ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editTextFrom.getWindowToken(), 0));
        imageViewConvert.setEnabled(false);
        imageViewConvert.setOnClickListener(view -> convert());
    }

    private void convert() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editTextFrom.getWindowToken(), 0);
        switch (baseFrom) {
            case 0 -> {
                switch (baseTo) {
                    case 1 ->
                            textViewTo.setText(Utils.stringToUnicode(editTextFrom.getText().toString()));
                    case 2 ->
                            textViewTo.setText(Utils.stringToAscii(editTextFrom.getText().toString()));
                }
            }
            case 1 -> {
                try {
                    textViewTo.setText(Utils.unicodeToString(editTextFrom.getText().toString()));
                } catch (Exception e) {
                    Snackbar.make(getConstraintLayoutRoot(), ResourceUtil.getString(R.string.tool_unicode_illegal_input), Snackbar.LENGTH_LONG).show();
                }
            }
            case 2 -> {
                try {
                    textViewTo.setText(Utils.asciiToString(editTextFrom.getText().toString()));
                } catch (Exception e) {
                    Snackbar.make(getConstraintLayoutRoot(), ResourceUtil.getString(R.string.tool_unicode_illegal_input), Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }
}
