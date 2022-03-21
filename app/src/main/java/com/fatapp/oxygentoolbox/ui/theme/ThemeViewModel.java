package com.fatapp.oxygentoolbox.ui.theme;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ThemeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ThemeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is theme fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}