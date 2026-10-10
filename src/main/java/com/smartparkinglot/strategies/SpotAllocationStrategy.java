package com.smartparkinglot.strategies;

import com.smartparkinglot.models.ParkingSpot;
import com.smartparkinglot.models.Vehicle;
import com.smartparkinglot.models.ParkingLot;

public interface SpotAllocationStrategy {
    ParkingSpot allocateSpot(Vehicle vehicle, ParkingLot parkingLot);
}
