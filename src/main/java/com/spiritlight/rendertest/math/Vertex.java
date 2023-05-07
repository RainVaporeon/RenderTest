package com.spiritlight.rendertest.math;

import java.util.Objects;

public class Vertex extends Point {
    protected final double x, y, z;

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double distanceTo(Vertex other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2) + Math.pow(this.z - other.z, 2));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vertex transform(Matrix matrix) {
        if(matrix.columns != 3 || matrix.rows != 3) throw new IllegalArgumentException("matrix has to be 3x3");

        Matrix vertex = Matrix.builder(3, 1)
                .putRow(x).putRow(y).putRow(z).build();

        Matrix result = matrix.multiply(vertex);

        return new Vertex(result.get(0, 0),
                result.get(1, 0),
                result.get(2, 0));
    }

    public static Vertex of(double x, double y, double z) {
        return new Vertex(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Double.compare(vertex.x, x) == 0 && Double.compare(vertex.y, y) == 0 && Double.compare(vertex.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
