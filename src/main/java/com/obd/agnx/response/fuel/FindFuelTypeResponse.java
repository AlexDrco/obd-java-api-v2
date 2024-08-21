package com.obd.agnx.response.fuel;

import com.obd.agnx.response.OBDResponse;

public class FindFuelTypeResponse extends OBDResponse {

    public FindFuelTypeResponse() {
        super("01 51");
    }

    @Override
    public String getDefaultResponse() {
        return "41 51 01"; // Default response with Gasoline fuel type
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with Gasoline fuel type
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
        int fuelType = Integer.parseInt(response);
        return "41 51" + String.format("%02X", fuelType);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}