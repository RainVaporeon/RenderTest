package com.spiritlight.rendertest.math;

public class Vec3d extends Vertex {

    public Vec3d(Vertex v) {
        this(v.x, v.y, v.z);
    }

    public Vec3d(double x, double y, double z) {
        super(x, y, z);
    }

    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public Vec3d scale(double modifier) {
        return new Vec3d(x * modifier, y * modifier, z * modifier);
    }

    public Vec3d add(Vec3d other) {
        return new Vec3d(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vec3d subtract(Vec3d other) {
        return new Vec3d(this.x - other.x, this.y - other.y, this.z - other.z);
    }
}
