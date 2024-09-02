package com.obd.agnx.response.temperature;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class EngineCoolantTemperatureResponse extends OBDResponse {

    private final PerlinNoise perlinNoise = new PerlinNoise();
    private double noiseSeed = 0;

    public EngineCoolantTemperatureResponse() {
        super("01 05");
    }

    @Override
    public String getDefaultResponse() {
        return "41 05 5A"; // 50°C
    }

    @Override
    public String getSimulatedDefaultResponse() {
        noiseSeed += 0.1;
        int temp = 85 + (int) (perlinNoise.noise(noiseSeed) * 10); // Slight variation using Perlin noise
        return String.format("41 05 %02X", temp);
    }

    @Override
    public String getSimulatedResponse() {
        noiseSeed += 0.1;
        int temp = 85 + (int) (perlinNoise.noise(noiseSeed) * 10); // Slight variation using Perlin noise
        return String.format("41 05 %02X", temp);
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        int initialDecimalValue = Integer.parseInt(initialValue);

        // Generate a random number between 3 and 6
        int randomNum = 1 + (int)(Math.random() * ((2 - 1) + 1));

        // Add the random number to the initial value
        int modifiedValue = initialDecimalValue + randomNum;

        return Integer.toString(modifiedValue);
    }

    @Override
    public String stringToHex(String response) {
        int rawValue = Integer.parseInt(response) + 40;
        String hexValue = String.format("%02X", rawValue);

        return "41 05" + hexValue;
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