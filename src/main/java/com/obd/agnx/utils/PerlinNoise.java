package com.obd.agnx.utils;

public class PerlinNoise {

    private final int[] p;

    public PerlinNoise() {
        int[] permutation = new int[256];
        p = new int[512];
        for (int i = 0; i < 256; i++) {
            permutation[i] = i;
        }
        // Shuffle the array
        for (int i = 0; i < 256; i++) {
            int j = (int) (Math.random() * 256);
            int temp = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }
        for (int i = 0; i < 512; i++) {
            p[i] = permutation[i % 256];
        }
    }

    public double noise(double x) {
        int X = (int) Math.floor(x) & 255;
        x -= Math.floor(x);
        double u = fade(x);
        return lerp(u, grad(p[X], x), grad(p[X + 1], x - 1)) * 2;
    }

    public int noiseInt(double x) {
        return (int) Math.round(noise(x));
    }

    public float noiseFloat(double x) {
        return Math.round(noise(x) * 100.0) / 100.0f;
    }

    public int noisePercentage(double x) {
        return (int) Math.round(noise(x) * 50 + 50);
    }

    public int noiseIntInRange(double x, int low, int high) {
        double noiseValue = noise(x);
        return low + (int) Math.round((noiseValue + 1) / 2 * (high - low));
    }

    public float noiseFloatInRange(double x, float low, float high) {
        double noiseValue = noise(x);
        return low + Math.round((noiseValue + 1) / 2 * (high - low) * 100.0) / 100.0f;
    }

    public int noisePercentageInRange(double x, int low, int high) {
        double noiseValue = noise(x);
        return low + (int) Math.round((noiseValue + 1) / 2 * (high - low));
    }

    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private double grad(int hash, double x) {
        return (hash & 1) == 0 ? x : -x;
    }
}