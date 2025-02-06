package org.example;

//Create abstract vehicle class
abstract public class Vehicle {
    protected String vehicleID;
    protected String manufacturer;
    protected String model;
    protected long acquisitionDate;
    protected double price;
    protected String type;

//Constructor
public Vehicle(String vehicleID, String manufacturer, String model, long acquisitionDate, double price, String type) {
    this.vehicleID = vehicleID;
    this.manufacturer = manufacturer;
    this.model = model;
    this.acquisitionDate = acquisitionDate;
    this.price = price;
    this.type = type;
}

// Getter methods to retrieve the values of instance variables/attributes
public String getVehicleID() { return vehicleID; }
public String getManufacturer() { return manufacturer; }
public String getModel() { return model; }
public long getAcquisitionDate() { return acquisitionDate; }
public double getPrice() { return price; }
public String getType() { return type; }
}

//Create SUV subclass, which extends the Vehicle class
class SUV extends Vehicle {
//Constructor
public SUV(String vehicleID, String manufacturer, String model, long acquisitionDate, double price) {
    super(vehicleID, manufacturer, model, acquisitionDate, price, "suv");
    }
}

//Create Sedan subclass, which extends the Vehicle class
class Sedan extends Vehicle {
//Constructor
public Sedan(String vehicleID, String manufacturer, String model,long  acquisitionDate, double price) {
    super(vehicleID, manufacturer, model, acquisitionDate, price, "sedan");
    }
}

//Create Pickup subclass, which extends the Vehicle class
class Pickup extends Vehicle {
//Constructor
public Pickup(String vehicleID, String manufacturer, String model, long acquisitionDate, double price) {
    super(vehicleID, manufacturer, model, acquisitionDate, price, "pickup");
    }
}

//Create SportsCar subclass, which extends the Vehicle class
class SportsCar extends Vehicle {
//Constructor
public SportsCar(String vehicleID, String manufacturer, String model, long acquisitionDate, double price) {
    super(vehicleID, manufacturer, model, acquisitionDate, price, "sports car");
    }
}


