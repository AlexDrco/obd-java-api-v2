package com.obd.comm;

public class CommandResponseRaw {
    private final String command;
    private final String response;
    private final String raw;

    public CommandResponseRaw(String command, String response, String raw) {
        this.command = command;
        this.response = response;
        this.raw = raw;
    }

    public CommandResponseRaw(String response){
        this.command = null;
        this.response = response;
        this.raw = null;
    }

    public String getCommand() {
        return command;
    }

    public String getResponse() {
        return response;
    }

    public String getRaw() {
        return raw;
    }
}