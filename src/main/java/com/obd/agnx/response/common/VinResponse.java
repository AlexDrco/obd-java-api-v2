package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class VinResponse extends OBDResponse {

    public VinResponse() {
        super("0902");
    }

    @Override
    public String getDefaultResponse() {
        return "49 02 01 57 50 30 5A 5A 5A 5A 5A 5A 5A 5A 5A 5A 5A 5A"; // VIN WP0ZZZZZZZZZZZZZ
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // VIN does not vary
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // VIN does not vary
    }
}
