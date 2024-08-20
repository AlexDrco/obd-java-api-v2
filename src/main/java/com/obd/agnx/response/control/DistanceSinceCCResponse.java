package com.obd.agnx.response.control;

import com.obd.agnx.response.OBDResponse;

public class DistanceSinceCCResponse extends OBDResponse {

    public DistanceSinceCCResponse() {
        super("01 31");
    }

    @Override
    public String getDefaultResponse() {
        return "41 31 04 D2"; // 1234 km
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Distance does not vary
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

        int distance;
        String unit = parts[1].toLowerCase();

        try {
            distance = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid distance value.");
        }

        // Convert miles to kilometers if necessary
        if (unit.equals("miles") || unit.equals("mi") || unit.equals("m")) {
            distance = Math.round(distance / 0.621371192F);
        } else if (!unit.equals("km")) {
            throw new IllegalArgumentException("Invalid unit. Use 'km' or 'miles'.");
        }

        // Ensure the distance is within the valid range (0 to 65535 km)
        if (distance < 0 || distance > 65535) {
            throw new IllegalArgumentException("Distance must be between 0 and 65535 kilometers.");
        }

        // Convert the distance to a 2-byte hexadecimal string
        String hexValue = String.format("%04X", distance);

        return "41 31 " + hexValue.substring(0, 2) + " " + hexValue.substring(2, 4);
    }

    public String getNoKmResponse() {
        return "41 31 00 00"; // 0 km
    }
}