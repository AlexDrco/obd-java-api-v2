package com.obd.agnx.response.control;

import com.obd.agnx.response.OBDResponse;

public class OdometerResponse extends OBDResponse {

    public OdometerResponse() {
        super("01 A6");
    }

    @Override
    public String getDefaultResponse() {
        return "41 A6 00 00 00 00"; // Default response with 0 km
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Odometer value does not vary
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
        String[] parts = response.split("(?<=\\d)(?=\\D)");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid distance format. Provided: " + response);
        }

        float distance;
        String unit = parts[1].toLowerCase();

        try {
            distance = Float.parseFloat(parts[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid distance value.");
        }

        // Convert miles to kilometers if necessary
        if (unit.equals("miles") || unit.equals("mi") || unit.equals("m")) {
            distance = distance / 0.621371192F;
        } else if (!unit.equals("km")) {
            throw new IllegalArgumentException("Invalid unit. Use 'km' or 'miles'.");
        }

        // Ensure the distance is within the valid range (0 to 429496729.5 km)
        if (distance < 0 || distance > 429496729.5) {
            throw new IllegalArgumentException("Distance must be between 0 and 429496729.5 kilometers.");
        }

        // Convert the distance to a 4-byte hexadecimal string
        int intDistance = Math.round(distance * 10);
        String hexValue = String.format("%08X", intDistance);

        return "41 A6 " + hexValue.substring(0, 2) + " " + hexValue.substring(2, 4) + " " + hexValue.substring(4, 6) + " " + hexValue.substring(6, 8);
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        try {
            return stringToHex(initialValue.replaceAll(" ", ""));
        } catch (Exception e) {
            return "NODATA";
        }
    }
}