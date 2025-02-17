package org.example;

import java.io.IOException;
import java.util.*;


public class Main {


    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        Set<Dealer> dealerSet = new HashSet<Dealer>();  //create an empty HashSet to store unique Dealer obj
        JsonReader fileOperator = new JsonReader();


        System.out.println("Enter JSON file path: ");        // filepath: src/main/java/org/example/inventory.json
        String filePath = s.next();
        //Called the JSONParse() with filePath & dealerSet
        fileOperator.JSONParse(filePath, dealerSet);

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
                        fileOperator.JSONParse(filePath, dealerSet);

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


                            Vehicle newVehicle = fileOperator.checkType(type, manufacturer, model, id, acquisitionDate, price);
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
                    JsonWriter.exportJSON(dealerSet);
                    System.out.println("---Check you new JSON file---");
                    System.out.println(" ");
                    break;
                case 6:   //Exit
                    System.out.println("Exiting the system.");
                    JsonWriter.exportJSON(dealerSet);
                    System.exit(0);
                    break;

                default:
                    System.out.println("Not a valid option");

            }
        }
    }

    }
