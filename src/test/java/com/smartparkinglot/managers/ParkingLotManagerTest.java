package com.smartparkinglot.managers;

import com.smartparkinglot.enums.ParkingSpotType;
import com.smartparkinglot.enums.VehicleType;
import com.smartparkinglot.exceptions.ParkingFullException;
import com.smartparkinglot.models.ParkingFloor;
import com.smartparkinglot.models.ParkingLot;
import com.smartparkinglot.models.ParkingSpot;
import com.smartparkinglot.models.Ticket;
import com.smartparkinglot.models.Vehicle;
import com.smartparkinglot.strategies.HourlyFeeCalculationStrategy;
import com.smartparkinglot.strategies.NearestSpotAllocationStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotManagerTest {

    private ParkingLotManager manager;

    @BeforeEach
    public void setup() {
        ParkingLot parkingLot = ParkingLot.getInstance("Test Lot", "Test Address");
        parkingLot.getParkingFloors().clear(); // Reset singleton state between tests
        
        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addParkingSpot(new ParkingSpot("S1", 1, ParkingSpotType.MOTORCYCLE));
        floor1.addParkingSpot(new ParkingSpot("S2", 1, ParkingSpotType.COMPACT));
        floor1.addParkingSpot(new ParkingSpot("S3", 1, ParkingSpotType.LARGE));
        
        parkingLot.addParkingFloor(floor1);

        manager = new ParkingLotManager(
            parkingLot, 
            new NearestSpotAllocationStrategy(), 
            new HourlyFeeCalculationStrategy()
        );
    }

    @Test
    public void testCheckInSuccess() {
        Vehicle car = new Vehicle("CAR-1", VehicleType.CAR);
        Ticket ticket = manager.checkIn(car);
        
        assertNotNull(ticket);
        assertNotNull(ticket.getTicketId());
        assertEquals(car, ticket.getVehicle());
        assertEquals("S2", ticket.getAllocatedSpot().getId()); // S2 is the first available COMPACT spot
    }

    @Test
    public void testCheckInParkingFull() {
        Vehicle bus1 = new Vehicle("BUS-1", VehicleType.BUS);
        Vehicle bus2 = new Vehicle("BUS-2", VehicleType.BUS);
        
        // First bus takes the only large spot (S3)
        manager.checkIn(bus1);
        
        // Second bus should fail because there are no LARGE spots left
        assertThrows(ParkingFullException.class, () -> manager.checkIn(bus2));
    }

    @Test
    public void testCheckOutSuccess() {
        Vehicle car = new Vehicle("CAR-1", VehicleType.CAR);
        Ticket ticket = manager.checkIn(car);
        
        Ticket processedTicket = manager.checkOut(ticket.getTicketId());
        
        assertNotNull(processedTicket.getExitTime());
        assertTrue(processedTicket.getTotalFee() > 0);
        assertEquals("AVAILABLE", processedTicket.getAllocatedSpot().getStatus().name());
    }
}
