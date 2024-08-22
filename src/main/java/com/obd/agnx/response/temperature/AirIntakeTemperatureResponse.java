package com.obd.agnx.response.temperature;

import com.obd.agnx.response.OBDResponse;

public class AirIntakeTemperatureResponse extends OBDResponse {

    public AirIntakeTemperatureResponse() {
        super("01 0F");
    }

    @Override
    public String getDefaultResponse() {
        return "41 0F 5D"; // Default response with 37°C air intake temperature
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse();
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
        int rawValue = Integer.parseInt(response) + 40;
        String hexValue = String.format("%02X", rawValue);
        return "41 0F" + hexValue;
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}