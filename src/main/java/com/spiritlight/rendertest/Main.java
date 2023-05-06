package com.spiritlight.rendertest;

import com.spiritlight.rendertest.math.Matrix;
import com.spiritlight.rendertest.math.MatrixElement;
import com.spiritlight.rendertest.objects.ExampleFrame;

public class Main {

    public static void main(String[] args) {
        ExampleFrame frame = new ExampleFrame();

        frame.setTitle("Example pane");
        // frame.setVisible(true);

        Matrix m = new Matrix(
                new MatrixElement[] {
                        new MatrixElement(1, 2),
                        new MatrixElement(0, 1),
                        new MatrixElement(2, 3)
                }
        );

        Matrix m2 = new Matrix(
                new MatrixElement[]{
                        new MatrixElement(2, 5),
                        new MatrixElement(6, 7)
                }
        );

        System.out.println(m.multiply(m2));
    }
}
