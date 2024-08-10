package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class DtcNumberResponse extends OBDResponse {

    public DtcNumberResponse() {
        super("0101");
    }

    @Override
    public String getDefaultResponse() {
        return "41 01 02 03 00 04 20"; // Example DTC number response with P0300 and P0420
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