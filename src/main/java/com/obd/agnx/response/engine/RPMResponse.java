package com.obd.agnx.response.engine;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class RPMResponse extends OBDResponse {

    private double noiseSeed = 0;

    public RPMResponse() {
        super("010C");
    }

    @Override
    public String getDefaultResponse() {
        return "41 0C 0A F0"; // 700 RPMs
    }

    @Override
    public String getSimulatedDefaultResponse() {
        noiseSeed += 0.1;
        int rpm = 6800 + (int) (PerlinNoise.noise(noiseSeed) * 100); // Slight variation using Perlin noise
        return String.format("41 0C %04X", rpm);
    }

    @Override
    public String getSimulatedResponse() {
        String substring = getDefaultResponse().replace(" ", "").substring(4, 8);
        noiseSeed += 0.1;
        int baseRpm = Integer.parseInt(substring, 16);

        // Generate a random number between 3 and 6
        int randomNum = 3 + (int)(Math.random() * ((6 - 3) + 1));

        // Randomly decide if the number should be positive or negative
        if (Math.random() < 0.5) {
            randomNum = -randomNum;
        }

        int rpm = baseRpm + randomNum;
        return String.format("41 0C %04X", rpm);
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        int initialDecimalValue = Integer.parseInt(initialValue);
        int noisyDecimal = PerlinNoise.addNoiseToInt(initialDecimalValue, 12);
        return Integer.toString(noisyDecimal);
    }

    @Override
    public String stringToHex(String response) {
        Integer rpm = Integer.parseInt(response) * 4;
        return "41 0C" + String.format("%04X", rpm);
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        try {
            String numericInitialValue = initialValue.replaceAll("\\D", "");
            String response = getSimulatedResponse(numericInitialValue);
            return stringToHex(response);
        } catch (Exception e) {
            return "NODATA";
        }
    }
}