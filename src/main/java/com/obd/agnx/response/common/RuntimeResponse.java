package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class RuntimeResponse extends OBDResponse {

    public RuntimeResponse() {
        super("01 1F");
    }

    @Override
    public String getDefaultResponse() {
        return "41 1F FF FF"; // Default response with 18:12:15
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0 seconds
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
        int runtime = Integer.parseInt(response);
        String hexRuntime = Integer.toHexString(runtime).toUpperCase();
        return "41 1F " + hexRuntime;
    }

    public String getNoErrorResponse(){
        return "41 1F 00 00";
    }
}