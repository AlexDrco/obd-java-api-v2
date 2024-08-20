package com.obd.agnx.response.control;

import com.obd.agnx.response.OBDResponse;

public class ModuleVoltageResponse extends OBDResponse {

    public ModuleVoltageResponse() {
        super("01 42");
    }

    @Override
    public String getDefaultResponse() {
        return "41 42 30 E8"; // Example default response
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Voltage does not vary
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // Voltage does not vary
    }

    @Override
    public String stringToHex(String response) {
        double voltage = Double.parseDouble(response);
        int value = (int) (voltage * 1000);
        int a = (value >> 8) & 0xFF;
        int b = value & 0xFF;
        return String.format("41 42 %02X %02X", a, b);
    }
}