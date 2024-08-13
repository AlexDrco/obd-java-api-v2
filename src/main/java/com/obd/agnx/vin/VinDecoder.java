package com.obd.agnx.vin;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class VinDecoder {

    private String vin;
    private static Map<String, String> wmiMap = new HashMap<>();

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = VinDecoder.class.getClassLoader().getResourceAsStream("new-wmi.json");
            if (inputStream != null) {
                List<WmiData> wmiDataList = mapper.readValue(inputStream, mapper.getTypeFactory().constructCollectionType(List.class, WmiData.class));
                for (WmiData wmiData : wmiDataList) {
                    wmiMap.put(wmiData.getWmi(), wmiData.getManufacturer());
                }
            } else {
                throw new IOException("Resource not found: new-wmi.json");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VinDecoder(String vin) {
        this.vin = vin;
    }

    public String getYear() {
        char yearCode = vin.charAt(9);
        List<String> possibleYears = decodeYear(yearCode);
        return selectCorrectYear(possibleYears);
    }

    public String getMake() {
        String wmi = vin.substring(0, 3);
        return decodeMake(wmi);
    }

    public String getModel() {
        String vds = vin.substring(3, 8);
        return decodeModel(vds);
    }

    private List<String> decodeYear(char yearCode) {
        List<String> years = new ArrayList<>();
        switch (yearCode) {
            case 'A': years.add("1980"); years.add("2010"); break;
            case 'B': years.add("1981"); years.add("2011"); break;
            case 'C': years.add("1982"); years.add("2012"); break;
            case 'D': years.add("1983"); years.add("2013"); break;
            case 'E': years.add("1984"); years.add("2014"); break;
            case 'F': years.add("1985"); years.add("2015"); break;
            case 'G': years.add("1986"); years.add("2016"); break;
            case 'H': years.add("1987"); years.add("2017"); break;
            case 'J': years.add("1988"); years.add("2018"); break;
            case 'K': years.add("1989"); years.add("2019"); break;
            case 'L': years.add("1990"); years.add("2020"); break;
            case 'M': years.add("1991"); years.add("2021"); break;
            case 'N': years.add("1992"); years.add("2022"); break;
            case 'P': years.add("1993"); years.add("2023"); break;
            case 'R': years.add("1994"); years.add("2024"); break;
            case 'S': years.add("1995"); years.add("2025"); break;
            case 'T': years.add("1996"); years.add("2026"); break;
            case 'V': years.add("1997"); years.add("2027"); break;
            case 'W': years.add("1998"); years.add("2028"); break;
            case 'X': years.add("1999"); years.add("2029"); break;
            case 'Y': years.add("2000"); years.add("2030"); break;
            case '1': years.add("2001"); break;
            case '2': years.add("2002"); break;
            case '3': years.add("2003"); break;
            case '4': years.add("2004"); break;
            case '5': years.add("2005"); break;
            case '6': years.add("2006"); break;
            case '7': years.add("2007"); break;
            case '8': years.add("2008"); break;
            case '9': years.add("2009"); break;
            default: years.add("Unknown Year"); break;
        }
        return years;
    }

    private String selectCorrectYear(List<String> possibleYears) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (String year : possibleYears) {
            int yearInt = Integer.parseInt(year);
            if (yearInt <= currentYear && yearInt > currentYear - 30) {
                return year;
            }
        }
        return possibleYears.get(0); // Default to the first year if no match found
    }

    private String decodeMake(String wmi) {
        return wmiMap.getOrDefault(wmi, "Unknown Make");
    }

    private String decodeModel(String vds) {
        switch (vds) {
            case "FF68S": return "Cherokee";
            // Add all other VDS codes here
            default: return "Unknown Model";
        }
    }
}