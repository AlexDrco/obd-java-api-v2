package com.obd.agnx.response.control;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import org.testng.annotations.Test;

public class DistanceMILOnResponseTest {

    @Test(testName = "Test getNoErrorResponse(String); when '42km'; then return '41 21 00 2A'")
    public void testGetNoErrorResponse_when42km_thenReturn4121002a() {
        DistanceMILOnResponse response = new DistanceMILOnResponse();

        assertEquals(response.getNoErrorResponse("42km"), "41 21 00 2A");
    }

    @Test(testName = "Test getNoErrorResponse(String); when '42miles'; then return '41 21 00 6B'")
    public void testGetNoErrorResponse_when42miles_thenReturn4121006b() {
        DistanceMILOnResponse response = new DistanceMILOnResponse();

        assertEquals(response.getNoErrorResponse("42miles"), "41 21 00 44");
    }

    @Test(testName = "Test getNoErrorResponse(String); when 'invalid'; then return 'NODATA'")
    public void testGetNoErrorResponse_whenInvalid_thenReturnNODATA() {
        DistanceMILOnResponse response = new DistanceMILOnResponse();

        assertEquals(response.getNoErrorResponse("invalid"), "NODATA");
    }

    @Test(testName = "Test getNoErrorResponse(String); when '100000km'; then return 'NODATA'")
    public void testGetNoErrorResponse_when100000km_thenReturnNODATA() {
        DistanceMILOnResponse response = new DistanceMILOnResponse();

        assertEquals(response.getNoErrorResponse("100000km"), "NODATA");
    }

    @Test(testName = "Test getNoErrorResponse(String); when '42'; then return 'NODATA'")
    public void testGetNoErrorResponse_when42_thenReturnNODATA() {
        DistanceMILOnResponse response = new DistanceMILOnResponse();

        assertEquals(response.getNoErrorResponse("42"), "NODATA");
    }

    @Test(testName = "Test stringToHex(String); when '42km'; then return '41 21 00 2A'")
    public void testStringToHex_when42km_thenReturn4121002a() {
        assertEquals((new DistanceMILOnResponse()).stringToHex("42km"), "41 21 00 2A");
    }

    @Test(testName = "Test stringToHex(String); when '42m'; then return '41 21 00 44'")
    public void testStringToHex_when42m_thenReturn41210044() {
        assertEquals((new DistanceMILOnResponse()).stringToHex("42m"), "41 21 00 44");
    }

    @Test(testName = "Test stringToHex(String); when '42mi'; then return '41 21 00 44'")
    public void testStringToHex_when42mi_thenReturn41210044() {
        assertEquals((new DistanceMILOnResponse()).stringToHex("42mi"), "41 21 00 44");
    }

    @Test(testName = "Test stringToHex(String); when '42miles'; then return '41 21 00 44'")
    public void testStringToHex_when42miles_thenReturn41210044() {
        assertEquals((new DistanceMILOnResponse()).stringToHex("42miles"), "41 21 00 44");
    }

    @Test(testName = "Test stringToHex(String); when '0123456789ABCDEF'; then throw IllegalArgumentException")
    public void testStringToHex_when0123456789abcdef_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> (new DistanceMILOnResponse()).stringToHex("0123456789ABCDEF"));
    }

    @Test(testName = "Test stringToHex(String); when '(?<=\\d)(?=\\D)0123456789ABCDEF'; then throw IllegalArgumentException")
    public void testStringToHex_whenDD0123456789abcdef_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> (new DistanceMILOnResponse()).stringToHex("(?<=\\d)(?=\\D)0123456789ABCDEF"));
    }

    @Test(testName = "Test stringToHex(String); when '(?<=\\d)(?=\\D)'; then throw IllegalArgumentException")
    public void testStringToHex_whenDD_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> (new DistanceMILOnResponse()).stringToHex("(?<=\\d)(?=\\D)"));
    }
}