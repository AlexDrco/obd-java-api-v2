package com.obd;

import com.fazecast.jSerialComm.SerialPort;
import com.obd.pires.commands.control.VinCommand;
import com.obd.pires.commands.engine.RPMCommand;
import com.obd.pires.commands.protocol.*;
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
        //comPort.setBaudRate(38400);

        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 100);

        //comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 5000, 0);

        if (comPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open port.");
            return;
        }

        try{
            // Execute commands
            System.out.println("Reset OBD");
            new ObdResetCommand().run(comPort.getInputStream(), comPort.getOutputStream());
            System.out.println("Executing EchoOffCommand");
            new EchoOffCommand().run(comPort.getInputStream(), comPort.getOutputStream());
            System.out.println("Executing LineFeedOffCommand");
            new LineFeedOffCommand().run(comPort.getInputStream(), comPort.getOutputStream());
            System.out.println("Executing TimeoutCommand");
            new TimeoutCommand(5000).run(comPort.getInputStream(), comPort.getOutputStream());
            System.out.println("Executing SelectProtocolCommand");
            new SelectProtocolCommand(ObdProtocols.AUTO).run(comPort.getInputStream(), comPort.getOutputStream());
            System.out.println("Headers Off");
            new HeadersOffCommand().run(comPort.getInputStream(), comPort.getOutputStream());
            System.out.println("Getting EngineRPM");
            new RPMCommand().run(comPort.getInputStream(), comPort.getOutputStream());

            new ObdResetCommand().run(comPort.getInputStream(), comPort.getOutputStream());
            new CloseCommand().run(comPort.getInputStream(), comPort.getOutputStream());

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