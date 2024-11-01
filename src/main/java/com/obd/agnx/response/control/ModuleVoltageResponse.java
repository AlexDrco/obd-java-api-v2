package com.obd.agnx.response.control;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class ModuleVoltageResponse extends OBDResponse {

    public ModuleVoltageResponse() {
        super("01 42");
    }

    @Override
    public String getDefaultResponse() {
        return "41 42 30 E8"; // Example default response
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Voltage does not vary
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        double voltage = Double.parseDouble(initialValue);
        System.out.println("voltage: " + voltage);

        // Generate a random number between 0.1 and 0.2
        double randomNum = 0.1 + (Math.random() * (0.2 - 0.1));

        // Randomly decide if the number should be positive or negative
        if (Math.random() < 0.5) {
            randomNum = -randomNum;
        }

        // Add the random number to the initial value
        double modifiedVoltage = voltage + randomNum;

        return Double.toString(modifiedVoltage);
    }

    @Override
    public String stringToHex(String response) {
        double voltage = Double.parseDouble(response);
        int value = (int) (voltage * 1000);
        int a = (value >> 8) & 0xFF;
        int b = value & 0xFF;
        return String.format("41 42 %02X %02X", a, b);
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        try {
            String numericInitialValue = initialValue.replaceAll("[^\\d.]", "");
            System.out.println("numericInitialValue: " + numericInitialValue);
            String response = getSimulatedResponse(numericInitialValue);
            return stringToHex(response);
        } catch (Exception e) {
            return "NODATA";
        }
    }
}