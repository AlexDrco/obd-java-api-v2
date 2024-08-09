package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class TroubleCodesResponse extends OBDResponse {

    public TroubleCodesResponse() {
        super("03");
    }

    @Override
    public String getDefaultResponse() {
        return "43 00 00 00 00 00 00"; // No error codes
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Trouble codes do not vary
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // Trouble codes do not vary
    }
}