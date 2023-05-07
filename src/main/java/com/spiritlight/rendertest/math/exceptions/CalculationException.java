package com.spiritlight.rendertest.math.exceptions;

public class CalculationException extends RuntimeException {

    public CalculationException(String s) {
        super(s);
    }

    public CalculationException() {
        super();
    }

    public CalculationException(Throwable cause) {
        super(cause);
    }
}
