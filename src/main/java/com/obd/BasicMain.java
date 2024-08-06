package com.obd;

import com.fazecast.jSerialComm.SerialPort;
import java.io.*;

public class BasicMain {
    public static void main(String[] args) {
        SerialPort[] commPorts = SerialPort.getCommPorts();
        SerialPort comPort = commPorts[2]; // Ensure this is the correct port
        comPort.setBaudRate(38400);

        if (comPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open port.");
            return;
        }

        try (InputStream inStream = comPort.getInputStream();
             OutputStream outStream = comPort.getOutputStream()) {

            // Initialize the ELM327
            outStream.write("ATZ\r".getBytes());
            Thread.sleep(100);
            outStream.write("ATE0\r".getBytes());
            Thread.sleep(100);
            outStream.write("ATL0\r".getBytes());
            Thread.sleep(100);
            outStream.write("ATSP0\r".getBytes()); // Set protocol to automatic
            Thread.sleep(100);

            // Send the VIN request
            outStream.write("0902\r".getBytes());
            Thread.sleep(100);

            // Read the response
            byte[] buffer = new byte[1024];
            int bytesRead = inStream.read(buffer);
            String response = new String(buffer, 0, bytesRead);
            System.out.println("VIN Response: " + response);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            comPort.closePort();
            System.out.println("Port closed.");
        }
    }
}

