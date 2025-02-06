package org.example;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Main {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        Set<Dealer> dealerSet = new HashSet<Dealer>();

        // asking user for file path
        System.out.println("Enter JSON file path: ");
        String filePath = s.next();

        JSONParse(filePath, dealerSet);


    }

    static void JSONParse(String path, Set<Dealer> dealerSet) throws IOException {
        JSONParser parse = new JSONParser();
        try {
            FileReader read = new FileReader(path);
            JSONObject mainJsonObject = (JSONObject) parse.parse(read);

            JSONArray carInventory = (JSONArray) mainJsonObject.get("car_inventory");

            for (Object vehicleObj : carInventory) {
                JSONObject vehicle = (JSONObject) vehicleObj;

                String type = (String) vehicle.get("vehicle_type");
                String dealershipId = (String) vehicle.get("dealership_id");
                String manufacturer = (String) vehicle.get("vehicle_manufacturer");
                String model = (String) vehicle.get("vehicle_model");
                String id = (String) vehicle.get("vehicle_id");
                Long price = (Long) vehicle.get("price");
                Long acquisitionDate = (Long) vehicle.get("acquisition_date");

                Vehicle newVehicle;
                switch (type.toLowerCase()) {
                    case "suv":
                        newVehicle = new SUV(id, manufacturer, model, acquisitionDate, price);
                        break;
                    case "sedan":
                        newVehicle = new Sedan(id, manufacturer, model, acquisitionDate, price);
                        break;
                    case "pickup":
                        newVehicle = new Pickup(id, manufacturer, model, acquisitionDate, price);
                        break;
                    case "sports car":
                        newVehicle = new SportsCar(id, manufacturer, model, acquisitionDate, price);
                        break;
                    default:
                        System.out.println("Unknown vehicle type: " + type);
                        continue;
                }

                // checking if dealer already has an instance
                boolean found = false;
                for (Dealer d : dealerSet) {
                    if (d.getDealerID().equals(dealershipId)) {
                        d.enableAcquisition();
                        d.addVehicle(newVehicle);
                        found = true;
                        break;
                    }

                }
                // if dealer does not have instance, create a new instance
                if (!found) {
                    Dealer d = new Dealer(dealershipId);
                    d.enableAcquisition();
                    d.addVehicle(newVehicle);
                    dealerSet.add(d);
                }

            }


        } catch (ParseException e) {
            System.out.println("Not Opening");
        }

        // printing  each dealer's vehicles
        for (Dealer d : dealerSet) {
            System.out.println("-----------------");
            System.out.println("Dealer " + d.getDealerID());
            System.out.println("-----------------");
            for (Vehicle v : d.getVehicles()) {
                System.out.println("Vehicle ID: " + v.getVehicleID());

            }
        }
    }
}