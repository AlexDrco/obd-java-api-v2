package com.obd.agnx.response.engine;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class MassAirFlowResponse extends OBDResponse {

    public MassAirFlowResponse() {
        super("01 10");
    }

    @Override
    public String getDefaultResponse() {
        return "41 10 01 0F"; // Default response with 2.71g/s MAF
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0 g/s MAF
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
        float maf = Float.parseFloat(response);
        int hexValue = Math.round(maf * 100);
        return "41 10" + String.format("%04X", hexValue);
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