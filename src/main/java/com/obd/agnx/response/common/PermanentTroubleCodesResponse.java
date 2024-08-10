package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class PermanentTroubleCodesResponse extends OBDResponse {

    public PermanentTroubleCodesResponse() {
        super("0A");
    }

    @Override
    public String getDefaultResponse() {
        return "00 00 00 00"; // No trouble codes
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Permanent trouble codes do not vary
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // Permanent trouble codes do not vary
    }
}