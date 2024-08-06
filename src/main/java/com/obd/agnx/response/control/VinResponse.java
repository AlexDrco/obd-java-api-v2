package com.obd.agnx.response.control;



import com.obd.agnx.response.ObdResponseBuilder;
import com.obd.agnx.response.PersistentResponse;
import com.obd.pires.commands.control.VinCommand;
import com.obd.pires.enums.AvailableCommandNames;


public class VinResponse extends PersistentResponse {
    String vin;

    public VinResponse(String vin) {
        super("09 02");
        this.vin = vin;
    }

    public VinResponse(VinCommand command) {
        super(command.getCommandPID());
        this.vin = "1HGCM82633A123456"; // Simulate a response based on the command
    }

    @Override
    public String getFormattedResult() {
        return "";
    }

    @Override
    public String getCalculatedResult() {
        return "";
    }

    public VinResponse(VinResponse other) {
        super(other);
        this.vin = other.vin;
    }

    public String getRawData() {
        ObdResponseBuilder builder = new ObdResponseBuilder();
        return builder.setHeader("48 6B 10")
                .setMode("09")
                .setPid("02")
                .setData(convertStringToHex(this.vin))
                .build();
    }

    public String getName() {
        return AvailableCommandNames.VIN.getValue();
    }

    public String convertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char ch : chars) {
            hex.append(Integer.toHexString(ch));
        }
        return hex.toString();
    }
}