package com.spiritlight.rendertest.utils;

public class Objects {

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        try {
            return (T) object;
        } catch (ClassCastException e) {
            return null;
        }
    }
}
