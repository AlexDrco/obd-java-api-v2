package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class TroubleCodesResponse extends OBDResponse {

    public TroubleCodesResponse() {
        super("03");
    }

    @Override
    public String getDefaultResponse() {
        return "43 01 03 00 04 20"; // No error codes
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

    public String getNoErrorResponse(){
        return "43 00 00 00 00 00";
    }
}