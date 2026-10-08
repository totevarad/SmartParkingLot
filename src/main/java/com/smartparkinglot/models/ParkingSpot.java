package com.smartparkinglot.models;

import com.smartparkinglot.enums.ParkingSpotType;
import com.smartparkinglot.enums.SpotStatus;

public class ParkingSpot {
    private String id;
    private int floorNumber;
    private ParkingSpotType type;
    private SpotStatus status;
    private Vehicle currentVehicle;

    public ParkingSpot(String id, int floorNumber, ParkingSpotType type) {
        this.id = id;
        this.floorNumber = floorNumber;
        this.type = type;
        this.status = SpotStatus.AVAILABLE;
    }

    public synchronized boolean assignVehicle(Vehicle vehicle) {
        if (this.status == SpotStatus.AVAILABLE) {
            this.currentVehicle = vehicle;
            this.status = SpotStatus.OCCUPIED;
            return true;
        }
        return false;
    }

    public synchronized void removeVehicle() {
        this.currentVehicle = null;
        this.status = SpotStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ParkingSpotType getType() {
        return type;
    }

    public SpotStatus getStatus() {
        return status;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }
}
