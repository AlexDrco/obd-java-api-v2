package com.obd.agnx.response.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public abstract class ObdResponse {
    protected ArrayList<Integer> buffer;
    public String cmd;
    protected String rawData;

    public ObdResponse(String command) {
        this.cmd = command;
        this.buffer = new ArrayList();
    }

    public void run(InputStream in, OutputStream out) throws IOException, InterruptedException {
        this.sendResponse(out);
    }

    protected void sendResponse(OutputStream out) throws IOException, InterruptedException {
        out.write((this.cmd + "\r").getBytes());
        out.flush();
    }

    public String getResult() {
        return this.rawData;
    }

    public abstract String getFormattedResult();

    public abstract String getCalculatedResult();

    protected ArrayList<Integer> getBuffer() {
        return this.buffer;
    }

    public abstract String getName();
}