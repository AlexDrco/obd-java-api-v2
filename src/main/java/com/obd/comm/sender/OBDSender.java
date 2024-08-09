package com.obd.comm.sender;

import com.obd.comm.model.CommandResponse;
import com.obd.pires.commands.ObdCommand;

import java.util.List;

/**
 * Methods required for sending OBD commands
 * to a vehicle's OBD-II interface. Implementations of this interface should handle the
 * connection setup, command execution, and response retrieval.
 */
public interface OBDSender {

    /**
     * Starts the connection to the OBD device.
     * @return true if the connection was successfully started, false otherwise.
     */
    boolean startConnection();

    /**
     * Sets up the OBD device by sending a series of initialization commands.
     */
    void setupEML327();

    /**
     * Sends a list of OBD commands to the device and stores their responses.
     * @param commands List of OBD commands to be sent.
     */
    void sendCommands(List<ObdCommand> commands);

    /**
     * Returns the list of command responses.
     * @return List of CommandResponse objects.
     */
    List<CommandResponse> getCommandResponses();
}