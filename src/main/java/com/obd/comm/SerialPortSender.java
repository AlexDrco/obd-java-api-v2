package com.obd.comm;

import com.fazecast.jSerialComm.SerialPort;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.commands.protocol.*;
import com.obd.pires.enums.ObdProtocols;
import com.obd.pires.exceptions.NoDataException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SerialPortSender {

    private final SerialPort serialPort;
    private final List<CommandResponse> commandResponses;

    public SerialPortSender(String portName) {
        serialPort = SerialPort.getCommPort(portName);
        serialPort.setBaudRate(38400);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 10000, 10000);
        commandResponses = new ArrayList<>();
    }

    public SerialPortSender(int portIndex) {
        serialPort = SerialPort.getCommPorts()[portIndex];
        serialPort.setBaudRate(38400);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 10000, 10000);
        commandResponses = new ArrayList<>();
    }

    public boolean startPort() {
        if (serialPort.openPort()) {
            System.out.println("Port opened successfully.");
            return true;
        } else {
            System.out.println("Failed to open port.");
            return false;
        }
    }

    public void setupELM327() {
        try {
            new ObdResetCommand().run(serialPort.getInputStream(), serialPort.getOutputStream());
            new EchoOffCommand().run(serialPort.getInputStream(), serialPort.getOutputStream());
            new LineFeedOffCommand().run(serialPort.getInputStream(), serialPort.getOutputStream());
            new TimeoutCommand(5000).run(serialPort.getInputStream(), serialPort.getOutputStream());
            new SelectProtocolCommand(ObdProtocols.AUTO).run(serialPort.getInputStream(), serialPort.getOutputStream());

        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
            serialPort.closePort();
            System.out.println("Port closed.");
        }
    }

    public void sendCommands(List<ObdCommand> commands) {
        if (!serialPort.isOpen() && !startPort()) {
            System.err.println("Failed to open port. Cannot send commands.");
            return;
        }

        try {
            // Execute each command using streams
            commandResponses.addAll(commands.stream()
                    .map(this::executeCommand)
                    .toList());

            // Reset and close the ELM327
            new ObdResetCommand().run(serialPort.getInputStream(), serialPort.getOutputStream());
            new CloseCommand().run(serialPort.getInputStream(), serialPort.getOutputStream());

        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            serialPort.closePort();
            System.out.println("Port closed.");
        }
    }

    private CommandResponse executeCommand(ObdCommand command) {
        try {
            command.run(serialPort.getInputStream(), serialPort.getOutputStream());
            String response = command.getFormattedResult();
            System.out.println(command.getName() + ": " + response);
            return new CommandResponse(command.getName(), response);
        } catch (IOException | InterruptedException | NoDataException | NumberFormatException e) {
            System.err.println("Failed to execute " + command.getName() + ": " + e.getMessage());
            return new CommandResponse(command.getName(), "Error: " + e.getMessage());
        }
    }

    public List<CommandResponse> getCommandResponses() {
        return commandResponses;
    }
}