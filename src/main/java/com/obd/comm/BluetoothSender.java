package com.obd.comm;

import com.obd.comm.sender.AbstractOBDSender;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BluetoothSender extends AbstractOBDSender {

    private final String address;
    private final String uuid;
    private StreamConnection connection;

    public BluetoothSender(String address, String uuid) {
        this.address = address;
        this.uuid = uuid;
    }

    @Override
    public boolean startConnection() {
        try {
            String connectionURL = "btspp://" + address + ":" + uuid + ";authenticate=false;encrypt=false;master=false";
            connection = (StreamConnection) Connector.open(connectionURL);
            System.out.println("Bluetooth connection started.");
            return true;
        } catch (IOException e) {
            System.out.println("Failed to start Bluetooth connection.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        return connection.openInputStream();
    }

    @Override
    protected OutputStream getOutputStream() throws IOException {
        return connection.openOutputStream();
    }

    @Override
    protected void closeConnection() throws IOException {
        if (connection != null) {
            connection.close();
            System.out.println("Bluetooth connection closed.");
        }
    }
}