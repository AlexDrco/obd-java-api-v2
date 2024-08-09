package com.obd.comm;

import com.fazecast.jSerialComm.SerialPort;
import com.obd.comm.sender.AbstractOBDSender;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * SerialPortSender is an AbstractOBDSender that uses a serial port
 * to communicate with the OBD-II interface. It handles opening and closing the serial port,
 * as well as providing input and output streams for communication.
 */
public class SerialPortSender extends AbstractOBDSender {

    private final SerialPort serialPort;
    // Baud rate is set to 38400 as it seems to be the most common rate for OBD-II interfaces.
    private final int BAUD_RATE = 38400;
    // Relatively long timeout to allow for slower devices to respond. Usually newer vehicles.
    private final int TIMEOUT = 10000;

    /**
     * Initializes the SerialPortSender with a specific port name ("COM5").
     * @param portName The name of the serial port to be used.
     */
    public SerialPortSender(String portName) {
        serialPort = SerialPort.getCommPort(portName);
        serialPort.setBaudRate(BAUD_RATE);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, TIMEOUT, TIMEOUT);
    }

    /**
     * Initializes the SerialPortSender with a specific port index.
     * @param portIndex The index of the serial port to be used.
     */
    public SerialPortSender(int portIndex) {
        serialPort = SerialPort.getCommPorts()[portIndex];
        serialPort.setBaudRate(BAUD_RATE);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, TIMEOUT, TIMEOUT);
    }

    @Override
    public boolean startConnection() {
        if (serialPort.openPort()) {
            System.out.println("Port opened successfully.");
            return true;
        } else {
            System.out.println("Failed to open port.");
            return false;
        }
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        return serialPort.getInputStream();
    }

    @Override
    protected OutputStream getOutputStream() throws IOException {
        return serialPort.getOutputStream();
    }

    @Override
    protected void closeConnection() throws IOException {
        serialPort.closePort();
        System.out.println("Port closed.");
    }
}