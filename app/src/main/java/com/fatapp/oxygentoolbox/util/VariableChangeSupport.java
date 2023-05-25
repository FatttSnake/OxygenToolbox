package com.fatapp.oxygentoolbox.util;

import java.util.Objects;

public class VariableChangeSupport<T> {
    private T value;
    private VariableChangeListener onChangeListener;

    public VariableChangeSupport() {
    }

    public VariableChangeSupport(T value) {
        this.value = value;
    }

    public VariableChangeSupport(VariableChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public VariableChangeSupport(T value, VariableChangeListener onChangeListener) {
        this.value = value;
        this.onChangeListener = onChangeListener;
    }

    public void setValue(T value) {
        if (!Objects.equals(this.value, value)) {
            if (onChangeListener != null) {
                onChangeListener.onChange(value, this.value);
            }
            this.value = value;
        }
    }

    public T getValue() {
        return value;
    }

    public void setOnChangeListener(VariableChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }
}
