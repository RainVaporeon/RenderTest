package com.spiritlight.rendertest.objects;

import com.spiritlight.rendertest.math.Vertex;

import java.awt.*;

public class Triangle {
    private Vertex point1;
    private Vertex point2;
    private Vertex point3;
    private Color color;

    public Triangle(Vertex point1, Vertex point2, Vertex point3, Color color) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    private static final float LINEAR_CONSTANT = 2.4F;
    public Color getShade(double shade) {
        double redL = Math.pow(color.getRed(), LINEAR_CONSTANT) * shade;
        double greenL = Math.pow(color.getGreen(), LINEAR_CONSTANT) * shade;
        double blueL = Math.pow(color.getBlue(), LINEAR_CONSTANT) * shade;

        int red = (int) Math.pow(redL, 1 / LINEAR_CONSTANT);
        int green = (int) Math.pow(greenL, 1 / LINEAR_CONSTANT);
        int blue = (int) Math.pow(blueL, 1 / LINEAR_CONSTANT);
        return new Color(red, green, blue);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vertex getPoint1() {
        return point1;
    }

    public void setPoint1(Vertex point1) {
        this.point1 = point1;
    }

    public Vertex getPoint2() {
        return point2;
    }

    public void setPoint2(Vertex point2) {
        this.point2 = point2;
    }

    public Vertex getPoint3() {
        return point3;
    }

    public void setPoint3(Vertex point3) {
        this.point3 = point3;
    }
}
