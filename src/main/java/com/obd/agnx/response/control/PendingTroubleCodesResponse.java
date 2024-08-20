package com.obd.agnx.response.control;

import com.obd.agnx.response.OBDResponse;

public class PendingTroubleCodesResponse extends OBDResponse {

    public PendingTroubleCodesResponse() {
        super("07");
    }

    @Override
    public String getDefaultResponse() {
        return "47 01 01 33 42 02"; // Example: P0133, C0202
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Pending trouble codes do not vary
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // Pending trouble codes do not vary
    }

    private static final char[] dtcLetters = {'P', 'C', 'B', 'U'};

    @Override
    public String stringToHex(String response) {
        String[] codes = response.split(",");
        StringBuilder hexResponse = new StringBuilder("47");
        hexResponse.append(String.format("%02X", codes.length));

        for (String code : codes) {
            char letter = code.charAt(0);
            int letterIndex = new String(dtcLetters).indexOf(letter);
            int firstDigit = Character.digit(code.charAt(1), 10);
            int secondDigit = Character.digit(code.charAt(2), 10);
            int thirdDigit = Character.digit(code.charAt(3), 10);
            int fourthDigit = Character.digit(code.charAt(4), 10);

            byte firstByte = (byte) ((letterIndex << 6) | (firstDigit << 4) | secondDigit);
            hexResponse.append(String.format("%02X", firstByte));
            hexResponse.append(String.format("%X%X", thirdDigit, fourthDigit));
        }

        return hexResponse.toString();
    }

    public String getNoErrorResponse() {
        return "00 00 00 00";
    }
}