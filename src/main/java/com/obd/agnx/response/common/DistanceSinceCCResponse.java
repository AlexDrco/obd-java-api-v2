package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class DistanceSinceCCResponse extends OBDResponse {

    public DistanceSinceCCResponse() {
        super("0131");
    }

    @Override
    public String getDefaultResponse() {
        return "41 31 00 00"; // 0 km
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Distance does not vary
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // Distance does not vary
    }
}