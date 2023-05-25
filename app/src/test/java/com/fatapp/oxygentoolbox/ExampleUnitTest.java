package com.fatapp.oxygentoolbox;

import static org.junit.Assert.assertEquals;

import com.fatapp.oxygentoolbox.util.VariableChangeListener;
import com.fatapp.oxygentoolbox.util.VariableChangeSupport;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void variableChangeTest() {
        VariableChangeSupport<String> stringVariableChangeSupport = new VariableChangeSupport<>("Hello", new VariableChangeListener() {
            @Override
            public <T> void onChange(T newValue, T oldValue) {
                System.out.println("newValue = " + newValue);
                System.out.println("oldValue = " + oldValue);
            }
        });

        stringVariableChangeSupport.setValue("Hello");
        stringVariableChangeSupport.setValue("Hi");
        stringVariableChangeSupport.setValue("Hi");
        stringVariableChangeSupport.setValue("HI");
        stringVariableChangeSupport.setValue("HI");
    }
}