package com.smartparkinglot.strategies;

import com.smartparkinglot.enums.ParkingSpotType;
import com.smartparkinglot.enums.SpotStatus;
import com.smartparkinglot.enums.VehicleType;
import com.smartparkinglot.models.ParkingFloor;
import com.smartparkinglot.models.ParkingLot;
import com.smartparkinglot.models.ParkingSpot;
import com.smartparkinglot.models.Vehicle;

public class NearestSpotAllocationStrategy implements SpotAllocationStrategy {

    @Override
    public ParkingSpot allocateSpot(Vehicle vehicle, ParkingLot parkingLot) {
        for (ParkingFloor floor : parkingLot.getParkingFloors()) {
            for (ParkingSpot spot : floor.getParkingSpots()) {
                if (isSpotSuitable(vehicle.getType(), spot.getType())) {
                    // Try to assign it directly; this is a synchronized, atomic operation
                    if (spot.assignVehicle(vehicle)) {
                        return spot;
                    }
                }
            }
        }
        return null; // No spot available
    }

    private boolean isSpotSuitable(VehicleType vehicleType, ParkingSpotType spotType) {
        switch (vehicleType) {
            case MOTORCYCLE:
                return spotType == ParkingSpotType.MOTORCYCLE || 
                       spotType == ParkingSpotType.COMPACT || 
                       spotType == ParkingSpotType.LARGE;
            case CAR:
                return spotType == ParkingSpotType.COMPACT || 
                       spotType == ParkingSpotType.LARGE;
            case BUS:
                return spotType == ParkingSpotType.LARGE;
            default:
                return false;
        }
    }
}
