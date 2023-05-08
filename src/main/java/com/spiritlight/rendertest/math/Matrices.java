package com.spiritlight.rendertest.math;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class Matrices {

    private Matrices() {}

    // It's probably useful keeping all previously created identity matrices
    // just in case multiple calls are needed and wastes extra computing power
    // to calculate another one. Since our Matrix implementation is immutable,
    // it's safe to assume that the generated matrices are going to be stable.
    private static final Map<Integer, Matrix> identity = new HashMap<>();

    /**
     * Creates an identity matrix for given size.
     * An identity matrix is a matrix which has the diagonal elements
     * as all ones and zero otherwise.
     *
     * @param size The size of the matrix
     * @return The identity matrix created.
     */
    public static Matrix identityMatrix(int size) {
        if(identity.containsKey(size)) return identity.get(size);
        double[][] val = new double[size][size];
        for(int i = 0; i < size; i++) {
            val[i][i] = 1;
        }
        Matrix m = Matrix.ofArray(val);
        identity.putIfAbsent(size, m);
        return m;
    }

    /**
     * Generates a matrix with identical rows
     * @param cols The columns to generate
     * @param element The elements to insert
     * @return A new Matrix instance with all given rows
     */
    public static Matrix generate(int cols, MatrixElement element) {
        // safe to refer tp raw elements as we are copying over anyways
        return generate(cols, element.elements());
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
