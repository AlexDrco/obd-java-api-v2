package com.obd.agnx.response.control;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import org.testng.annotations.Test;

public class DistanceSinceCCResponseTest {

    @Test(testName = "Test stringToHex(String); when '42km'; then return '41 31 00 2A'")
    public void testStringToHex_when42km_thenReturn4131002a() {
        assertEquals((new DistanceSinceCCResponse()).stringToHex("42km"), "41 31 00 2A");
    }

    @Test(testName = "Test stringToHex(String); when '42miles'; then return '41 31 00 6B'")
    public void testStringToHex_when42miles_thenReturn4131006b() {
        assertEquals((new DistanceSinceCCResponse()).stringToHex("42miles"), "41 31 00 44");
    }

    @Test(testName = "Test stringToHex(String); when 'invalid'; then throw IllegalArgumentException")
    public void testStringToHex_whenInvalid_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> (new DistanceSinceCCResponse()).stringToHex("invalid"));
    }

    @Test(testName = "Test stringToHex(String); when '100000km'; then throw IllegalArgumentException")
    public void testStringToHex_when100000km_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> (new DistanceSinceCCResponse()).stringToHex("100000km"));
    }

    @Test(testName = "Test stringToHex(String); when '42'; then throw IllegalArgumentException")
    public void testStringToHex_when42_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> (new DistanceSinceCCResponse()).stringToHex("42"));
    }

    @Test(testName = "Test getNoErrorResponse(String); when '42km'; then return '41 31 00 2A'")
    public void testGetNoErrorResponse_when42km_thenReturn4131002a() {
        DistanceSinceCCResponse response = new DistanceSinceCCResponse();

        assertEquals(response.getNoErrorResponse("42km"), "41 31 00 2A");
    }

    @Test(testName = "Test getNoErrorResponse(String); when '42miles'; then return '41 31 00 6B'")
    public void testGetNoErrorResponse_when42miles_thenReturn4131006b() {
        DistanceSinceCCResponse response = new DistanceSinceCCResponse();

        assertEquals(response.getNoErrorResponse("42miles"), "41 31 00 44");
    }

    @Test(testName = "Test getNoErrorResponse(String); when 'invalid'; then return 'NODATA'")
    public void testGetNoErrorResponse_whenInvalid_thenReturnNODATA() {
        DistanceSinceCCResponse response = new DistanceSinceCCResponse();

        assertEquals(response.getNoErrorResponse("invalid"), "NODATA");
    }

    @Test(testName = "Test getNoErrorResponse(String); when '100000km'; then return 'NODATA'")
    public void testGetNoErrorResponse_when100000km_thenReturnNODATA() {
        DistanceSinceCCResponse response = new DistanceSinceCCResponse();

        assertEquals(response.getNoErrorResponse("100000km"), "NODATA");
    }

    @Test(testName = "Test getNoErrorResponse(String); when '42'; then return 'NODATA'")
    public void testGetNoErrorResponse_when42_thenReturnNODATA() {
        DistanceSinceCCResponse response = new DistanceSinceCCResponse();

        assertEquals(response.getNoErrorResponse("42"), "NODATA");
    }
}