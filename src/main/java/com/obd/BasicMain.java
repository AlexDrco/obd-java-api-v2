
package com.obd;

import com.fazecast.jSerialComm.SerialPort;
import com.obd.pires.commands.protocol.*;
import com.obd.pires.enums.ObdProtocols;


import java.io.InputStream;
import java.io.OutputStream;

public class BasicMain {
    public static void main(String[] args) {
        try {
            // Open serial port connection
            SerialPort serialPort = SerialPort.getCommPort("COM5"); // Adjust the port name as needed
            serialPort.setComPortParameters(9600, 8, 1, 0);
            serialPort.openPort();

            InputStream in = serialPort.getInputStream();
            OutputStream out = serialPort.getOutputStream();

            // Initialize ELM327
            new ObdRawCommand("ATZ").run(in, out);
            new ObdRawCommand("AT E0").run(in, out);
            new ObdRawCommand("AT L0").run(in, out);
            new ObdRawCommand("AT S0").run(in, out);
            new ObdRawCommand("AT H0").run(in, out);
            new ObdRawCommand("AT SP 0").run(in, out);


            // Send OBD command
            ObdRawCommand command = new ObdRawCommand("010C");
            command.run(in, out);
            System.out.println("RPM: " + command.getFormattedResult());

            // Close serial port
            serialPort.closePort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}