package com.obd.agnx.response.control;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ModuleVoltageResponseTest {

    @Test(testName = "Test getSimulatedResponse(String); when '12.5'; then return a value close to '12.5'")
    public void testGetSimulatedResponse_when12_5_thenReturnCloseTo12_5() {
        ModuleVoltageResponse response = new ModuleVoltageResponse();
        String simulatedResponse = response.getSimulatedResponse("12.5");
        double simulatedVoltage = Double.parseDouble(simulatedResponse);
        assertTrue(simulatedVoltage >= 12.3 && simulatedVoltage <= 12.7);
    }

    @Test(testName = "Test stringToHex(String); when '12.5'; then return '41 42 30 D4'")
    public void testStringToHex_when12_5_thenReturn413230D4() {
        ModuleVoltageResponse response = new ModuleVoltageResponse();
        assertEquals(response.stringToHex("12.5"), "41 42 30 D4");
    }

    @Test(testName = "Test getNoErrorResponse(String); when '12.5'; then return a value close to '41 42 30 D4'")
    public void testGetNoErrorResponse_when12_5_thenReturnCloseTo413230D4() {
        ModuleVoltageResponse response = new ModuleVoltageResponse();
        String noErrorResponse = response.getNoErrorResponse("12.5");
        String[] parts = noErrorResponse.split(" ");
        int value = Integer.parseInt(parts[2] + parts[3], 16);
        double voltage = value / 1000.0;
        assertTrue(voltage >= 12.3 && voltage <= 12.7);
    }

    @Test(testName = "Test getNoErrorResponse(String); when 'invalid'; then return 'NODATA'")
    public void testGetNoErrorResponse_whenInvalid_thenReturnNODATA() {
        ModuleVoltageResponse response = new ModuleVoltageResponse();
        assertEquals(response.getNoErrorResponse("invalid"), "NODATA");
    }
}