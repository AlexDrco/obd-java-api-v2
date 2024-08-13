package com.obd;

import com.obd.agnx.vin.VinDecoder;

public class TestingMain {
    public static void main(String[] args) {
        VinDecoder vinDecoder = new VinDecoder("4T3DWRFV1RU113150");
        System.out.println("Year: " + vinDecoder.getYear());
        System.out.println("Make: " + vinDecoder.getMake());
        System.out.println("Model: " + vinDecoder.getModel());
    }
}