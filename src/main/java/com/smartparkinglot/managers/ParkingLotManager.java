package com.smartparkinglot.managers;

import com.smartparkinglot.enums.TicketStatus;
import com.smartparkinglot.exceptions.ParkingFullException;
import com.smartparkinglot.models.ParkingLot;
import com.smartparkinglot.models.ParkingSpot;
import com.smartparkinglot.models.Ticket;
import com.smartparkinglot.models.Vehicle;
import com.smartparkinglot.strategies.FeeCalculationStrategy;
import com.smartparkinglot.strategies.SpotAllocationStrategy;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLotManager {

    private ParkingLot parkingLot;
    private SpotAllocationStrategy allocationStrategy;
    private FeeCalculationStrategy feeCalculationStrategy;
    
    // Thread-safe map to store active tickets
    private Map<String, Ticket> activeTickets;

    public ParkingLotManager(ParkingLot parkingLot, 
                             SpotAllocationStrategy allocationStrategy, 
                             FeeCalculationStrategy feeCalculationStrategy) {
        this.parkingLot = parkingLot;
        this.allocationStrategy = allocationStrategy;
        this.feeCalculationStrategy = feeCalculationStrategy;
        this.activeTickets = new ConcurrentHashMap<>();
    }

    /**
     * Handles the entry of a vehicle into the parking lot.
     */
    public Ticket checkIn(Vehicle vehicle) {
        ParkingSpot allocatedSpot = allocationStrategy.allocateSpot(vehicle, parkingLot);
        
        if (allocatedSpot == null) {
            throw new ParkingFullException("Parking lot is full for vehicle type: " + vehicle.getType());
        }

        // Generate a new ticket
        String ticketId = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(ticketId, vehicle, allocatedSpot);
        
        // Store in the active tickets registry
        activeTickets.put(ticketId, ticket);

        return ticket;
    }

    /**
     * Handles the exit of a vehicle and calculates the fee based on ticketId.
     */
    public Ticket checkOut(String ticketId) {
        Ticket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid ticket ID.");
        }
        return checkOut(ticket);
    }

    /**
     * Handles the exit of a vehicle and calculates the fee.
     */
    public Ticket checkOut(Ticket ticket) {
        if (ticket.getStatus() == TicketStatus.COMPLETED) {
            throw new IllegalArgumentException("Ticket is already processed.");
        }

        // Record the exit time
        ticket.setExitTime(LocalDateTime.now());
        
        // Calculate the fee
        double totalFee = feeCalculationStrategy.calculateFee(ticket);
        ticket.setTotalFee(totalFee);
        
        // Free up the allocated spot
        ParkingSpot spot = ticket.getAllocatedSpot();
        spot.removeVehicle();
        
        // Complete the transaction
        ticket.setStatus(TicketStatus.COMPLETED);
        
        // Remove from active tickets
        activeTickets.remove(ticket.getTicketId());

        return ticket;
    }
}
