package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class DtcNumberResponse extends OBDResponse {

    public DtcNumberResponse() {
        super("0101");
    }

    @Override
    public String getDefaultResponse() {
        return "41 01 01 00 00 00"; // Example DTC number response
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // DTC number does not vary
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // DTC number does not vary
    }
}