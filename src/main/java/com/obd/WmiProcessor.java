package com.obd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.obd.agnx.vin.WmiData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WmiProcessor {

    private static final Set<String> BRANDS = new HashSet<>(Arrays.asList(
            "Toyota", "Ford", "Honda", "Nissan", "Chevrolet", "Volkswagen", "Hyundai", "Kia",
            "Mazda", "Subaru", "BMW", "Mercedes-Benz", "Audi", "Tesla", "Fiat", "Renault",
            "Peugeot", "Citroen", "Opel", "Skoda", "Mitsubishi", "Suzuki", "Ferrari", "Jaguar",
            "Land Rover", "Volvo", "Porsche", "Saab", "Alfa Romeo", "Mini", "Chrysler", "Dodge",
            "Jeep", "General Motors (GM)", "Buick", "Cadillac", "GMC", "Great Wall", "Geely",
            "BYD", "Bentley", "Aston Martin", "Lotus", "Infiniti", "Acura", "Lexus", "Isuzu",
            "Dacia", "SEAT", "Daihatsu"
    ));

    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = WmiProcessor.class.getClassLoader().getResourceAsStream("wmi.json");
            if (inputStream == null) {
                throw new IOException("Resource not found: wmi.json");
            }

            List<WmiData> wmiDataList = mapper.readValue(inputStream, mapper.getTypeFactory().constructCollectionType(List.class, WmiData.class));
            ArrayNode newWmiArray = mapper.createArrayNode();

            for (WmiData wmiData : wmiDataList) {
                String manufacturer = wmiData.getManufacturer();
                for (String brand : BRANDS) {
                    if (manufacturer.contains(brand)) {
                        ObjectNode newWmiObject = mapper.createObjectNode();
                        newWmiObject.put("WMI", wmiData.getWmi());
                        newWmiObject.put("Manufacturer", brand);
                        newWmiArray.add(newWmiObject);
                        break;
                    }
                }
            }

            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("new-wmi.json"), newWmiArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}