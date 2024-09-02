package com.obd.agnx.response.pressure;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class EvapVpResponse extends OBDResponse {

    public EvapVpResponse() {
        super("01 32");
    }

    @Override
    public String getDefaultResponse() {
        return "41 32 00 10"; // Default response with -4.19inH2O
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0 pressure
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
        float pressure = Float.parseFloat(response);
        int rawValue = Math.round((pressure + 8.192f) * 4);
        String hexValue = String.format("%04X", rawValue);

        return "41 32" + hexValue;
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        try {
            String numericInitialValue = initialValue.replaceAll("[^-\\d.]", "");
            String response = getSimulatedResponse(numericInitialValue);
            return stringToHex(response);
        } catch (Exception e) {
            return "NODATA";
        }
    }
}