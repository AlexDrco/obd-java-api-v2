package com.obd.examples;

import com.obd.comm.sender.OBDSender;
import com.obd.comm.SerialPortSender;
import com.obd.comm.CommandResponseRaw;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.commands.control.*;
import com.obd.pires.commands.engine.RPMCommand;
import com.obd.pires.commands.fuel.FuelLevelCommand;
import com.obd.pires.commands.protocol.*;
import com.obd.pires.commands.temperature.EngineCoolantTemperatureCommand;

import java.util.Arrays;
import java.util.List;

public class SerialExample {

    public static void main(String[] args) {

        OBDSender sender = new SerialPortSender("COM5");

        if (!sender.startConnection()) {
            System.err.println("Failed to start connection.");
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

        List<CommandResponseRaw> responses = sender.getCommandResponses();
        for (CommandResponseRaw response : responses) {
            System.out.println("Command: " + response.getCommand() + ", Response: " + response.getResponse() + ", Raw: " + response.getRaw());
        }
    }
}