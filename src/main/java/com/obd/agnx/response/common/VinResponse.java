package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class VinResponse extends OBDResponse {

    public VinResponse() {
        super("0902");
    }

    @Override
    public String getDefaultResponse() {
        return "4902010000005749020250305A5A4902035A39395A4902045453333949020532313234"; // VIN WP0ZZZ99ZTS392124
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // VIN does not vary
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // VIN does not vary
    }
}
