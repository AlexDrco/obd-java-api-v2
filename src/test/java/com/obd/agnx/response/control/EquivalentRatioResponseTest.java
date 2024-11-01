package com.obd.agnx.response.control;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import org.testng.annotations.Test;

public class EquivalentRatioResponseTest {

    @Test(testName = "Test stringToHex(String); when '1.0'; then return '41 44 80 00'")
    public void testStringToHex_when1_0_thenReturn41448000() {
        EquivalentRatioResponse response = new EquivalentRatioResponse();
        assertEquals(response.stringToHex("1.0"), "41 44 80 00");
    }

    @Test(testName = "Test stringToHex(String); when '0.5'; then return '41 44 40 00'")
    public void testStringToHex_when0_5_thenReturn41444000() {
        EquivalentRatioResponse response = new EquivalentRatioResponse();
        assertEquals(response.stringToHex("0.5"), "41 44 40 00");
    }

    @Test(testName = "Test stringToHex(String); when '2.55'; then return '41 44 14 66'")
    public void testStringToHex_when2_55_thenReturn4144FFFF() {
        EquivalentRatioResponse response = new EquivalentRatioResponse();
        assertEquals(response.stringToHex("2.55"), "41 44 14 66");
    }

    @Test(testName = "Test stringToHex(String); when 'invalid'; then throw IllegalArgumentException")
    public void testStringToHex_whenInvalid_thenThrowIllegalArgumentException() {
        EquivalentRatioResponse response = new EquivalentRatioResponse();
        assertThrows(IllegalArgumentException.class, () -> response.stringToHex("invalid"));
    }

    @Test(testName = "Test stringToHex(String); when '3.0'; then throw IllegalArgumentException")
    public void testStringToHex_when3_0_thenThrowIllegalArgumentException() {
        EquivalentRatioResponse response = new EquivalentRatioResponse();
        assertThrows(IllegalArgumentException.class, () -> response.stringToHex("3.0"));
    }

    @Test(testName = "Test getNoErrorResponse(String); when '1.0'; then return '41 44 80 00'")
    public void testGetNoErrorResponse_when1_0_thenReturn41448000() {
        EquivalentRatioResponse response = new EquivalentRatioResponse();
        assertEquals(response.getNoErrorResponse("1.0"), "41 44 80 00");
    }

    @Test(testName = "Test getNoErrorResponse(String); when '0.5'; then return '41 44 40 00'")
    public void testGetNoErrorResponse_when0_5_thenReturn41444000() {
        EquivalentRatioResponse response = new EquivalentRatioResponse();
        assertEquals(response.getNoErrorResponse("0.5"), "41 44 40 00");
    }

    @Test(testName = "Test getNoErrorResponse(String); when '2.55'; then return '41 44 14 66'")
    public void testGetNoErrorResponse_when2_55_thenReturn4144FFFF() {
        EquivalentRatioResponse response = new EquivalentRatioResponse();
        assertEquals(response.getNoErrorResponse("2.55"), "41 44 14 66");
    }

    @Test(testName = "Test getNoErrorResponse(String); when 'invalid'; then return 'NODATA'")
    public void testGetNoErrorResponse_whenInvalid_thenReturnNODATA() {
        EquivalentRatioResponse response = new EquivalentRatioResponse();
        assertEquals(response.getNoErrorResponse("invalid"), "NODATA");
    }

    @Test(testName = "Test getNoErrorResponse(String); when '3.0'; then return 'NODATA'")
    public void testGetNoErrorResponse_when3_0_thenReturnNODATA() {
        EquivalentRatioResponse response = new EquivalentRatioResponse();
        assertEquals(response.getNoErrorResponse("3.0"), "NODATA");
    }
}