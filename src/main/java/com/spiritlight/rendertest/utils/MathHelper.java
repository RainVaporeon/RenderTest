package com.spiritlight.rendertest.utils;

import java.util.Arrays;

public final class MathHelper {

    // im not really an optional enjoyer you see

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static int min(int... parameters) {
        assertNonNull(parameters);
        return Arrays.stream(parameters).min().getAsInt();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static double min(double... parameters) {
        assertNonNull(parameters);
        return Arrays.stream(parameters).min().getAsDouble();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static long min(long... parameters) {
        assertNonNull(parameters);
        return Arrays.stream(parameters).min().getAsLong();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static int max(int... parameters) {
        assertNonNull(parameters);
        return Arrays.stream(parameters).max().getAsInt();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static double max(double... parameters) {
        assertNonNull(parameters);
        return Arrays.stream(parameters).max().getAsDouble();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static long max(long... parameters) {
        assertNonNull(parameters);
        return Arrays.stream(parameters).max().getAsLong();
    }

    public static boolean inRange(double num, double min, double max) {
        return num > min && num < max;
    }

    public static double clamp(double val, double min, double max) {
        if(inRange(val, min, max)) return val;
        return val < min ? min : max;
    }

    private static void assertNonNull(Object element) {
        if(element == null) throw new NullPointerException();
    }
}
