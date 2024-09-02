package com.obd.agnx.response.pressure;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class FuelPressureResponse extends OBDResponse {

    public FuelPressureResponse() {
        super("01 0A");
    }

    @Override
    public String getDefaultResponse() {
        return "41 0A 57"; // Default response with 261 kPa fuel pressure
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 30 kPa fuel pressure
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        int initialDecimalValue = Integer.parseInt(initialValue);

        // Generate a random number between 3 and 6
        int randomNum = 1 + (int)(Math.random() * 1);

        // Add the random number to the initial value
        int modifiedValue = initialDecimalValue + randomNum;

        return Integer.toString(modifiedValue);
    }

    @Override
    public String stringToHex(String response) {
        float pressure;
        if (response.endsWith("psi")) {
            float psiValue = Float.parseFloat(response.replace("psi", "").trim());
            pressure = psiValue / 0.145037738F;
        } else if (response.endsWith("kPa")) {
            pressure = Float.parseFloat(response.replace("kPa", "").trim());
        } else {
            pressure = Float.parseFloat(response.trim());
        }
        int pressureInt = Math.round(pressure / 3); // Fuel pressure is multiplied by 3 in the command
        return "41 0A" + String.format("%02X", pressureInt);
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