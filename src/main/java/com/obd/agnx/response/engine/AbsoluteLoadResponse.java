package com.obd.agnx.response.engine;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class AbsoluteLoadResponse extends OBDResponse {

    public AbsoluteLoadResponse() {
        super("01 43");
    }

    @Override
    public String getDefaultResponse() {
        return "41 43 00 0B"; // Default response with 5.9% load
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
        double initialDecimalValue = Double.parseDouble(initialValue);
        double noisyDecimal = PerlinNoise.addNoiseToDecimal(initialDecimalValue, 0.1);
        return Double.toString(noisyDecimal);
    }

    @Override
    public String stringToHex(String response) {
        float percentage = Float.parseFloat(response);
        int hexValue = Math.round(percentage * 255 / 100);
        return "41 43" + String.format("%04X", hexValue);
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        try {
            String numericInitialValue = initialValue.replaceAll("[^\\d.]*(?:\\.(?!.*\\.))?[^\\d.]*", "");
            String response = getSimulatedResponse(numericInitialValue);
            return stringToHex(response);
        } catch (Exception e) {
            return "NODATA";
        }
    }
}