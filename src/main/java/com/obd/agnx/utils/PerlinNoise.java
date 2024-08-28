package com.obd.agnx.utils;

/**
 * This class generates Perlin noise, a type of gradient noise used in procedural texture generation.
 * The implementation of this will generate a smooth, continuous "random" pattern for use on RPMs, temperatures, etc.
 * It supports single-layer noise as well as multi-layer octave noise for more complex patterns.
 * Think of this as values on a line graph, you provide X coordinates, and it gives you a "smooth" Y value.
 */
public class PerlinNoise {

    private static int[] p;

    static {
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
    public static double noise(double x) {
        int X = (int) Math.floor(x) & 255;
        x -= Math.floor(x);
        double u = fade(x);
        return lerp(u, grad(p[X], x), grad(p[X + 1], x - 1)) * 2;
    }

    /**
     * Adds Perlin noise to a decimal value.
     *
     * @param initialValue The initial decimal value.
     * @param x The x coordinate for noise generation.
     * @return The decimal value with added Perlin noise.
     */
    public static double addNoiseToDecimal(double initialValue, double x) {
        return initialValue + noise(x);
    }

    /**
     * Adds Perlin noise to an integer value.
     *
     * @param initialValue The initial integer value.
     * @param x The x coordinate for noise generation.
     * @return The integer value with added Perlin noise.
     */
    public static int addNoiseToInt(int initialValue, double x) {
        return initialValue + (int) Math.round(noise(x));
    }

    /**
     * Adds Perlin noise to an integer value with larger steps.
     *
     * @param initialValue The initial integer value.
     * @param x The x coordinate for noise generation.
     * @param stepMagnitude The magnitude of the steps.
     * @return The integer value with added Perlin noise in larger steps.
     */
    public static int addNoiseToIntWithSteps(int initialValue, double x, int stepMagnitude) {
        return initialValue + (int) Math.round(noise(x) * stepMagnitude);
    }

    /**
     * Fade function to smooth the noise.
     *
     * @param t The input value.
     * @return The faded value.
     */
    private static double fade(double t) {
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
    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    /**
     * Gradient function to calculate the dot product of a pseudo-random gradient vector and the distance vector.
     *
     * @param hash The hash value.
     * @param x The distance vector.
     * @return The dot product.
     */
    private static double grad(int hash, double x) {
        return (hash & 1) == 0 ? x : -x;
    }
}