package com.obd.comm.sender;

import com.obd.agnx.response.common.*;
import com.obd.comm.CommandResponse;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.commands.control.*;
import com.obd.pires.commands.engine.RPMCommand;
import com.obd.pires.commands.fuel.FuelLevelCommand;
import com.obd.pires.commands.protocol.*;
import com.obd.pires.commands.temperature.EngineCoolantTemperatureCommand;
import com.obd.pires.enums.ObdProtocols;
import com.obd.pires.exceptions.NoDataException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * AbstractOBDSender provides a template for sending OBD commands
 * to a vehicle's OBD-II interface. It handles the setup of the device, sending commands,
 * and managing the connection.
 */
public abstract class AbstractOBDSender implements OBDSender {

    protected final List<CommandResponse> commandResponses;

    /**
     * Initializes the empty commandResponses list.
     */
    public AbstractOBDSender() {
        commandResponses = new ArrayList<>();
    }

    /**
     * Abstract method to get the input stream from the OBD device.
     *
     * @return InputStream from the OBD device.
     * @throws IOException if an I/O error occurs.
     */
    protected abstract InputStream getInputStream() throws IOException;

    /**
     * Abstract method to get the output stream to the OBD device.
     *
     * @return OutputStream to the OBD device.
     * @throws IOException if an I/O error occurs.
     */
    protected abstract OutputStream getOutputStream() throws IOException;

    /**
     * Abstract method to close the connection to the OBD device.
     *
     * @throws IOException if an I/O error occurs.
     */
    protected abstract void closeConnection() throws IOException;

    /**
     * Sets up the OBD EML327 device. Apparently common initialization commands.
     */
    @Override
    public void setupEML327() {
        try {
            new ObdResetCommand().run(getInputStream(), getOutputStream());
            new EchoOffCommand().run(getInputStream(), getOutputStream());
            new LineFeedOffCommand().run(getInputStream(), getOutputStream());
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
     * Sends a list of OBD commands to the device and stores their responses.
     *
     * @param commands List of OBD commands to be sent.
     */
    @Override
    public void sendCommands(List<ObdCommand> commands) {
        if (!startConnection()) {
            System.err.println("Failed to start connection. Cannot send commands.");
            return;
        }

        try {
            commandResponses.addAll(commands.stream()
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
     * Executes a single OBD command and returns its response.
     *
     * @param command The OBD command to be executed.
     * @return CommandResponse containing the result of the command.
     */
    private CommandResponse executeCommand(ObdCommand command) {
        try {
            command.run(getInputStream(), getOutputStream());
            String response = command.getFormattedResult();
            System.out.println(command.getName() + ": " + response);
            return new CommandResponse(command.getName(), response);
        } catch (IOException | InterruptedException | NoDataException | NumberFormatException e) {
            System.err.println("Failed to execute " + command.getName() + ": " + e.getMessage());
            return new CommandResponse(command.getName(), "Error: " + e.getMessage());
        }
    }

    /**
     * Returns the list of command responses.
     *
     * @return List of CommandResponse objects.
     */
    @Override
    public List<CommandResponse> getCommandResponses() {
        return commandResponses;
    }


    protected String generateResponse(ObdCommand command) {
        return switch (command) {
            case DistanceSinceCCCommand distanceSinceCCCommand -> new DistanceSinceCCResponse().getDefaultResponse();

            case TroubleCodesCommand troubleCodesCommand -> new TroubleCodesResponse().getDefaultResponse();

            case PermanentTroubleCodesCommand permanentTroubleCodesCommand -> new PermanentTroubleCodesResponse().getDefaultResponse();

            case RPMCommand rpmCommand -> new RPMResponse().getDefaultResponse();

            case VinCommand vinCommand -> new VinResponse().getDefaultResponse();

            case FuelLevelCommand fuelLevelCommand -> "41 2F 64";

            case EngineCoolantTemperatureCommand engineCoolantTemperatureCommand ->
                    new EngineCoolantTemperatureResponse().getDefaultResponse();

            case AvailablePidsCommand_01_20 availablePidsCommand0120 -> "41 00 BE 3E B8 13";

            case DtcNumberCommand dtcNumberCommand -> new DtcNumberResponse().getDefaultResponse();

            case ObdRawCommand obdRawCommand -> "NO DATA";

            case null, default -> "NO DATA"; // Default response for unknown commands

        };
    }
}