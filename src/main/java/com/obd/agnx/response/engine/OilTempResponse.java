package com.obd.agnx.response.engine;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class OilTempResponse extends OBDResponse {

    public OilTempResponse() {
        super("01 5C");
    }

    @Override
    public String getDefaultResponse() {
        return "41 5C 6F"; // Default response with 71°C oil temperature
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0°C oil temperature
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        int initialDecimalValue = Integer.parseInt(initialValue);
        int noisyDecimal = PerlinNoise.addNoiseToInt(initialDecimalValue, 2);
        return Integer.toString(noisyDecimal);
    }

    @Override
    public String stringToHex(String response) {
        float temperature = Float.parseFloat(response);
        int hexValue = Math.round(temperature + 40); // OBD-II standard for temperature
        return "41 5C" + String.format("%02X", hexValue);
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        try {
            String numericInitialValue = initialValue.replaceAll("\\D", "");
            String response = getSimulatedResponse(numericInitialValue);
            return stringToHex(response);
        } catch (Exception e) {
            return "NODATA";
        }
    }
}