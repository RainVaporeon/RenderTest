package com.spiritlight.rendertest.math;

import java.util.Arrays;
import java.util.Iterator;

/**
 * An immutable class representing a row of a matrix's elements.
 * This method may also be iterated for the elements.
 */
public class MatrixElement implements Iterable<Double> {
    public final int length;
    private final double[] elements;

    public MatrixElement(double... elements) {
        this.length = elements.length;
        this.elements = elements;
    }

    public double getElement(int index) {
        if(index < 0 || index >= length) throw new IndexOutOfBoundsException(index);
        return elements[index];
    }

    public double[] getElements() {
        return elements.clone();
    }

    @Override
    public Iterator<Double> iterator() {
        return Arrays.stream(elements).boxed().iterator();
    }
}
