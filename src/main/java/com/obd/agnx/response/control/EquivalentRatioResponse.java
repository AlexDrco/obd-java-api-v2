package com.obd.agnx.response.control;

import com.obd.agnx.response.OBDResponse;

public class EquivalentRatioResponse extends OBDResponse {

    public EquivalentRatioResponse() {
        super("01 44");
    }

    @Override
    public String getDefaultResponse() {
        return "41 44 80 00"; // Default response with 1.9 equivalence ratio
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 1.0 equivalence ratio
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse();
    }

    @Override
    public String stringToHex(String response) {
        double ratio;
        try {
            ratio = Double.parseDouble(response);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid equivalence ratio value.");
        }

        // Ensure the ratio is within the valid range (0 to 2.55)
        if (ratio < 0 || ratio > 2.55) {
            throw new IllegalArgumentException("Equivalence ratio must be between 0 and 2.55.");
        }

        // Convert the ratio to a 2-byte hexadecimal string
        int intValue = (int) (ratio * 32768);
        String hexValue = String.format("%04X", intValue);

        return "41 44 " + hexValue.substring(0, 2) + " " + hexValue.substring(2, 4);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse(); // No error response
    }
}