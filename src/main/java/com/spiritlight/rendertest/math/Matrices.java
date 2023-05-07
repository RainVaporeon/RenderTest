package com.spiritlight.rendertest.math;

import java.util.Arrays;

public final class Matrices {

    private Matrices() {}

    /**
     * Generates a matrix with identical rows
     * @param cols The columns to generate
     * @param element The elements to insert
     * @return A new Matrix instance with all given rows
     */
    public static Matrix generate(int cols, MatrixElement element) {
        return generate(cols, element.getElements());
    }

    /**
     * Generates a matrix with given rows and columns
     * @param rows The length of the matrix
     * @param cols The size of the matrix
     * @param val The value to fill this matrix with
     * @return The generated matrix, with all elements being {@code val}
     */
    public static Matrix generate(int rows, int cols, double val) {
        double[] elements = new double[rows];
        Arrays.fill(elements, val);
        return generate(cols, elements);
    }

    /**
     * Generates a matrix with identical rows
     * @param cols The columns to generate
     * @param element The elements to insert
     * @return A new Matrix instance with all given rows
     */
    public static Matrix generate(int cols, double... element) {
        // row length is inferred by the supplied elements
        int rows = element.length;
        double[][] val = new double[rows][cols];

        for(double[] arr : val) {
            System.arraycopy(element, 0, arr, 0, arr.length);
        }

        return Matrix.ofArray(val);
    }


}
