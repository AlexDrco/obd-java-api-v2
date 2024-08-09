package com.obd.agnx.utils;

/**
 * This class generates Perlin noise, a type of gradient noise used in procedural texture generation.
 * The implementation of this will generate a smooth, continuous "random"" pattern for use on RPMs, temperatures, etc.
 * It supports single-layer noise as well as multi-layer octave noise for more complex patterns.
 * Think of this as values on a line graph, you provide X coordinates, and it gives you a "smooth" Y value.
 */
public class PerlinNoise {

    private final int[] p;

    /**
     * Initializes the permutation array and shuffles it to create a pseudo-random sequence.
     */
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

    /**
     * Generates Perlin noise for a given x coordinate.
     *
     * @param x The x coordinate.
     * @return The noise value at the given coordinate.
     */
    public double noise(double x) {
        int X = (int) Math.floor(x) & 255;
        x -= Math.floor(x);
        double u = fade(x);
        return lerp(u, grad(p[X], x), grad(p[X + 1], x - 1)) * 2;
    }

    /**
     * Generates octave Perlin noise for a given x coordinate.
     * Combines multiple layers of noise at different frequencies and amplitudes.
     * Gives a smoother, more natural-looking pattern.
     *
     * @param x The x coordinate.
     * @param octaves The number of noise layers.
     * @param persistence The amplitude reduction factor for each subsequent octave.
     * @param frequency The initial frequency of the noise.
     * @return The combined noise value at the given coordinate.
     */
    public double octaveNoise(double x, int octaves, double persistence, double frequency) {
        double total = 0;
        double maxValue = 0;
        double amplitude = 1;
        double freq = frequency;

        for (int i = 0; i < octaves; i++) {
            total += noise(x * freq) * amplitude;
            maxValue += amplitude;
            amplitude *= persistence;
            freq *= 2;
        }

        return total / maxValue;
    }

    /**
     * Generates an integer noise value for a given x coordinate.
     *
     * @param x The x coordinate.
     * @return The integer noise value at the given coordinate.
     */
    public int noiseInt(double x) {
        return (int) Math.round(noise(x));
    }

    /**
     * Generates a float noise value for a given x coordinate, rounded to two decimal places.
     *
     * @param x The x coordinate.
     * @return The float noise value at the given coordinate.
     */
    public float noiseFloat(double x) {
        return Math.round(noise(x) * 100.0) / 100.0f;
    }

    /**
     * Generates a noise value as a percentage for a given x coordinate.
     *
     * @param x The x coordinate.
     * @return The noise value as a percentage (0-100) at the given coordinate.
     */
    public int noisePercentage(double x) {
        return (int) Math.round(noise(x) * 50 + 50);
    }

    /**
     * Generates an integer noise value within a specified range for a given x coordinate.
     *
     * @param x The x coordinate.
     * @param low The lower bound of the range.
     * @param high The upper bound of the range.
     * @return The integer noise value within the specified range at the given coordinate.
     */
    public int noiseIntInRange(double x, int low, int high) {
        double noiseValue = noise(x);
        return low + (int) Math.round((noiseValue + 1) / 2 * (high - low));
    }

    /**
     * Generates a float noise value within a specified range for a given x coordinate, rounded to two decimal places.
     *
     * @param x The x coordinate.
     * @param low The lower bound of the range.
     * @param high The upper bound of the range.
     * @return The float noise value within the specified range at the given coordinate.
     */
    public float noiseFloatInRange(double x, float low, float high) {
        double noiseValue = noise(x);
        return low + Math.round((noiseValue + 1) / 2 * (high - low) * 100.0) / 100.0f;
    }

    /**
     * Generates a noise value as a percentage within a specified range for a given x coordinate.
     *
     * @param x The x coordinate.
     * @param low The lower bound of the range.
     * @param high The upper bound of the range.
     * @return The noise value as a percentage within the specified range at the given coordinate.
     */
    public int noisePercentageInRange(double x, int low, int high) {
        double noiseValue = noise(x);
        return low + (int) Math.round((noiseValue + 1) / 2 * (high - low));
    }

    /**
     * Fade function to smooth the noise.
     *
     * @param t The input value.
     * @return The faded value.
     */
    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    /**
     * Linear interpolation function.
     *
     * @param t The interpolation factor.
     * @param a The start value.
     * @param b The end value.
     * @return The interpolated value.
     */
    private double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    /**
     * Gradient function to calculate the dot product of a pseudo-random gradient vector and the distance vector.
     *
     * @param hash The hash value.
     * @param x The distance vector.
     * @return The dot product.
     */
    private double grad(int hash, double x) {
        return (hash & 1) == 0 ? x : -x;
    }
}