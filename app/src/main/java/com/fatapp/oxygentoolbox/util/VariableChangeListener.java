package com.fatapp.oxygentoolbox.util;

public interface VariableChangeListener {
    <T> void onChange(T newValue, T oldValue);
}
