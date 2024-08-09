package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class RPMResponse extends OBDResponse {

    private final PerlinNoise perlinNoise = new PerlinNoise();
    private double noiseSeed = 0;

    public RPMResponse() {
        super("010C");
    }

    @Override
    public String getDefaultResponse() {
        return "41 0C 0A 80"; // 700 RPMs
    }

    @Override
    public String getSimulatedDefaultResponse() {
        noiseSeed += 0.1;
        int rpm = 6800 + (int) (perlinNoise.noise(noiseSeed) * 100); // Slight variation using Perlin noise
        return String.format("41 0C %04X", rpm);
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        noiseSeed += 0.1;
        int baseRpm = Integer.parseInt(initialValue, 16);
        int rpm = baseRpm + (int) (perlinNoise.noise(noiseSeed) * 100); // Variation around initial value using Perlin noise
        return String.format("41 0C %04X", rpm);
    }

    public String getSimulatedResponseInRange(String initialValue, int lowerLimit, int upperLimit) {
        noiseSeed += 0.1;
        int baseRpm = Integer.parseInt(initialValue, 16);
        int rpm = perlinNoise.noiseIntInRange(noiseSeed, lowerLimit, upperLimit);
        return String.format("41 0C %04X", rpm);
    }
}