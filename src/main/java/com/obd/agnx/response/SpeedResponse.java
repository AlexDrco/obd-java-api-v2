package com.obd.agnx.response;

public class SpeedResponse extends OBDResponse {

    public SpeedResponse() {
        super("01 0D");
    }

    @Override
    public String getDefaultResponse() {
        return "41 0D 00"; // Default response with 0 km/h speed
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0 km/h speed
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
        int speed;
        if (response.endsWith("mph")) {
            float mphValue = Float.parseFloat(response.replace("mph", "").trim());
            speed = Math.round(mphValue / 0.621371192F);
        } else if (response.endsWith("km/h")) {
            speed = Integer.parseInt(response.replace("km/h", "").trim());
        } else {
            speed = Integer.parseInt(response.trim());
        }
        return "41 0D" + String.format("%02X", speed);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}