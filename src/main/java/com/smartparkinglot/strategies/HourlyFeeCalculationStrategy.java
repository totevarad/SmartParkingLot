package com.smartparkinglot.strategies;

import com.smartparkinglot.enums.VehicleType;
import com.smartparkinglot.models.Ticket;

import java.time.Duration;

public class HourlyFeeCalculationStrategy implements FeeCalculationStrategy {

    private static final double MOTORCYCLE_HOURLY_RATE = 10.0;
    private static final double CAR_HOURLY_RATE = 20.0;
    private static final double BUS_HOURLY_RATE = 30.0;

    @Override
    public double calculateFee(Ticket ticket) {
        if (ticket.getExitTime() == null) {
            throw new IllegalArgumentException("Exit time must be set to calculate the fee.");
        }

        Duration duration = Duration.between(ticket.getEntryTime(), ticket.getExitTime());
        long hours = duration.toHours();
        
        // Charge for the full hour if there's any fraction of an hour used
        if (duration.toMinutesPart() > 0 || duration.toSecondsPart() > 0 || duration.toMillisPart() > 0) {
            hours++;
        }
        
        // Minimum of 1 hour charge
        if (hours == 0) {
            hours = 1;
        }

        double rate = getRateForVehicle(ticket.getVehicle().getType());
        return hours * rate;
    }

    private double getRateForVehicle(VehicleType type) {
        switch (type) {
            case MOTORCYCLE:
                return MOTORCYCLE_HOURLY_RATE;
            case CAR:
                return CAR_HOURLY_RATE;
            case BUS:
                return BUS_HOURLY_RATE;
            default:
                throw new IllegalArgumentException("Unknown vehicle type");
        }
    }
}
