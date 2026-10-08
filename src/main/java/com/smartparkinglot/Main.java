package com.smartparkinglot;

import com.smartparkinglot.enums.ParkingSpotType;
import com.smartparkinglot.enums.VehicleType;
import com.smartparkinglot.managers.ParkingLotManager;
import com.smartparkinglot.models.ParkingFloor;
import com.smartparkinglot.models.ParkingLot;
import com.smartparkinglot.models.ParkingSpot;
import com.smartparkinglot.models.Ticket;
import com.smartparkinglot.models.Vehicle;
import com.smartparkinglot.strategies.HourlyFeeCalculationStrategy;
import com.smartparkinglot.strategies.NearestSpotAllocationStrategy;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing Smart Parking Lot...");

        ParkingLot parkingLot = ParkingLot.getInstance("Downtown Parking", "123 Main St");
        
        // Add a floor with some spots
        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addParkingSpot(new ParkingSpot("1A", 1, ParkingSpotType.COMPACT));
        floor1.addParkingSpot(new ParkingSpot("1B", 1, ParkingSpotType.LARGE));
        floor1.addParkingSpot(new ParkingSpot("1C", 1, ParkingSpotType.MOTORCYCLE));
        parkingLot.addParkingFloor(floor1);

        ParkingLotManager manager = new ParkingLotManager(
                parkingLot,
                new NearestSpotAllocationStrategy(),
                new HourlyFeeCalculationStrategy()
        );

        Vehicle myCar = new Vehicle("XYZ-1234", VehicleType.CAR);
        System.out.println("\nChecking in Car: " + myCar.getLicensePlate());
        
        try {
            Ticket ticket = manager.checkIn(myCar);
            System.out.println("Checked in successfully!");
            System.out.println("Ticket ID: " + ticket.getTicketId());
            System.out.println("Allocated Spot: " + ticket.getAllocatedSpot().getId());

            System.out.println("\nChecking out Car: " + myCar.getLicensePlate());
            Ticket completedTicket = manager.checkOut(ticket.getTicketId());
            System.out.println("Checked out successfully!");
            System.out.println("Total Fee: $" + completedTicket.getTotalFee());
            
        } catch (Exception e) {
            System.err.println("Error during parking operations: " + e.getMessage());
        }
    }
}
