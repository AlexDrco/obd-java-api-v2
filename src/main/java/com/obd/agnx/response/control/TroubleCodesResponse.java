package com.obd.agnx.response.control;

import com.obd.agnx.response.OBDResponse;

public class TroubleCodesResponse extends OBDResponse {

    public TroubleCodesResponse() {
        super("03");
    }

    /*
     * For P codes use 0, for C codes use 4, for B codes use 8, and for U codes use C.
     * Example codes: P0300 -> 43 03 03 00, C0420 -> 43 03 44 20, B0123 -> 43 01 81 23, U0100 -> 43 03 C1 00
     */
    @Override
    public String getDefaultResponse() {
        return "43 02 03 00 04 20"; // Error codes P0300, C0420
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Trouble codes do not vary
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse();
    }

    private static final char[] dtcLetters = {'P', 'C', 'B', 'U'};

    @Override
    public String stringToHex(String response) {
        String[] codes = response.split(",");
        StringBuilder hexResponse = new StringBuilder("43");
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

    public String stringToHex(String response, String protocol) {
        StringBuilder hexResponse = new StringBuilder();
        String[] dtcs = response.split(",");

        for (String dtc : dtcs) {
            if (dtc.length() < 5) continue; // Skip if not a valid DTC format

            char type = dtc.charAt(0);
            int typeHex = 0;
            switch (type) {
                case 'P':
                    typeHex = 0x00;
                    break;
                case 'B':
                    typeHex = 0x01;
                    break;
                case 'C':
                    typeHex = 0x02;
                    break;
                case 'U':
                    typeHex = 0x03;
                    break;
                default:
                    continue; // Skip if not a recognized type
            }

            String codePart = dtc.substring(1);
            try {
                int codeValue = Integer.parseInt(codePart, 16);
                String hexCode = String.format("%04X", codeValue);

                if (protocol.equals("ISO9141-2") || protocol.equals("KWP2000 Fast") || protocol.equals("KWP2000 5Kbps")) {
                    // For ISO9141-2, KWP2000 Fast, and KWP2000 5Kbps, we only need the code without the 43 prefix
                    hexResponse.append("43 03").append(hexCode);
                } else if (protocol.equals("CAN(ISO-15765)")) {
                    // For CAN(ISO-15765), we might need to add the 43 prefix and length
                    hexResponse.append("43 03").append(String.format("%02X", hexCode.length() / 2)).append(hexCode);
                } else {
                    // Default case, just append the code
                    hexResponse.append(hexCode);
                }
            } catch (NumberFormatException e) {
                continue; // Skip if the DTC code part is not in hex format
            }
        }

        // Add protocol-specific headers or formatting if needed
        if (protocol.equals("CAN(ISO-15765)")) {
            // Assuming we need to add a length byte for CAN protocol
            hexResponse.insert(0, String.format("%02X", hexResponse.length() / 2));
        }

        return hexResponse.toString();
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        return "43 00";
    }
}