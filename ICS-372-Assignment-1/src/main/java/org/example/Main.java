package org.example;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {


    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        Set<Dealer> dealerSet = new HashSet<Dealer>();  //create an empty HashSet to store unique Dealer obj

        System.out.println("Enter JSON file path: ");        // filepath: src/main/java/org/example/inventory.json
        String filePath = s.next();
        //Called the JSONParse() with filePath & dealerSet
        JSONParse(filePath, dealerSet);

        int num =0;
        while (true) {
            System.out.println("1: Check Dealers and their Vehicles: ");
            System.out.println("2: Enable dealer");
            System.out.println("3: Disable dealer");
            System.out.println("4: Add a new vehicle in desired dealer: ");
            System.out.println("5: Get the updated JSON file.");
            System.out.println("6: Exit and Export JSON file: ");
            System.out.println("Please Choose your desired option : ");
            num = s.nextInt();    //Read user input


            switch (num) {
                case 1: // printing  each dealer and  it's vehicle record

                    for (Dealer d : dealerSet) {
                        System.out.println("-----------------");
                        System.out.println("Dealer " + d.getDealerID());
                        System.out.println("-----------------");
                        for (Vehicle v : d.getVehicles()) {
                            System.out.println("Vehicle ID: " + v.getVehicleID());
                            System.out.println("Model:" + v.getModel());
                        }
                    }
                    System.out.println(" ");
                    break;
                case 2:   //Enable a dealer
                    System.out.println("Which dealer you want to enable: ");
                    String dealerID = s.next();
                    boolean isenable = false;
                    for (Dealer d : dealerSet) {    // Loop through existing dealers
                        if (d.getDealerID().equals(dealerID)) {
                            d.enableAcquisition();
                            System.out.println("Dealer " + dealerID + " is now enable.");
                            isenable = true;
                            break;
                        }
                    }
                    if (!isenable) {
                        System.out.println("This Dealer ID does not exist.");
                    }
                    System.out.println(" ");
                    break;

                case 3:  //Disable a dealer
                    System.out.println("Which dealer you want to disable: ");
                    String dealerID2 = s.next();
                    boolean isDisable = false;
                    for (Dealer d : dealerSet) {
                        if (d.getDealerID().equals(dealerID2)) {
                            d.disableAcquisition();
                            System.out.println("Dealer " + dealerID2 + " is now disable.");
                            isDisable = true;
                            break;
                        }
                    }

                    if (!isDisable) {
                        System.out.println("This Dealer Id does not exist.");
                    }
                    System.out.println(" ");
                    break;
                case 4:   //Add a new vehicle to a dealer
                    System.out.println("Add vehicle via JSON or Manual (enter 'manual' or 'json') : ");
                    String inputMethod = s.next();

                    if (inputMethod.equalsIgnoreCase("JSON")) {
                        System.out.println("Enter JSON file path: ");
                        filePath = s.next();
                        JSONParse(filePath, dealerSet);

                    } else if (inputMethod.equalsIgnoreCase("manual")) {
                        // Manual input case
                        System.out.println("Enter Dealer ID to add the vehicle to: ");
                        dealerID = s.next();
                        Dealer selectedDealer = null;

                        for (Dealer d : dealerSet) {
                            if (d.getDealerID().equals(dealerID)) {
                                selectedDealer = d;
                                break;
                            }
                        }

                        if (selectedDealer == null) {
                            System.out.println("Dealer ID not found.");
                            break;
                        }

                        if (!selectedDealer.getIsAcquisitionEnabled()) {
                            System.out.println("Dealer Disabled");
                        } else {
                            System.out.println("Enter Vehicle ID: ");
                            String id = s.next();

                            System.out.println("Enter Manufacturer: ");
                            String manufacturer = s.next();

                            s.nextLine();

                            System.out.println("Enter Model: ");
                            String model = s.nextLine();

                            System.out.println("Enter Acquisition Date (as long value): ");
                            long acquisitionDate = s.nextLong();

                            System.out.println("Enter Price: ");
                            double price = s.nextDouble();

                            s.nextLine();

                            System.out.println("Enter Vehicle Type (SUV, Sedan, Pickup, Sports Car): ");
                            String type = s.nextLine();


                            Vehicle newVehicle = checkType(type, manufacturer, model, id, acquisitionDate, price);
                            boolean added = selectedDealer.addVehicle(newVehicle);
                            if (added) {
                                System.out.println("Vehicle added successfully to Dealer " + dealerID);
                            }
                        }


                    } else {
                        System.out.println("Invalid option. Please enter 'manual' or 'json'.");
                    }

                    System.out.println(" ");
                    break;

                case 5:  //Get the updated JSON File
                    exportJSON(dealerSet);
                    System.out.println("---Check you new JSON file---");
                    System.out.println(" ");
                    break;
                case 6:   //Exit
                    System.out.println("Exiting the system.");
                    exportJSON(dealerSet);
                    System.exit(0);
                    break;

                default:
                    System.out.println("Not a valid option");

            }
        }
    }
    //JSONParse() reads the JSON file, extracts vehicle data, categorizes vehicles, and organizes them under dealerships.
    static void JSONParse(String path, Set<Dealer> dealerSet) throws IOException {
        JSONParser parser = new JSONParser();     //JSONParser obj(translator): to parser(convert) JSON-formatted data
        try {
            FileReader read = new FileReader(path);    //Opens the JSON file at path and prepares for reading
            JSONObject mainJsonObject = (JSONObject) parser.parse(read);    //JSONParser reads content from FileReader(read) as a stream of characters|
                                                                            // parses the JSON content| Converts it into JSONObject that JAVA can work with

            JSONArray carInventory = (JSONArray) mainJsonObject.get("car_inventory");   //retrieves the value associated with the "car_inventory" key & cast it to JSONArray

            //Iterate over a JSONArray (carInventory), extracts data for each vehicle
            //and creates a corresponding Vehicle object based on its type (SUV, Sedan, Pickup, or SportsCar).
            for (Object vehicleObj : carInventory) {
                 JSONObject vehicle = (JSONObject) vehicleObj;

                //Extract values using .get("key") from vehicle JSONObject
                String type = (String) vehicle.get("vehicle_type");
                String dealershipId = (String) vehicle.get("dealership_id");
                String manufacturer = (String) vehicle.get("vehicle_manufacturer");
                String model = (String) vehicle.get("vehicle_model");
                String id = (String) vehicle.get("vehicle_id");
                Long price = (Long) vehicle.get("price");
                Long acquisitionDate = (Long) vehicle.get("acquisition_date");

                //Create a correct type of vehicle Obj


                Vehicle newVehicle = checkType( type,  manufacturer,  model,  id,  acquisitionDate,  price);
                // checking if dealer already has an instance
                boolean found = false;
                for (Dealer d : dealerSet) {    // Loop through existing dealers
                    if (d.getDealerID().equals(dealershipId)) {     // Check if the dealer exists
                        d.addVehicle(newVehicle);
                        found = true;
                        break;
                    }

                }
                // if dealer does not have instance, create a new instance
                if (!found) {
                    Dealer d = new Dealer(dealershipId);    //If the dealer is not found, create a new one and add it to dealerSet
                    d.addVehicle(newVehicle);
                    dealerSet.add(d);

                }

            }


        } catch (ParseException e) {
            System.out.println("Not Opening");
        }


    }
    static Vehicle checkType(String type, String manufacturer, String model, String id, long acquisitionDate, double price ){
        Vehicle newVehicle = null;
        switch (type.toLowerCase()){
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
                break;
        }
        return newVehicle;
    }

    static void exportJSON(Set<Dealer> dealerSet) throws IOException {
        ObjectMapper objMap = new ObjectMapper();               //Used to convert JAVA obj into JSON
        objMap.enable(SerializationFeature.INDENT_OUTPUT);         //makes output JSON more readable
        Map<String, List<Map<String, Object>>> outerList = new LinkedHashMap<>();     //outerList:Map-> key="Car Inventory", value= List of vehicles (inner List)
                                                                                //Stores the entire inventory under a key ("Car Inventory"), making the JSON structured.
        List<Map<String, Object>> innerList = new ArrayList<>();             //innerList: list of multiple vehicle records(For Each Vehicle-> Vehicle's attributed stored in a Map(key-value pairs)

        for (Dealer d : dealerSet) {   //loops through all dealers in dealerSet
            for (Vehicle v : d.getVehicles()) {    //loops through all vehicles owned by that dealer
                Map<String, Object> vehicleData = new LinkedHashMap<>();     //Stores each  attribute of a vehicle as a Map(key-value pairs)
                vehicleData.put("dealership_id", d.getDealerID());    // HashMap.put("key", value) | assuming getDealerID() exists
                vehicleData.put("vehicle_type", v.getType());
                vehicleData.put("vehicle_manufacturer", v.getManufacturer());
                vehicleData.put("vehicle_model", v.getModel());
                vehicleData.put("vehicle_id", v.getVehicleID());       // Assuming getId() exists
                vehicleData.put("price", v.getPrice());
                vehicleData.put("acquisition_date", v.getAcquisitionDate());

                innerList.add(vehicleData);     //add the  vehicle Obj to innerList (list)
            }
        }
        innerList.sort(Comparator.comparing(m ->(String)m.get("dealership_id")));   //sort by dealership_id to group similar dealerships together
        outerList.put("Car Inventory", innerList);
        objMap.writerWithDefaultPrettyPrinter().writeValue(new File("Dealers_Vehicles.json"), outerList);    //using: writeValue( File resultFile, Obj value) from Jackson's ObjectMapper class

    }
}