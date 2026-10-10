package com.smartparkinglot.models;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private String name;
    private String address;
    private List<ParkingFloor> parkingFloors;

    private static ParkingLot instance = null;

    private ParkingLot(String name, String address) {
        this.name = name;
        this.address = address;
        this.parkingFloors = new ArrayList<>();
    }

    public static synchronized ParkingLot getInstance(String name, String address) {
        if (instance == null) {
            instance = new ParkingLot(name, address);
        }
        return instance;
    }
    
    public static ParkingLot getInstance() {
        return instance; // Could be null if not initialized first
    }

    public void addParkingFloor(ParkingFloor floor) {
        this.parkingFloors.add(floor);
    }

    public List<ParkingFloor> getParkingFloors() {
        return parkingFloors;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
