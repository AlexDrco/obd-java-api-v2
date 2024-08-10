package com.obd;

import com.obd.comm.CommandResponse;
import com.obd.comm.SimulatedSender;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.commands.control.*;
import com.obd.pires.commands.engine.RPMCommand;
import com.obd.pires.commands.fuel.FuelLevelCommand;
import com.obd.pires.commands.protocol.AvailablePidsCommand_01_20;
import com.obd.pires.commands.protocol.ObdRawCommand;
import com.obd.pires.commands.temperature.EngineCoolantTemperatureCommand;

import java.util.Arrays;
import java.util.List;

public class SimulatedExample {
    public static void main(String[] args) {

        SimulatedSender sender = new SimulatedSender();

        if (!sender.startConnection()) {
            System.err.println("Failed to simulate start connection.");
            return;
        }

        sender.setupEML327();

        List<ObdCommand> commands = Arrays.asList(
                new DistanceSinceCCCommand(),
                new TroubleCodesCommand(),
                new PermanentTroubleCodesCommand(),
                new RPMCommand(),
                new VinCommand(),
                new FuelLevelCommand(),
                new EngineCoolantTemperatureCommand(),
                new AvailablePidsCommand_01_20(),
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
