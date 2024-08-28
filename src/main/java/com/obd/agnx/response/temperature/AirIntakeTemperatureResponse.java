package com.obd.agnx.response.temperature;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class AirIntakeTemperatureResponse extends OBDResponse {

    public AirIntakeTemperatureResponse() {
        super("01 0F");
    }

    @Override
    public String getDefaultResponse() {
        return "41 0F 5D"; // Default response with 37°C air intake temperature
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
        int initialDecimalValue = Integer.parseInt(initialValue);
        int noisyDecimal = PerlinNoise.addNoiseToInt(initialDecimalValue, 1);
        return Integer.toString(noisyDecimal);
    }

    @Override
    public String stringToHex(String response) {
        int rawValue = Integer.parseInt(response) + 40;
        String hexValue = String.format("%02X", rawValue);
        return "41 0F" + hexValue;
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