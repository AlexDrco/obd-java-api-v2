package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class EngineCoolantTemperatureResponse extends OBDResponse {

    private final PerlinNoise perlinNoise = new PerlinNoise();
    private double noiseSeed = 0;

    public EngineCoolantTemperatureResponse() {
        super("0105");
    }

    @Override
    public String getDefaultResponse() {
        return "41 05 5A"; // 90°C
    }

    @Override
    public String getSimulatedDefaultResponse() {
        noiseSeed += 0.1;
        int temp = 85 + (int) (perlinNoise.noise(noiseSeed) * 10); // Slight variation using Perlin noise
        return String.format("41 05 %02X", temp);
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        noiseSeed += 0.1;
        int baseTemp = Integer.parseInt(initialValue, 16);
        int temp = baseTemp + (int) (perlinNoise.noise(noiseSeed) * 10); // Variation around initial value using Perlin noise
        return String.format("41 05 %02X", temp);
    }
}