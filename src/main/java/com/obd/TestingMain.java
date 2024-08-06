package com.obd;

import com.obd.pires.commands.control.VinCommand;
import com.obd.pires.commands.protocol.EchoOffCommand;
import com.obd.pires.commands.protocol.LineFeedOffCommand;
import com.obd.pires.commands.protocol.SelectProtocolCommand;
import com.obd.pires.commands.protocol.TimeoutCommand;
import com.obd.pires.commands.temperature.AmbientAirTemperatureCommand;
import com.obd.pires.enums.ObdProtocols;

import java.io.*;

public class TestingMain {

    public static void main(String[] args) {

        // Mock input stream with some data
        String inputData = "01 46 02 01 1A";
        InputStream inStream = new ByteArrayInputStream(inputData.getBytes());

        // Mock output stream
        OutputStream outStream = new ByteArrayOutputStream();



        // Execute commands
        try {
            new EchoOffCommand().run(inStream, outStream);
            new LineFeedOffCommand().run(inStream, outStream);
            new TimeoutCommand(125).run(inStream, outStream);
            new SelectProtocolCommand(ObdProtocols.AUTO).run(inStream, outStream);

            // Mock input stream with some data
            byte[] vinData = new byte[]{
                    '4', '9', ' ', '0', '2', ' ', '0', '1', ' ', '0', '0', ' ', '0', '0', ' ', '0', '0', ' ', '5', '7', '\n',
                    '4', '9', ' ', '0', '2', ' ', '0', '2', ' ', '5', '0', ' ', '3', '0', ' ', '5', 'A', ' ', '5', 'A', '\n',
                    '4', '9', ' ', '0', '2', ' ', '0', '3', ' ', '5', 'A', ' ', '3', '9', ' ', '3', '9', ' ', '5', 'A', '\n',
                    '4', '9', ' ', '0', '2', ' ', '0', '4', ' ', '5', '4', ' ', '5', '3', ' ', '3', '3', ' ', '3', '9', '\n',
                    '4', '9', ' ', '0', '2', ' ', '0', '5', ' ', '3', '2', ' ', '3', '1', ' ', '3', '2', ' ', '3', '4', '>'
            };
            inStream = new ByteArrayInputStream(vinData);
            VinCommand vinCommand = new VinCommand();
            vinCommand.run(inStream, outStream);
            System.out.println("VIN: " + vinCommand.getFormattedResult());

            String vinData2 = "4902010000005749020250305A5A4902035A39395A4902045453333949020532313234";
            inStream = new ByteArrayInputStream(vinData2.getBytes());
            vinCommand.run(inStream, outStream);
            System.out.println("VIN: " + vinCommand.getFormattedResult());

            // Mock data for AmbientAirTemperatureCommand
            String temperatureData = "41 46 42";
            inStream = new ByteArrayInputStream(temperatureData.getBytes());
            AmbientAirTemperatureCommand ambientAirTemperatureCommand = new AmbientAirTemperatureCommand();
            ambientAirTemperatureCommand.run(inStream, outStream);

            System.out.println(ambientAirTemperatureCommand.getFormattedResult());

            // Use ECU Simulator to generate responses
            String request = "010C"; // Example request for Engine RPM

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Print the output stream
        System.out.println(outStream.toString());
    }
}
