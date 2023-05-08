package com.spiritlight.rendertest.math;

import java.util.Objects;

// vec3d
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

    public Vertex addX(double x) {
        return add(x, 0, 0);
    }

    public Vertex addY(double y) {
        return add(0, y, 0);
    }

    public Vertex addZ(double z) {
        return add(0, 0, z);
    }

    public Vertex add(Vertex other) {
        return add(other.x, other.y, other.z);
    }

    public Vertex subtract(Vertex other) {
        return add(-other.x, -other.y, -other.z);
    }

    public Vertex add(double x, double y, double z) {
        return new Vertex(this.x + x, this.y + y, this.z + z);
    }

    public Vertex normalize() {
        double len = normalLength();
        return new Vertex(x / len, y / len, z / len);
    }

    /**
     * Gets the cross product with another vertex
     * @param other The other vertex
     * @return The vertex representing the cross product
     */
    public Vertex cross(Vertex other) {
        Matrix result = this.asSkewMatrix().multiply(other.asMatrix());
        return new Vertex(result.get(0, 0),
                result.get(1, 0),
                result.get(2, 0));
    }

    public double dot(Vertex other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public double normalLength() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    /**
     * Produces a skew-symmetric matrix from this vertex.
     * The matrix returned is always 3x3, and is laid out
     * as the following:
     * <pre>
     *     [0, -z, y]
     *     [z, 0, -x]
     *     [y, -x, 0]
     * </pre>
     * @return A 3x3 matrix that's skew-symmetric by the vertex.
     */
    public Matrix asSkewMatrix() {
        return Matrix.builder(3, 3)
                .putRow(0, -z, y)
                .putRow(z, 0, -x)
                .putRow(y, -x, 0).build();
    }

    /**
     * Produces a 3x1 matrix, with each row representing
     * exactly one coordinate, that is:
     * <p>- The row size is always 3</p>
     * <p>- There are always only one column</p>
     *
     * @return A 3x1 matrix with each row representing this
     * vertex's x, y, z respectively
     */
    public Matrix asMatrix() {
        return Matrix.builder(3, 1)
                .putRow(x).putRow(y).putRow(z).build();
    }

    public Vertex scale(double scalar) {
        return new Vertex(this.x * scalar, this.y * scalar, this.z * scalar);
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
