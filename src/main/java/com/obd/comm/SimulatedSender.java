package com.obd.comm;

import com.obd.comm.sender.AbstractOBDSender;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.exceptions.NoDataException;
import com.obd.agnx.response.OBDResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Simulates sending OBD commands and receiving responses.
 * It extends AbstractOBDSender and overrides methods to provide simulated input and output streams.
 */
public class SimulatedSender extends AbstractOBDSender {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[0]);

    /**
     * Gets the input stream for communication with the simulated OBD device.
     *
     * @return the input stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected InputStream getInputStream() throws IOException {
        return inputStream;
    }

    /**
     * Gets the output stream for communication with the simulated OBD device.
     *
     * @return the output stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected OutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    /**
     * Closes the connection to the simulated OBD device.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void closeConnection() throws IOException {
        // Simulated close connection
    }

    /**
     * Simulates starting a connection to the OBD device.
     *
     * @return true indicating the connection was successfully started
     */
    @Override
    public boolean startConnection() {
        // Simulate starting connection
        return true;
    }

    /**
     * Sends a list of OBD commands to the simulated device and processes the responses.
     *
     * @param commands the list of OBD commands to send
     */
    @Override
    public void sendCommands(List<ObdCommand> commands) {
        for (ObdCommand command : commands) {
            String response = OBDResponse.generateDefaultResponse(command);
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