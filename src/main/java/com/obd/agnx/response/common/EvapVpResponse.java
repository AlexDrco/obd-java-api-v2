package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class EvapVpResponse extends OBDResponse {

    public EvapVpResponse() {
        super("01 32");
    }

    @Override
    public String getDefaultResponse() {
        return "41 32 00 10"; // Default response with -4.19inH2O
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0 pressure
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
        float pressure = Float.parseFloat(response);
        int rawValue = Math.round((pressure + 8.192f) * 4);
        String hexValue = String.format("%04X", rawValue);

        return "41 32" + hexValue;
    }

    public String getNoErrorResponse(){
        return "41 32 00 00";
    }
}