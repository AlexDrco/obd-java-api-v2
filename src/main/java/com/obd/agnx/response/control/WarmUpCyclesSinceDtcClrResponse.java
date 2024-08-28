package com.obd.agnx.response.control;

import com.obd.agnx.response.OBDResponse;

public class WarmUpCyclesSinceDtcClrResponse extends OBDResponse {

    public WarmUpCyclesSinceDtcClrResponse() {
        super("01 30");
    }

    @Override
    public String getDefaultResponse() {
        return "41 30 01"; // Default response with 1 warm-up cycles
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse();
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
        int cycleCount = Integer.parseInt(response);
        String hexCycleCount = String.format("%02X", cycleCount);
        return "41 30" + hexCycleCount;
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        return "41 30 00";
    }
}