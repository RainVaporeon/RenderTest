package com.spiritlight.rendertest.math;


import java.util.Arrays;
import java.util.Iterator;


/**
 * An immutable class representing a matrix. The class provides some
 * useful methods to do matrix calculations, and the underlying elements
 * may be iterated via the iterator method.
 */
public class Matrix implements Iterable<MatrixElement> {
    private final MatrixElement[] elements;
    private final int rows;
    private final int columns;

    /**
     * Initializes a Matrix instance with given element.
     *
     * @apiNote The inputted elements will be copied over to this class
     * instead of directly referencing from the supplied array.
     */
    public Matrix(MatrixElement[] elements) {
        if(elements.length == 0 || elements[0].length == 0) {
            throw new IllegalArgumentException("Invalid matrix size of 0");
        }
        this.elements = elements.clone();
        this.rows = elements.length;
        this.columns = elements[0].length;
    }

    public static Matrix ofArray(double[][] array) {
        MatrixElement[] e = new MatrixElement[array.length];
        int i = 0;
        for(double[] val : array) {
            e[i++] = new MatrixElement(val);
        }
        return new Matrix(e);
    }

    public MatrixElement getRow(int index) {
        if(index < 0 || index >= elements.length) throw new IndexOutOfBoundsException(index);
        return elements[index];
    }

    public MatrixElement getColumn(int index) {
        double[] result = new double[rows];
        int i = 0;
        for(MatrixElement element : elements) {
            result[i++] = element.getElement(index);
        }
        return new MatrixElement(result);
    }

    public double get(int row, int column) {
        MatrixElement element = this.getRow(row);
        if(column < 0 || column >= element.length) throw new IndexOutOfBoundsException(column);
        return element.getElement(column);
    }

    public MatrixElement[] getElements() {
        return elements.clone(); // We clone here for some sort of immutability
    }

    public Matrix multiply(Matrix that) {
        if(this.columns != that.rows) throw new IllegalArgumentException("rows mismatch on multiplying matrix");
        double[][] result = new double[rows][that.columns];
        for (int i = 0; i < rows; i++) {
            MatrixElement element = elements[i];
            for (int j = 0; j < that.columns; j++) {
                for (int k = 0; k < that.rows; k++)
                    result[i][j] += element.getElement(k) * that.elements[k].getElement(j);
            }
        }
        return Matrix.ofArray(result);
    }

    public double[][] toArray() {
        double[][] ret = new double[rows][columns];

        for(int i = 0; i < rows; i++) {
            ret[i] = elements[i].getElements();
        }

        return ret;
    }

    @Override
    public Iterator<MatrixElement> iterator() {
        return Arrays.stream(elements).iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        for(MatrixElement element : this) {
            builder.append(Arrays.toString(element.getElements()));
            builder.append("\n");
        }
        builder.replace(builder.length() - 1, builder.length(), "}");
        return builder.toString();
    }

    public static class Builder {
        // need to know rows/cols to actually initialize our array
        private int rows;
        private int columns;

        // tracking which row was modified last
        private int cursor = 0;

        // we'll be using the factory method so this should suffice
        private double[][] element;

        public Builder(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
            this.element = new double[rows][columns];
        }

        /**
         * Sets the row length
         * @param rows The new length to assign to
         * @return the builder itself
         *
         * @apiNote regardless of the calling method, an array resize will
         * always initiate.
         */
        public Builder setRows(int rows) {
            resize(rows, this.columns);
            this.rows = rows;
            return this;
        }

        /**
         * Sets the column length
         * @param columns The new length to assign to
         * @return the builder itself
         *
         * @apiNote regardless of the calling method, an array resize will
         * always initiate.
         */
        public Builder setColumns(int columns) {
            resize(this.rows, columns);
            this.columns = columns;
            return this;
        }

        /**
         * Puts given elements to a row, the row these given numbers
         * will be put on is determined by the cursor position
         * @param elements The elements to assign to a row
         * @return The builder itself, with cursor incremented by one.
         */
        public Builder putRow(double... elements) {
            return putRow(cursor++, elements);
        }

        /**
         * Puts given elements to a given row position
         * @param elements The elements to assign to a row
         * @param row The row to assign the elements to
         * @return The builder itself, with cursor incremented by one.
         */
        public Builder putRow(int row, double... elements) {
            if(checkRange(row)) throw new IndexOutOfBoundsException(rows);
            element[row] = elements;
            return this;
        }

        public int getCursor() {
            return cursor;
        }

        public Builder setCursor(int newPos) {
            this.cursor = newPos;
            return this;
        }

        public Matrix build() {
            return Matrix.ofArray(element);
        }

        /**
         * Check if a number is out of range
         * @param num number to check range
         * @return true if number is either larger than determined rows or columns
         * @throws IllegalArgumentException if num is less or equal to 0
         */
        private boolean checkRange(int num) {
            if(num <= 0) throw new IllegalArgumentException("row and column has to be longer than 0");
            return num > rows || num > columns;
        }

        private void resize(int rows, int cols) {
            double[][] create = new double[rows][cols];

            System.arraycopy(element, 0, create, 0, rows);

            int i = 0;
            for(double[] arr : create) {
                System.arraycopy(element[i], 0, arr, 0, cols);
            }

            element = create;
        }
    }
}
