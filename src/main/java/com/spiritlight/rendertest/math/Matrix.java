package com.spiritlight.rendertest.math;


import com.spiritlight.rendertest.math.exceptions.MatrixException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;


/**
 * An immutable class representing a matrix. The class provides some
 * useful methods to do matrix calculations, and the underlying elements
 * may be iterated via the iterator method.
 * <p></p>
 * A row is denoted by the size of the elements, and the
 * column is determined by the elements in each row.
 */
public class Matrix implements Iterable<MatrixElement> {
    protected final MatrixElement[] elements;

    // these numbers are tracked for sake of accessibility
    public final int rows;
    public final int columns;

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

    @Contract("_ -> new")
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

    @Contract(value = "null -> fail; _ -> new", pure = true)
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

    @Contract(value = "_ -> new", pure = true)
    public Matrix scale(double scale) {
        double[][] val = toArray();
        double[][] other = new double[val.length][val[0].length];
        for(int i = 0; i < val.length; i++) {
            for(int j = 0; j < val[0].length; j++) {
                other[i][j] = val[i][j] * scale;
            }
        }
        return Matrix.ofArray(other);
    }

    /**
     * Converts this matrix to an array.
     * <p>
     *     The array is constructed such that when supplying the output
     *     into {@link Matrix#ofArray(double[][])}, the output matrix
     *     is equal to this matrix.
     * </p>
     * @return A 2D array representing this matrix
     */
    public double[][] toArray() {
        double[][] ret = new double[rows][columns];

        for(int i = 0; i < rows; i++) {
            ret[i] = elements[i].getElements();
        }

        return ret;
    }

    /**
     * Creates a new matrix that is transposed by this matrix.
     * @return A new matrix that's the transpose of the current matrix
     */
    public Matrix transpose() {
        double[][] val = new double[columns][rows];
        for(int i = 0; i < columns; i++) {
            // safe to use here as the array provided is copied instead of consumed
            val[i] = this.getColumn(i).elements();
        }
        return Matrix.ofArray(val);
    }

    public boolean isSquare() {
        return rows == columns;
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Builder builder(int rows, int cols) {
        return new Builder(rows, cols);
    }

    @Override
    public Iterator<MatrixElement> iterator() {
        return Arrays.stream(elements.clone()).iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(MatrixElement element : this) {
            builder.append(Arrays.toString(element.elements()));
            builder.append("\n");
        }
        builder.replace(builder.length() - 1, builder.length(), "");
        return builder.toString();
    }

    public static class Builder {
        // need to know rows/cols to actually initialize our array
        private int rows;
        private int columns;

        /**
         * The cursor pointing to the row {@link Builder#putRow(double...)}
         * should modify. By contract, this cursor is never less than 0,
         * and is never larger or equal to the maximum rows specified
         * by the property {@link Builder#rows}, though it can be modified
         * to break the contract via {@link Builder#setCursor(int)}.
         */
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
            checkRange(rows);
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
            checkRange(columns);
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
            return setRow(cursor++, elements);
        }

        /**
         * Puts given elements to a given row position
         * @param elements The elements to assign to a row
         * @param row The row to assign the elements to
         * @return The builder itself, with cursor incremented by one.
         */
        public Builder setRow(int row, double... elements) {
            if(row < 0 || row > rows) throw new IndexOutOfBoundsException(row);
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

        /**
         * Builds the matrix. All uninitialized parts are assigned as 0.
         * @return The matrix built
         */
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
            if(num < 0) throw new IllegalArgumentException("row and column has to be longer than 0");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix that = (Matrix) o;
        return rows == that.rows && columns == that.columns && Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, columns);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }
}
