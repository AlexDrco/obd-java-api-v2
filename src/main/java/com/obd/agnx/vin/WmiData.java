package com.obd.agnx.vin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WmiData {
    @JsonProperty("WMI")
    private String wmi;

    @JsonProperty("Manufacturer")
    private String manufacturer;

    public String getWmi() {
        return wmi;
    }

    public void setWmi(String wmi) {
        this.wmi = wmi;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}