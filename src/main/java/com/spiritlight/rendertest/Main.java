package com.spiritlight.rendertest;

import com.spiritlight.rendertest.math.Matrices;
import com.spiritlight.rendertest.math.Vertex;
import com.spiritlight.rendertest.objects.ExampleFrame;
import com.spiritlight.rendertest.objects.Triangle;
import com.spiritlight.rendertest.test.MatrixTest;
import com.spiritlight.rendertest.utils.Timer;

import java.awt.*;
import java.util.List;

public class Main {

    public static final List<Triangle> list = List.of(
            new Triangle(Vertex.of(100, 100, 100),
                    Vertex.of(-100, -100, 100),
                    Vertex.of(-100, 100, -100),
                    Color.WHITE),
            new Triangle(Vertex.of(100, 100, 100),
                    Vertex.of(-100, -100, 100),
                    Vertex.of(100, -100, -100),
                    Color.RED),
            new Triangle(Vertex.of(-100, 100, -100),
                    Vertex.of(100, -100, -100),
                    Vertex.of(100, 100, 100),
                    Color.GREEN),
            new Triangle(Vertex.of(-100, 100, -100),
                    Vertex.of(100, -100, -100),
                    Vertex.of(-100, -100, 100),
                    Color.BLUE)
    );

    public static void main(String[] args) {
        ExampleFrame frame = new ExampleFrame();

        frame.setVisible(true);

    }

    static void printResult(String name, long val) {
        System.out.printf("The result of running test %s is %dms\n", name, val);
    }
}

