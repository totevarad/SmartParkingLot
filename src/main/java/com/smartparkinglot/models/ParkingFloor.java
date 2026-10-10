package com.smartparkinglot.models;

import com.smartparkinglot.enums.SpotStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingFloor {
    private int floorNumber;
    private List<ParkingSpot> parkingSpots;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.parkingSpots = new ArrayList<>();
    }

    public void addParkingSpot(ParkingSpot spot) {
        this.parkingSpots.add(spot);
    }

    public List<ParkingSpot> getAvailableSpots() {
        return parkingSpots.stream()
            .filter(spot -> spot.getStatus() == SpotStatus.AVAILABLE)
            .collect(Collectors.toList());
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }
}
