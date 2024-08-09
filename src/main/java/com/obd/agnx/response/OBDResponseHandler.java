package com.obd.agnx.response;


import java.util.HashMap;
import java.util.Map;

public class OBDResponseHandler {

    private final Map<String, String> obdResponses;

    public OBDResponseHandler() {
        obdResponses = new HashMap<>();
        initializeObdResponses();
    }

    private void initializeObdResponses() {
        // Add OBD command-response pairs
        obdResponses.put("010C", "41 0C 1A F8"); // Example response for Engine RPM
        obdResponses.put("010D", "41 0D 40");    // Example response for Vehicle Speed
        // Add more OBD command-response pairs as needed
    }

    public String getResponse(String request) {
        return obdResponses.getOrDefault(request.trim(), "NO DATA");
    }
}