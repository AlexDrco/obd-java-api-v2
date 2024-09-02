package com.obd.agnx.response.control;

import com.obd.agnx.response.OBDResponse;

public class RuntimeMilResponse extends OBDResponse {

    public RuntimeMilResponse() {
        super("01 4D");
    }

    @Override
    public String getDefaultResponse() {
        return "41 4D FF FF"; // Default response with maximum value
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with maximum value
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
        int runtime = Integer.parseInt(response);
        String hexRuntime = Integer.toHexString(runtime).toUpperCase();
        return "41 4D " + hexRuntime;
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        try {
            int seconds = convertToSeconds(initialValue);
            return stringToHex(Integer.toString(seconds));
        } catch (Exception e) {
            return "NODATA";
        }
    }

    public int convertToSeconds(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);

        return hours * 3600 + minutes * 60 + seconds;
    }
}