package org.example;

import java.time.LocalDate;

//Create abstract vehicle class
abstract public class Vehicle {
    protected String vehicleID;
    protected String manufacturer;
    protected String model;
    protected long acquisitionDate;
    protected double price;
    protected String type;

    public Vehicle(String vehicleID, String manufacturer, String model, long acquisitionDate, double price, String type) {
        this.vehicleID = vehicleID;
        this.manufacturer = manufacturer;
        this.model = model;
        this.acquisitionDate = acquisitionDate;
        this.price = price;
        this.type = type;
    }

    public String getVehicleID() { return vehicleID; }
    public String getManufacturer() { return manufacturer; }
    public String getModel() { return model; }
    public long getAcquisitionDate() { return acquisitionDate; }
    public double getPrice() { return price; }
    public String getType() { return type; }
}




class SUV extends Vehicle {
    public SUV(String vehicleID, String manufacturer, String model, long acquisitionDate, double price) {
        super(vehicleID, manufacturer, model, acquisitionDate, price, "suv");
    }
}

class Sedan extends Vehicle {
    public Sedan(String vehicleID, String manufacturer, String model,long  acquisitionDate, double price) {
        super(vehicleID, manufacturer, model, acquisitionDate, price, "sedan");
    }
}

class Pickup extends Vehicle {
    public Pickup(String vehicleID, String manufacturer, String model, long acquisitionDate, double price) {
        super(vehicleID, manufacturer, model, acquisitionDate, price, "pickup");
    }
}

class SportsCar extends Vehicle {
    public SportsCar(String vehicleID, String manufacturer, String model, long acquisitionDate, double price) {
        super(vehicleID, manufacturer, model, acquisitionDate, price, "sports car");
    }
}


