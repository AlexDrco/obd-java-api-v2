package com.obd.agnx.response.common;

import com.obd.agnx.response.OBDResponse;

public class WarmUpCyclesSinceDtcClrResponse extends OBDResponse {

    public WarmUpCyclesSinceDtcClrResponse() {
        super("01 30");
    }

    @Override
    public String getDefaultResponse() {
        return "41 30 01"; // Default response with 1 warm-up cycles
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0 warm-up cycles
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse();
    }

    public String getNoCyclesResponse(){
        return "41 30 00";
    }
}