package com.obd;


import com.fazecast.jSerialComm.SerialPort;
import com.obd.pires.commands.protocol.EchoOffCommand;
import com.obd.pires.commands.protocol.LineFeedOffCommand;
import com.obd.pires.commands.protocol.SelectProtocolCommand;
import com.obd.pires.commands.protocol.TimeoutCommand;
import com.obd.pires.commands.temperature.AmbientAirTemperatureCommand;
import com.obd.pires.enums.ObdProtocols;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        SerialPort comPort = SerialPort.getCommPorts()[0];
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
            new TimeoutCommand(125).run(inStream, outStream);
            new SelectProtocolCommand(ObdProtocols.AUTO).run(inStream, outStream);
            new AmbientAirTemperatureCommand().run(inStream, outStream);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            comPort.closePort();
            System.out.println("Port closed.");
        }
    }
}