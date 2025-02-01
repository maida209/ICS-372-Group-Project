package org.example;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException{

        String path = "src/main/java/org/example/inventory.json";

        JSONParser parse = new JSONParser();
        try{
            FileReader read = new FileReader(path);
            JSONObject mainJsonObject = (JSONObject) parse.parse(read);

            JSONArray carInventory = (JSONArray) mainJsonObject.get("car_inventory");

            for(Object vehicleObj : carInventory){
                JSONObject vehicle = (JSONObject) vehicleObj;

                String type = (String) vehicle.get("vehicle_type");
                String dealershipId = (String) vehicle.get("dealership_id");
                String manufacturer = (String) vehicle.get("vehicle_manufacturer");
                String model = (String) vehicle.get("vehicle_model");
                String id = (String) vehicle.get("vehicle_id");
                Long price = (Long) vehicle.get("price");

                System.out.println("Vehicle Type: " + type);
                System.out.println("Manufacturer: " + manufacturer);
                System.out.println("Model: " + model);
                System.out.println("Price: $" + price);
                System.out.println("Dealer ID: "+ dealershipId);
                System.out.println("ID: " + id);
                System.out.println("----------------------");





            }

        }catch (ParseException e){
            System.out.println("Not Opening");
        }



    }
}