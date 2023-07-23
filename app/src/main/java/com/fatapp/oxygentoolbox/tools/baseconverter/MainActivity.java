package com.fatapp.oxygentoolbox.tools.baseconverter;

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
import com.fatapp.oxygentoolbox.util.HexConversionUtils;
import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.fatapp.oxygentoolbox.util.tool.BaseActivityNormal;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

public class MainActivity extends BaseActivityNormal {

    private final String[] BASE_ITEMS = new String[35];

    {
        for (int i = 0; i < 35; i++) {
            switch (i + 2) {
                case 2 -> BASE_ITEMS[i] = ResourceUtil.getString(R.string.tool_baseconverter_base_2);
                case 8 -> BASE_ITEMS[i] = ResourceUtil.getString(R.string.tool_baseconverter_base_8);
                case 10 -> BASE_ITEMS[i] = ResourceUtil.getString(R.string.tool_baseconverter_base_10);
                case 16 -> BASE_ITEMS[i] = ResourceUtil.getString(R.string.tool_baseconverter_base_16);
                default -> BASE_ITEMS[i] = String.valueOf(i + 2);
            }
        }
    }

    private int baseFrom = 10;
    private int baseTo = 2;

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
        super.loadView(this, R.layout.activity_tool_baseconverter);

        initView();
        initBaseChoose();
        initConverter();
    }

    private void initBaseChoose() {
        textViewBaseFrom.setOnClickListener(view -> new MaterialAlertDialogBuilder(this)
                .setTitle(ResourceUtil.getString(R.string.tool_baseconverter_choose_base))
                .setItems(BASE_ITEMS, (dialogInterface, i) -> {
                    textViewBaseFrom.setText(BASE_ITEMS[i]);
                    baseFrom = i + 2;
                    editTextFrom.setText(null);
                    textViewTo.setText(null);
                })
                .show());

        textViewBaseTo.setOnClickListener(view -> new MaterialAlertDialogBuilder(this)
                .setTitle(ResourceUtil.getString(R.string.tool_baseconverter_choose_base))
                .setItems(BASE_ITEMS, (dialogInterface, i) -> {
                    textViewBaseTo.setText(BASE_ITEMS[i]);
                    baseTo = i + 2;
                    if (textViewTo.getText().length() > 0) {
                        convert();
                    }
                })
                .show());

        imageViewSwap.setOnClickListener(view -> {
            AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageViewSwap.getDrawable();
            animatedVectorDrawable.start();
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
        StringBuffer beforeText = new StringBuffer();
        editTextFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                beforeText.delete(0, beforeText.length());
                beforeText.append(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                imageViewConvert.setEnabled(charSequence.length() != 0);
                if (!Pattern.compile(HexConversionUtils.getRegExp(baseFrom)).matcher(charSequence).matches()) {
                    editTextFrom.setText(beforeText.toString());
                    editTextFrom.setSelection(beforeText.toString().length() - 1);
                } else {
                    textViewTo.setText(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        textViewTo.setOnFocusChangeListener((view, b) -> ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editTextFrom.getWindowToken(), 0));

        imageViewConvert.setEnabled(false);
        imageViewConvert.setOnClickListener(view -> convert());
    }

    private void convert() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editTextFrom.getWindowToken(), 0);
        if (baseFrom == baseTo) {
            textViewTo.setText(editTextFrom.getText());
        }
        try {
            String result = HexConversionUtils.decimalToBase(HexConversionUtils.baseToDecimal(editTextFrom.getText().toString(), baseFrom), baseTo);
            textViewTo.setText(result);
        } catch (Exception e) {
            textViewTo.setText(ResourceUtil.getString(R.string.base_nan));
            Snackbar.make(getConstraintLayoutRoot(), ResourceUtil.getString(R.string.tool_baseconverter_illegal_number), Snackbar.LENGTH_LONG).show();
        }
    }
}
