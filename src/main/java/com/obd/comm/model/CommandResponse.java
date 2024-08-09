package com.obd.comm.model;

public class CommandResponse {
    private final String command;
    private final String response;

    public CommandResponse(String command, String response) {
        this.command = command;
        this.response = response;
    }

    public String getCommand() {
        return command;
    }

    public String getResponse() {
        return response;
    }
}