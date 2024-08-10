package com.obd.comm;

import com.obd.comm.sender.AbstractOBDSender;
import com.obd.comm.CommandResponse;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.exceptions.NoDataException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class SimulatedSender extends AbstractOBDSender {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[0]);

    @Override
    protected InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    protected OutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    @Override
    protected void closeConnection() throws IOException {
        // Simulated close connection
    }

    @Override
    public boolean startConnection() {
        // Simulate starting connection
        return true;
    }

    @Override
    public void sendCommands(List<ObdCommand> commands) {
        for (ObdCommand command : commands) {
            String response = generateResponse(command);
            inputStream = new ByteArrayInputStream(response.getBytes());
            try {
                command.run(getInputStream(), getOutputStream());
                commandResponses.add(new CommandResponse(command.getName(), command.getFormattedResult()));
            } catch (IOException | InterruptedException | NoDataException | NumberFormatException e) {
                commandResponses.add(new CommandResponse(command.getName(), "Error: " + e.getMessage()));
            }
        }
    }
}