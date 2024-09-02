package com.obd.agnx.response.fuel;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class AirFuelRatioResponse extends OBDResponse {

    public AirFuelRatioResponse() {
        super("01 44");
    }

    @Override
    public String getDefaultResponse() {
        return "41 44 80 00"; // Default response with 14.7:1 AFR which is the ideal AFR
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0:1 AFR
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
        float afr = Float.parseFloat(response);
        int hexValue = Math.round((afr / 14.7f) * 32768);
        return "41 44" + String.format("%04X", hexValue);
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