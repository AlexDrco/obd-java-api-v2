package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class DistanceMILOnResponse extends OBDResponse {

    public DistanceMILOnResponse() {
        super("01 21");
    }

    @Override
    public String getDefaultResponse() {
        return "41 21 00 10"; // Default response with 16 km
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0 km
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
        return "41 21 00 00";
    }
}