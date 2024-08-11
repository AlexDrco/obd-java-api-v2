package com.obd.examples;

import com.obd.comm.CommandResponse;
import com.obd.comm.SimulatedSender;
import com.obd.comm.sender.AbstractOBDSender;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.commands.control.*;
import com.obd.pires.commands.engine.RPMCommand;
import com.obd.pires.commands.fuel.FuelLevelCommand;
import com.obd.pires.commands.protocol.ObdRawCommand;
import com.obd.pires.commands.temperature.EngineCoolantTemperatureCommand;

import java.util.Arrays;
import java.util.List;

public class SimulatedExample {

    /*
     * This example demonstrates how to use the SimulatedSender class to simulate a
     * "scanner" communicating with an "OBD-II capable ECU".
     * The Sender makes the command requests, simulates the stream and the corresponding
     * response class provides the default response.
     * The Command <-> Response switch statement can be found in AbstractOBDSender class.
     */
    public static void main(String[] args) {

        AbstractOBDSender sender = new SimulatedSender();

        // Only simulated, code does nothing
        if (!sender.startConnection()) {
            System.err.println("Failed to simulate start connection.");
            return;
        }

        // Only simulated, code does nothing
        sender.setupEML327();

        List<ObdCommand> commands = Arrays.asList(
                new DistanceSinceCCCommand(),
                new TroubleCodesCommand(),
                new PermanentTroubleCodesCommand(),
                new RPMCommand(),
                new VinCommand(),
                new FuelLevelCommand(),
                new EngineCoolantTemperatureCommand(),
                new DtcNumberCommand(),
                new ObdRawCommand("01 03")
        );

        sender.sendCommands(commands);

        List<CommandResponse> responses = sender.getCommandResponses();
        for (CommandResponse response : responses) {
            System.out.println("Command: " + response.getCommand() + ", Response: " + response.getResponse());
        }
    }
}
