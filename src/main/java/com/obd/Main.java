package com.obd;

import com.fazecast.jSerialComm.SerialPort;
import com.obd.pires.commands.control.VinCommand;
import com.obd.pires.commands.protocol.EchoOffCommand;
import com.obd.pires.commands.protocol.LineFeedOffCommand;
import com.obd.pires.commands.protocol.SelectProtocolCommand;
import com.obd.pires.commands.protocol.TimeoutCommand;
import com.obd.pires.commands.temperature.AmbientAirTemperatureCommand;
import com.obd.pires.enums.ObdProtocols;

import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SerialPort[] commPorts = SerialPort.getCommPorts();
        Arrays.stream(commPorts).forEach(System.out::println);
        SerialPort comPort = commPorts[2];
        System.out.println("Connected to: " + comPort.getSystemPortName());
        comPort.setBaudRate(38400);

        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        //comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 5000, 0);

        if (comPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open port.");
            return;
        }

        try (InputStream inStream = comPort.getInputStream();
             OutputStream outStream = comPort.getOutputStream()) {

            // Execute commands
            System.out.println("Executing EchoOffCommand");
            new EchoOffCommand().run(inStream, outStream);
            System.out.println("Executing LineFeedOffCommand");
            new LineFeedOffCommand().run(inStream, outStream);
            System.out.println("Executing TimeoutCommand");
            new TimeoutCommand(2500).run(inStream, outStream);
            System.out.println("Executing SelectProtocolCommand");
            new SelectProtocolCommand(ObdProtocols.AUTO).run(inStream, outStream);
            System.out.println("Getting Vin");
            new VinCommand().run(inStream, outStream);
            System.out.println("Getting Ambient Temperature");
            new AmbientAirTemperatureCommand().run(inStream, outStream);

        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("InterruptedException occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            comPort.closePort();
            System.out.println("Port closed.");
        }
    }
}