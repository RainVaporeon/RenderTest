package com.spiritlight.rendertest.test;

import com.spiritlight.rendertest.math.Matrix;

public abstract class MatrixTest implements Runnable {
    protected final Matrix matrix;

    public MatrixTest(Matrix matrix) {
        this.matrix = matrix;
    }

    public static class Multiply extends MatrixTest {
        public Multiply(Matrix matrix) {
            super(matrix);
        }

        @Override
        public void run() {
            matrix.multiply(matrix);
        }
    }

    public static class Scale extends MatrixTest {
        public Scale(Matrix matrix) {
            super(matrix);
        }

        @Override
        public void run() {
            matrix.scale(1.1);
        }
    }

    public static class Multiply100 extends MatrixTest {
        public Multiply100(Matrix matrix) {
            super(matrix);
        }

        @Override
        public void run() {
            for(int i = 0; i < 100; i++) matrix.multiply(matrix);
        }
    }

    public static class Transpose extends MatrixTest {
        public Transpose(Matrix matrix) {
            super(matrix);
        }

        @Override
        public void run() {
            matrix.transpose();
        }
    }
}
