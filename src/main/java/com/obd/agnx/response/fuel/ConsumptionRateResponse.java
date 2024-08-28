package com.obd.agnx.response.fuel;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class ConsumptionRateResponse extends OBDResponse {

    public ConsumptionRateResponse() {
        super("01 5E");
    }

    @Override
    public String getDefaultResponse() {
        return "41 5E 00 AE"; // Default response with 8.7 L/h consumption rate for idle
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0 L/h consumption rate
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
        float rate = Float.parseFloat(response);
        int hexValue = Math.round(rate / 0.05f);
        return "41 5E" + String.format("%04X", hexValue);
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