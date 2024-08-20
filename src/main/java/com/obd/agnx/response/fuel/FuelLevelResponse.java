package com.obd.agnx.response.fuel;


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

    @Override
    public String stringToHex(String response) {
        float percentage = Float.parseFloat(response);
        int hexValue = Math.round(percentage * 255 / 100);
        return "41 2F" + String.format("%02X", hexValue);
    }

    public String getFullFuelLevelResponse(){
        return "41 2F FF"; // Default response with 100% fuel level
    }
}