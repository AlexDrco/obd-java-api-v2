package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class PermanentTroubleCodesResponse extends OBDResponse {

    public PermanentTroubleCodesResponse() {
        super("0A");
    }

    @Override
    public String getDefaultResponse() {
        return "43 01 01 33 42 02"; // Example: P0133, C0202
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Permanent trouble codes do not vary
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // Permanent trouble codes do not vary
    }

    public String getNoErrorResponse(){
        return "00 00 00 00";
    }
}