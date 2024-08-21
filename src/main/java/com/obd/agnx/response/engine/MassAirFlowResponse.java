package com.obd.agnx.response.engine;

import com.obd.agnx.response.OBDResponse;

public class MassAirFlowResponse extends OBDResponse {

    public MassAirFlowResponse() {
        super("01 10");
    }

    @Override
    public String getDefaultResponse() {
        return "41 10 01 0F"; // Default response with 2.71g/s MAF
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0 g/s MAF
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
        float maf = Float.parseFloat(response);
        int hexValue = Math.round(maf * 100);
        return "41 10" + String.format("%04X", hexValue);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}