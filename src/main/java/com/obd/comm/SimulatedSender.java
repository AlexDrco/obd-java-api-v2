package com.obd.comm;

import com.obd.comm.sender.AbstractOBDSender;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.exceptions.NoDataException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Simulates the behavior of an OBD sender.
 * It extends the AbstractOBDSender class and overrides methods to provide
 * simulated input and output streams, as well as handling the sending of OBD commands.
 */
public class SimulatedSender extends AbstractOBDSender {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[0]);

    /**
     * Returns the input stream for the simulated sender.
     *
     * @return the input stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected InputStream getInputStream() throws IOException {
        return inputStream;
    }

    /**
     * Returns the output stream for the simulated sender.
     *
     * @return the output stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected OutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    /**
     * Closes the connection for the simulated sender.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void closeConnection() throws IOException {
        // Simulated close connection
    }

    /**
     * Starts the connection for the simulated sender.
     *
     * @return true if the connection is successfully started, false otherwise
     */
    @Override
    public boolean startConnection() {
        // Simulate starting connection
        return true;
    }

    /**
     * Simulates the Streams Sends a list of OBD commands
     * and processes their responses.
     * @param commands the list of OBD commands to send
     */
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