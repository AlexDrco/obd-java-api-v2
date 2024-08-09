package com.obd.comm;

import com.fazecast.jSerialComm.SerialPort;
import com.obd.agnx.response.OBDResponseHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SerialPortListener {

    private final SerialPort serialPort;
    private BufferedReader inputReader;
    private OutputStream outputStream;
    private final OBDResponseHandler obdResponseHandler;

    public SerialPortListener(String portName) {
        serialPort = SerialPort.getCommPort(portName);
        serialPort.setBaudRate(38400);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 10000, 10000);

        obdResponseHandler = new OBDResponseHandler();

        if (serialPort.openPort()) {
            System.out.println("Port opened successfully.");
            inputReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream(), StandardCharsets.UTF_8));
            outputStream = serialPort.getOutputStream();
            listen();
        } else {
            System.out.println("Failed to open port.");
        }
    }

    private void listen() {
        new Thread(() -> {
            try {
                String inputLine;
                while ((inputLine = inputReader.readLine()) != null) {
                    System.out.println("Received: " + inputLine);
                    String response = handleRequest(inputLine);
                    outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                serialPort.closePort();
                System.out.println("Port closed.");
            }
        }).start();
    }

    private String handleRequest(String request) {
        // Use ObdResponseHandler to get the appropriate response
        return obdResponseHandler.getResponse(request);
    }

}