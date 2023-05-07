package com.spiritlight.rendertest.math;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

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

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatrixElement element = (MatrixElement) o;
        return length == element.length && Arrays.equals(elements, element.elements);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(length);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }
}
