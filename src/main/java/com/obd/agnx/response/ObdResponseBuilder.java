package com.obd.agnx.response;

public class ObdResponseBuilder {
    private String header;
    private String mode;
    private String pid;
    private String data;
    private int length;

    public ObdResponseBuilder setHeader(String header) {
        this.header = header;
        return this;
    }

    public ObdResponseBuilder setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public ObdResponseBuilder setPid(String pid) {
        this.pid = pid;
        return this;
    }

    public ObdResponseBuilder setData(String data) {
        this.data = data;
        this.length = data.length();
        return this;
    }

    public String build() {
        return header + " " + mode + " " + pid + " " + length + " " + data;
    }
}
