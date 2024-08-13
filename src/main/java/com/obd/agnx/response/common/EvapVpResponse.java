package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class EvapVpResponse extends OBDResponse {

    public EvapVpResponse() {
        super("01 32");
    }

    @Override
    public String getDefaultResponse() {
        return "41 32 00 00"; // Default response with 0 pressure
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

    public String getNoErrorResponse(){
        return "41 32 00 00";
    }
}