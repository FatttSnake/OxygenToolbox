package com.fatapp.oxygentoolbox.tools.timescreen;

import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.fatapp.oxygentoolbox.util.VariableChangeListener;
import com.fatapp.oxygentoolbox.util.VariableChangeSupport;
import com.ypz.bangscreentools.BangScreenTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private int uiMode = ResourceUtil.getAppUiMode();
    private VariableChangeSupport<String> hourTenVariableChangeSupport;
    private VariableChangeSupport<String> hourOneVariableChangeSupport;
    private VariableChangeSupport<String> minuteTenVariableChangeSupport;
    private VariableChangeSupport<String> minuteOneVariableChangeSupport;
    private VariableChangeSupport<String> secondTenVariableChangeSupport;
    private VariableChangeSupport<String> secondOneVariableChangeSupport;
    private VariableChangeSupport<Boolean> dateVisibilityChangeSupport;
    private VariableChangeSupport<Boolean> uiVisibilityChangeSupport;

    private ConstraintLayout constraintLayoutTimeScreen;
    private ImageView imageViewMode;
    private TextSwitcher textSwitcherHourTen;
    private TextSwitcher textSwitcherHourOne;
    private TextView textViewColonHourMinute;
    private TextSwitcher textSwitcherMinuteTen;
    private TextSwitcher textSwitcherMinuteOne;
    private TextView textViewColonMinuteSecond;
    private TextSwitcher textSwitcherSecondTen;
    private TextSwitcher textSwitcherSecondOne;
    private TextView textViewDate;

    private void initView() {
        constraintLayoutTimeScreen = findViewById(R.id.constraint_layout_time_screen);
        imageViewMode = findViewById(R.id.image_view_mode);
        textSwitcherHourTen = findViewById(R.id.text_switcher_hour_ten);
        textSwitcherHourOne = findViewById(R.id.text_switcher_hour_one);
        textViewColonHourMinute = findViewById(R.id.text_view_colon_hour_minute);
        textSwitcherMinuteTen = findViewById(R.id.text_switcher_minute_ten);
        textSwitcherMinuteOne = findViewById(R.id.text_switcher_minute_one);
        textViewColonMinuteSecond = findViewById(R.id.text_view_colon_minute_second);
        textSwitcherSecondTen = findViewById(R.id.text_switcher_second_ten);
        textSwitcherSecondOne = findViewById(R.id.text_switcher_second_one);
        textViewDate = findViewById(R.id.text_view_date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BangScreenTools.getBangScreenTools().fullscreen(getWindow(), this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_time_screen);

        initView();
        initTextSwitcher();

        constraintLayoutTimeScreen.setOnClickListener(view -> uiVisibilityChangeSupport.setValue(!uiVisibilityChangeSupport.getValue()));
        uiVisibilityChangeSupport = new VariableChangeSupport<>(true, new VariableChangeListener() {
            @Override
            public <T> void onChange(T newValue, T oldValue) {
                ObjectAnimator.ofFloat(imageViewMode, View.ALPHA, (boolean) newValue ? 0.0f : 1.0f, (boolean) newValue ? 1.0f : 0.0f).start();
            }
        });

        if (uiMode >= Configuration.UI_MODE_NIGHT_YES) {
            imageViewMode.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.animation_dark_to_light_mode));
        }
        imageViewMode.setOnClickListener(view -> {
            if (uiMode < Configuration.UI_MODE_NIGHT_YES) {
                uiMode = ResourceUtil.UI_MODE_DARK;
                setColors(ResourceUtil.getColor(R.color.app_show_dark_background), ResourceUtil.getColor(R.color.app_show_dark_primary_text));
                imageViewMode.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.animation_light_to_dark_mode));
                AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageViewMode.getDrawable();
                animatedVectorDrawable.start();
            } else {
                uiMode = ResourceUtil.UI_MODE_LIGHT;
                setColors(ResourceUtil.getColor(R.color.app_show_light_background), ResourceUtil.getColor(R.color.app_show_light_primary_text));
                imageViewMode.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.animation_dark_to_light_mode));
                AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageViewMode.getDrawable();
                animatedVectorDrawable.start();
            }
        });

        dateVisibilityChangeSupport = new VariableChangeSupport<>(true, new VariableChangeListener() {
            @Override
            public <T> void onChange(T newValue, T oldValue) {
                ObjectAnimator.ofFloat(textViewDate, View.ALPHA, (boolean) newValue ? 0.0f : 1.0f, (boolean) newValue ? 1.0f : 0.0f).start();
            }
        });

        textViewDate.setOnClickListener(view -> dateVisibilityChangeSupport.setValue(!dateVisibilityChangeSupport.getValue()));

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (ResourceUtil.getAppLocale().getLanguage().equals("zh")) {
                        textViewDate.setText(new SimpleDateFormat("yyyy年M月d日 EEEE", ResourceUtil.getAppLocale()).format(new Date(System.currentTimeMillis())));
                    } else {
                        textViewDate.setText(new SimpleDateFormat("EEEE, d MMMM, yyyy", ResourceUtil.getAppLocale()).format(new Date(System.currentTimeMillis())));
                    }
                    hourTenVariableChangeSupport.setValue(DateFormat.format("HH", System.currentTimeMillis()).toString().substring(0, 1));
                    hourOneVariableChangeSupport.setValue(DateFormat.format("HH", System.currentTimeMillis()).toString().substring(1));
                    minuteTenVariableChangeSupport.setValue(DateFormat.format("mm", System.currentTimeMillis()).toString().substring(0, 1));
                    minuteOneVariableChangeSupport.setValue(DateFormat.format("mm", System.currentTimeMillis()).toString().substring(1));
                    secondTenVariableChangeSupport.setValue(DateFormat.format("ss", System.currentTimeMillis()).toString().substring(0, 1));
                    secondOneVariableChangeSupport.setValue(DateFormat.format("ss", System.currentTimeMillis()).toString().substring(1));
                });
            }
        };

        new Timer().schedule(timerTask, 0, 100L);
    }

    private void initTextSwitcher() {
        ViewSwitcher.ViewFactory viewFactory = () -> {
            TextView textView = new TextView(getApplicationContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(ResourceUtil.getColor(R.color.app_show_primary_text));
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setTextSize(100f);
            return textView;
        };

        String hourTen = DateFormat.format("HH", System.currentTimeMillis()).toString().substring(0, 1);
        textSwitcherHourTen.setFactory(viewFactory);
        textSwitcherHourTen.setCurrentText(hourTen);
        hourTenVariableChangeSupport = new VariableChangeSupport<>(hourTen, new VariableChangeListener() {
            @Override
            public <T> void onChange(T newValue, T oldValue) {
                textSwitcherHourTen.setText((CharSequence) newValue);
            }
        });

        String hourOne = DateFormat.format("HH", System.currentTimeMillis()).toString().substring(1);
        textSwitcherHourOne.setFactory(viewFactory);
        textSwitcherHourOne.setCurrentText(hourOne);
        hourOneVariableChangeSupport = new VariableChangeSupport<>(hourOne, new VariableChangeListener() {
            @Override
            public <T> void onChange(T newValue, T oldValue) {
                textSwitcherHourOne.setText((CharSequence) newValue);
            }
        });

        String minuteTen = DateFormat.format("mm", System.currentTimeMillis()).toString().substring(0, 1);
        textSwitcherMinuteTen.setFactory(viewFactory);
        textSwitcherMinuteTen.setCurrentText(minuteTen);
        minuteTenVariableChangeSupport = new VariableChangeSupport<>(minuteTen, new VariableChangeListener() {
            @Override
            public <T> void onChange(T newValue, T oldValue) {
                textSwitcherMinuteTen.setText((CharSequence) newValue);
            }
        });

        String minuteOne = DateFormat.format("mm", System.currentTimeMillis()).toString().substring(1);
        textSwitcherMinuteOne.setFactory(viewFactory);
        textSwitcherMinuteOne.setCurrentText(minuteOne);
        minuteOneVariableChangeSupport = new VariableChangeSupport<>(minuteOne, new VariableChangeListener() {
            @Override
            public <T> void onChange(T newValue, T oldValue) {
                textSwitcherMinuteOne.setText((CharSequence) newValue);
            }
        });

        String secondTen = DateFormat.format("ss", System.currentTimeMillis()).toString().substring(0, 1);
        textSwitcherSecondTen.setFactory(viewFactory);
        textSwitcherSecondTen.setCurrentText(secondTen);
        secondTenVariableChangeSupport = new VariableChangeSupport<>(secondTen, new VariableChangeListener() {
            @Override
            public <T> void onChange(T newValue, T oldValue) {
                textSwitcherSecondTen.setText((CharSequence) newValue);
            }
        });

        String secondOne = DateFormat.format("ss", System.currentTimeMillis()).toString().substring(1);
        textSwitcherSecondOne.setFactory(viewFactory);
        textSwitcherSecondOne.setCurrentText(secondOne);
        secondOneVariableChangeSupport = new VariableChangeSupport<>(secondOne, new VariableChangeListener() {
            @Override
            public <T> void onChange(T newValue, T oldValue) {
                textSwitcherSecondOne.setText((CharSequence) newValue);
            }
        });
    }

    private void setColors(@ColorInt int backgroundColor, @ColorInt int primaryTextColor) {
        ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofArgb(constraintLayoutTimeScreen, "backgroundColor", ((ColorDrawable) constraintLayoutTimeScreen.getBackground()).getColor(), backgroundColor);
        backgroundColorAnimator.setDuration(500L);
        backgroundColorAnimator.start();
        ((TextView) textSwitcherHourTen.getChildAt(0)).setTextColor(primaryTextColor);
        ((TextView) textSwitcherHourTen.getChildAt(1)).setTextColor(primaryTextColor);
        ((TextView) textSwitcherHourOne.getChildAt(0)).setTextColor(primaryTextColor);
        ((TextView) textSwitcherHourOne.getChildAt(1)).setTextColor(primaryTextColor);
        textViewColonHourMinute.setTextColor(primaryTextColor);
        ((TextView) textSwitcherMinuteTen.getChildAt(0)).setTextColor(primaryTextColor);
        ((TextView) textSwitcherMinuteTen.getChildAt(1)).setTextColor(primaryTextColor);
        ((TextView) textSwitcherMinuteOne.getChildAt(0)).setTextColor(primaryTextColor);
        ((TextView) textSwitcherMinuteOne.getChildAt(1)).setTextColor(primaryTextColor);
        textViewColonMinuteSecond.setTextColor(primaryTextColor);
        ((TextView) textSwitcherSecondTen.getChildAt(0)).setTextColor(primaryTextColor);
        ((TextView) textSwitcherSecondTen.getChildAt(1)).setTextColor(primaryTextColor);
        ((TextView) textSwitcherSecondOne.getChildAt(0)).setTextColor(primaryTextColor);
        ((TextView) textSwitcherSecondOne.getChildAt(1)).setTextColor(primaryTextColor);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        BangScreenTools.getBangScreenTools().windowChangeFullscreen(getWindow());
        super.onWindowFocusChanged(hasFocus);
    }
}