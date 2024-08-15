package com.obd;

import com.obd.agnx.response.common.*;
import com.obd.agnx.vin.VinDecoder;
import com.obd.pires.commands.control.DistanceSinceCCCommand;

public class TestingMain {
    public static void main(String[] args) {
        VinDecoder vinDecoder = new VinDecoder("4T3DWRFV1RU113150");
        System.out.println("Year: " + vinDecoder.getYear());
        System.out.println("Make: " + vinDecoder.getMake());
        System.out.println("Model: " + vinDecoder.getModel());

        VinResponse vinResponse = new VinResponse();
        String s = vinResponse.stringToHex("4T3DWRFV1RU113150");
        WarmUpCyclesSinceDtcClrResponse warmUpCyclesSinceDtcClrResponse = new WarmUpCyclesSinceDtcClrResponse();
        String s1 = warmUpCyclesSinceDtcClrResponse.stringToHex("1");
        System.out.println(s1);
        TroubleCodesResponse troubleCodesResponse = new TroubleCodesResponse();
        String s2 = troubleCodesResponse.stringToHex("P0300,P0420", "ISO9141-2");
        System.out.println(s2);
        RuntimeResponse runtimeResponse = new RuntimeResponse();
        String s3 = runtimeResponse.stringToHex("65535");
        System.out.println(s3);
        RPMResponse rpmResponse = new RPMResponse();
        String s4 = rpmResponse.stringToHex("891");
        System.out.println(s4);
        FuelLevelResponse fuelLevelResponse = new FuelLevelResponse();
        String s5 = fuelLevelResponse.stringToHex("39.2");
        System.out.println(s5);
        EvapVpResponse evapVpResponse = new EvapVpResponse();
        String s6 = evapVpResponse.stringToHex("-8.19");
        System.out.println(s6);
        EngineCoolantTemperatureResponse engineCoolantTemperatureResponse = new EngineCoolantTemperatureResponse();
        String s7 = engineCoolantTemperatureResponse.stringToHex("50");
        System.out.println(s7);
        DtcNumberResponse dtcNumberResponse = new DtcNumberResponse();
        String s8 = dtcNumberResponse.stringToHex("MIL is ON 2 codes");
        System.out.println(s8);
        DistanceSinceCCResponse distanceSinceCCResponse = new DistanceSinceCCResponse();
        String s9 = distanceSinceCCResponse.stringToHex("767m");
        System.out.println(s9);
        DistanceMILOnResponse distanceMILOnResponse = new DistanceMILOnResponse();
        String s10 = distanceMILOnResponse.stringToHex("16km");
        System.out.println(s10);
        TroubleCodesResponse troubleCodesResponse1 = new TroubleCodesResponse();
        String s11 = troubleCodesResponse1.stringToHex("P0300,P0420");
        System.out.println(s11);
    }
}