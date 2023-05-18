package com.spiritlight.rendertest.math.exceptions;

public class MatrixException extends CalculationException {

    public MatrixException(String s) {
        super(s);
    }

    public static MatrixException mismatchSize(double val, double expected) {
        return new MatrixException(
                String.format("value mismatch: expeced %f, but got %f", val, expected)
        );
    }
}
