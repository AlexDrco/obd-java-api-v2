package com.obd.agnx.response;

import com.obd.agnx.response.control.ObdResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class PersistentResponse extends ObdResponse {
    private static Map<String, String> knownValues = new HashMap<>();
    private static Map<String, ArrayList<Integer>> knownBuffers = new HashMap<>();

    public PersistentResponse(String command) {
        super(command);
    }

    public PersistentResponse(ObdResponse other) {
        this(other.cmd);
    }

    public static void reset() {
        knownValues = new HashMap<>();
        knownBuffers = new HashMap<>();
    }

    public static boolean knows(Class cmd) {
        String key = cmd.getSimpleName();
        return knownValues.containsKey(key);
    }

    protected void writeResult(OutputStream out) throws IOException {
        String key = this.getClass().getSimpleName();
        if (knownValues.containsKey(key)) {
            this.rawData = (String)knownValues.get(key);
            this.buffer = (ArrayList)knownBuffers.get(key);
        }
    }

    public void run(InputStream in, OutputStream out) throws IOException, InterruptedException {
        String key = this.getClass().getSimpleName();
        if (knownValues.containsKey(key)) {
            this.rawData = (String)knownValues.get(key);
            this.buffer = (ArrayList)knownBuffers.get(key);
        } else {
            super.run(in, out);
        }
    }
}