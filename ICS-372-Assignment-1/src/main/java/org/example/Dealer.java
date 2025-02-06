package org.example;
import java.util.*;

public class Dealer {
        private String dealerID;
        private boolean isAcquisitionEnabled;
        private List<Vehicle> vehicles;

        public Dealer(String dealerID) {
            this.dealerID = dealerID;
            this.vehicles = new ArrayList<>();
        }

        public void enableAcquisition() {
            this.isAcquisitionEnabled = true;
        }

        public void disableAcquisition() {
            this.isAcquisitionEnabled = false;
        }

        public boolean addVehicle(Vehicle vehicle) {
            if (isAcquisitionEnabled) {
                vehicles.add(vehicle);
                return true;
            }
            return false;
        }

        public String getDealerID() {
            return dealerID;
        }

        public List<Vehicle> getVehicles() {
            return vehicles;
        }
}





