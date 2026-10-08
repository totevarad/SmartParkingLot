package com.smartparkinglot.strategies;

import com.smartparkinglot.models.Ticket;

public interface FeeCalculationStrategy {
    double calculateFee(Ticket ticket);
}
