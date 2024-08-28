package com.obd.agnx.response.control;

import com.obd.agnx.response.OBDResponse;

public class DtcNumberResponse extends OBDResponse {

    public DtcNumberResponse() {
        super("0101");
    }

    @Override
    public String getDefaultResponse() {
        return "41 01 82 00 00 00 00"; // Example DTC number response with P0300 and P0420, MIL ON
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // DTC number does not vary
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // DTC number does not vary
    }

    @Override
    public String stringToHex(String response) {
        boolean milOn = response.contains("ON");
        int dtcCount = Integer.parseInt(response.replaceAll("\\D+", ""));

        // Calculate the third byte
        int thirdByte = (milOn ? 0x80 : 0x00) | dtcCount;

        // Convert the third byte to a 2-digit hexadecimal string
        String hexThirdByte = String.format("%02X", thirdByte);

        // Construct the full response
        String hexResponse = hexThirdByte + " 00 00 00 00";

        return "41 01" + hexResponse;
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        return "43 00 00 00 00 00";
    }
}