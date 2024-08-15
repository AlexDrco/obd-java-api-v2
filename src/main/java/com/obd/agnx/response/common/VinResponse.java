package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class VinResponse extends OBDResponse {

    public VinResponse() {
        super("09 02");
    }

    @Override
    public String getDefaultResponse() {
        return "49023454334457524656315255313133313530"; // VIN WP0ZZZ99ZTS392124
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // VIN does not vary
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // VIN does not vary
    }

    @Override
    public String stringToHex(String response) {
        // Convert the string to a character array
        char[] chars = response.toCharArray();
        StringBuilder hex = new StringBuilder();

        // Loop through each character, convert to int, then to hex, and append to StringBuilder
        for (char c : chars) {
            int value = (int) c;
            // Use String.format to convert the integer to a two-digit hexadecimal string
            hex.append(String.format("%02X", value));
        }

        // Return the hexadecimal string
        return "49 02" + hex.toString();
    }
}
