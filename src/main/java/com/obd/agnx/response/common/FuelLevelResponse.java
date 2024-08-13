package com.obd.agnx.response.common;


import com.obd.agnx.response.OBDResponse;

public class FuelLevelResponse extends OBDResponse {

    public FuelLevelResponse() {
        super("01 2F");
    }

    @Override
    public String getDefaultResponse() {
        return "41 2F 64"; // Default response with 39.2% fuel level
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 100% fuel level
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse();
    }

    public String getFullFuelLevelResponse(){
        return "41 2F FF"; // Default response with 100% fuel level
    }
}