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
        comPort.setBaudRate(9600);

        if (comPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open port.");
            return;
        }

        try (InputStream inStream = comPort.getInputStream();
             OutputStream outStream = comPort.getOutputStream()) {

            // Execute commands
            new EchoOffCommand().run(inStream, outStream);
            new LineFeedOffCommand().run(inStream, outStream);
            new TimeoutCommand(2500).run(inStream, outStream);
            new SelectProtocolCommand(ObdProtocols.ISO_9141_2).run(inStream, outStream);
            System.out.println("Getting Vin");
            new VinCommand().run(inStream, outStream);
            System.out.println("Getting Ambient Temperature");
            new AmbientAirTemperatureCommand().run(inStream, outStream);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            comPort.closePort();
            System.out.println("Port closed.");
        }
    }
}