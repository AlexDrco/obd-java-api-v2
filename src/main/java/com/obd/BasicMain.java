package com.obd;

import com.obd.comm.SerialPortSender;
import com.obd.comm.CommandResponse;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.commands.control.*;
import com.obd.pires.commands.engine.RPMCommand;
import com.obd.pires.commands.fuel.FuelLevelCommand;
import com.obd.pires.commands.protocol.*;
import com.obd.pires.commands.temperature.EngineCoolantTemperatureCommand;

import java.util.Arrays;
import java.util.List;

public class BasicMain {
    public static void main(String[] args) {

        SerialPortSender sender = new SerialPortSender("COM5");
        if (!sender.startPort()) {
            System.err.println("Failed to open port.");
            return;
        }

        sender.setupELM327();

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