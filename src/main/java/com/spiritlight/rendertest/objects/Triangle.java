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
