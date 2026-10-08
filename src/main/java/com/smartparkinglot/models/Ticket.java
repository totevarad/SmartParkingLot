package com.smartparkinglot.models;

import com.smartparkinglot.enums.TicketStatus;
import java.time.LocalDateTime;

public class Ticket {
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSpot allocatedSpot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double totalFee;
    private TicketStatus status;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSpot allocatedSpot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.allocatedSpot = allocatedSpot;
        this.entryTime = LocalDateTime.now();
        this.status = TicketStatus.ACTIVE;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getAllocatedSpot() {
        return allocatedSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
