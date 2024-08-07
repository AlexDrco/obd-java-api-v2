package com.obd;

import com.fazecast.jSerialComm.SerialPort;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.commands.control.*;
import com.obd.pires.commands.engine.RPMCommand;
import com.obd.pires.commands.fuel.FuelLevelCommand;
import com.obd.pires.commands.protocol.*;
import com.obd.pires.commands.temperature.EngineCoolantTemperatureCommand;
import com.obd.pires.enums.ObdProtocols;
import com.obd.pires.exceptions.NoDataException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) {
        SerialPort[] commPorts = SerialPort.getCommPorts();
        Arrays.stream(commPorts).forEach(System.out::println);
        SerialPort comPort = commPorts[2];
        System.out.println("Connected to: " + comPort.getSystemPortName());
        comPort.setBaudRate(38400);
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, 1000);

        if (comPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open port.");
            return;
        }

        try {
            // Setup ELM327
            new ObdResetCommand().run(comPort.getInputStream(), comPort.getOutputStream());
            new EchoOffCommand().run(comPort.getInputStream(), comPort.getOutputStream());
            new LineFeedOffCommand().run(comPort.getInputStream(), comPort.getOutputStream());
            new TimeoutCommand(5000).run(comPort.getInputStream(), comPort.getOutputStream());
            new SelectProtocolCommand(ObdProtocols.AUTO).run(comPort.getInputStream(), comPort.getOutputStream());

            // List of commands to execute
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

            // Execute each command
            for (ObdCommand command : commands) {
                executeCommand(command, comPort);
            }

            new ObdResetCommand().run(comPort.getInputStream(), comPort.getOutputStream());
            new CloseCommand().run(comPort.getInputStream(), comPort.getOutputStream());

        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            comPort.closePort();
            System.out.println("Port closed.");
        }
    }

    private static void executeCommand(ObdCommand command, SerialPort comPort) {
        try {
            command.run(comPort.getInputStream(), comPort.getOutputStream());
            System.out.println(command.getName() + ": " + command.getFormattedResult());
        } catch (IOException | InterruptedException | NoDataException e) {
            System.err.println("Failed to execute " + command.getName() + ": " + e.getMessage());
        }
    }
}