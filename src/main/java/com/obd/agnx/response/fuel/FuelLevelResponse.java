package com.obd.agnx.response.fuel;


import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class FuelLevelResponse extends OBDResponse {

    public FuelLevelResponse() {
        super("01 2F");
    }

    @Override
    public String getDefaultResponse() {
        return "41 2F 64"; // Default response with 39.2% fuel level
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 100% fuel level
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        double voltage = Double.parseDouble(initialValue);

        // Generate a random number
        double randomNum = 0.1 + (Math.random() * 0.1);

        // Randomly decide if the number should be positive or negative
        if (Math.random() < 0.5) {
            randomNum = -randomNum;
        }

        // Add the random number to the initial value
        double modifiedValue = voltage + randomNum;

        return Double.toString(modifiedValue);
    }

    @Override
    public String stringToHex(String response) {
        float percentage = Float.parseFloat(response);
        int hexValue = Math.round(percentage * 255 / 100);
        return "41 2F" + String.format("%02X", hexValue);
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        try {
            String numericInitialValue = initialValue.replaceAll("[^\\d.]*(?:\\.(?!.*\\.))?[^\\d.]*", "");
            String response = getSimulatedResponse(numericInitialValue);
            return stringToHex(response);
        } catch (Exception e) {
            return "NODATA";
        }
    }
}