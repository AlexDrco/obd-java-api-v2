package com.obd.comm.sender;

import com.obd.comm.CommandResponseRaw;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.commands.protocol.*;
import com.obd.pires.enums.ObdProtocols;
import com.obd.pires.exceptions.NoDataException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Representing an OBD command device, most probably a scanner.
 * Provides methods to set up the EML327 device and send OBD commands.
 */
public abstract class AbstractOBDSender implements OBDSender {
    protected final List<CommandResponseRaw> commandResponsRaws;
    protected final List<String> hexResponses;

    /**
     * Initializes the list of command responses.
     *
     */
    public AbstractOBDSender() {
        commandResponsRaws = new ArrayList<>();
        hexResponses = new ArrayList<>();
    }

    /**
     * Gets the input stream for communication with the OBD device.
     *
     * @return the input stream
     * @throws IOException if an I/O error occurs
     */
    protected abstract InputStream getInputStream() throws IOException;

    /**
     * Gets the output stream for communication with the OBD device.
     *
     * @return the output stream
     * @throws IOException if an I/O error occurs
     */
    protected abstract OutputStream getOutputStream() throws IOException;

    /**
     * Closes the connection to the OBD device.
     *
     * @throws IOException if an I/O error occurs
     */
    protected abstract void closeConnection() throws IOException;

    /**
     * Sets up the EML327 device by sending some common initialization commands.
     *
     */
    @Override
    public void setupEML327() {
        try {
            new ObdResetCommand().run(getInputStream(), getOutputStream());
            new EchoOffCommand().run(getInputStream(), getOutputStream());
            new LineFeedOffCommand().run(getInputStream(), getOutputStream());
            new ObdRawCommand("AT H1").run(getInputStream(), getOutputStream());
            new TimeoutCommand(5000).run(getInputStream(), getOutputStream());
            new SelectProtocolCommand(ObdProtocols.AUTO).run(getInputStream(), getOutputStream());
        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
            try {
                closeConnection();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Sends a list of OBD commands to the device.
     *
     * @param commands the list of OBD commands to send
     */
    @Override
    public void sendCommands(List<ObdCommand> commands) {
        if (!startConnection()) {
            System.err.println("Failed to start connection. Cannot send commands.");
            return;
        }

        try {
            commandResponsRaws.addAll(commands.stream()
                    .map(this::executeCommand)
                    .toList());

            new ObdResetCommand().run(getInputStream(), getOutputStream());
            new CloseCommand().run(getInputStream(), getOutputStream());
        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Executes a single OBD command and returns the response.
     *
     * @param command the OBD command to execute
     * @return the command response
     */
    private CommandResponseRaw executeCommand(ObdCommand command) {
        try {
            command.run(getInputStream(), getOutputStream());
            String response = command.getFormattedResult();
            String rawHex = command.getRawHexString();
            hexResponses.add(rawHex);
            return new CommandResponseRaw(command.getName(), command.getFormattedResult(), command.getRawHexString());
        } catch (IOException | InterruptedException | NoDataException | NumberFormatException e) {
            System.err.println("Failed to execute " + command.getName() + ": " + e.getMessage());
            return new CommandResponseRaw(command.getName(), "Error: " + e.getMessage(), command.getRawHexString());
        }
    }

    /**
     * Gets the list of command responses.
     *
     * @return the list of command responses
     */
    @Override
    public List<CommandResponseRaw> getCommandResponses() {
        return commandResponsRaws;
    }

    /**
     * Gets the list of raw hex responses.
     *
     * @return the list of raw hex responses
     */
    public List<String> getHexResponses() {
        return hexResponses;
    }
}