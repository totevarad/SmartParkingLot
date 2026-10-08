# Smart Parking Lot System - Implementation Plan

This document outlines the step-by-step implementation plan for the Smart Parking Lot backend system, based on the requirements defined in `problemStatement.md`.

## Phase 1: Define the Data Models (Core Entities)
**Goal:** Establish the foundational class structures and enumerations.
- [ ] Define **Enums**:
  - `VehicleType`: MOTORCYCLE, CAR, BUS
  - `ParkingSpotType`: MOTORCYCLE, COMPACT, LARGE
  - `SpotStatus`: AVAILABLE, OCCUPIED
- [ ] Create **Vehicle** Model:
  - Properties: `licensePlate` (String), `type` (VehicleType)
- [ ] Create **ParkingSpot** Model:
  - Properties: `id` (String), `floorNumber` (int), `type` (ParkingSpotType), `status` (SpotStatus)
  - Methods: `assignVehicle(Vehicle)`, `removeVehicle()`
- [ ] Create **Ticket** Model:
  - Properties: `ticketId` (String), `vehicle` (Vehicle), `allocatedSpot` (ParkingSpot), `entryTime` (LocalDateTime), `exitTime` (LocalDateTime), `totalFee` (double), `status` (ACTIVE, COMPLETED)
- [ ] Create **ParkingFloor** Model:
  - Properties: `floorNumber` (int), list of `ParkingSpot`s
  - Methods: `addParkingSpot()`, `getAvailableSpots()`
- [ ] Create **ParkingLot** Singleton/Manager:
  - Properties: list of `ParkingFloor`s, `name`, `address`
  - Purpose: Manages the entire hierarchy of floors and spots.

## Phase 2: Implement Spot Allocation Logic
**Goal:** Develop the algorithm to assign vehicles to the appropriate parking spots.
- [ ] Implement a `SpotAllocationStrategy` interface.
- [ ] Create a concrete strategy (e.g., `NearestSpotAllocationStrategy`):
  - Logic: Iterate through floors (from ground up) and find the first available spot that matches the vehicle's size.
  - Mapping logic: 
    - Motorcycle -> Motorcycle Spot, Compact Spot, Large Spot
    - Car -> Compact Spot, Large Spot
    - Bus -> Large Spot (or multiple consecutive spots if needed)

## Phase 3: Implement Fee Calculation Strategy
**Goal:** Develop flexible and extensible pricing logic.
- [ ] Implement a `FeeCalculationStrategy` interface.
- [ ] Create concrete pricing strategies (e.g., `HourlyFeeCalculationStrategy`):
  - Base rates per hour based on `VehicleType`.
  - Logic to calculate duration (difference between exit time and entry time) and multiply by the rate.

## Phase 4: Implement Check-In and Check-Out Workflows
**Goal:** Build the primary APIs/Methods to interact with the parking lot.
- [ ] Implement **Check-In Flow** in `ParkingLotManager`:
  - Input: `Vehicle`
  - Process: 
    1. Invoke allocation strategy to find a spot.
    2. If no spot is available, throw `ParkingFullException`.
    3. Update spot status to OCCUPIED.
    4. Generate and return a `Ticket` with current timestamp.
- [ ] Implement **Check-Out Flow** in `ParkingLotManager`:
  - Input: `Ticket` (or ticketId)
  - Process:
    1. Set exit time on the ticket.
    2. Invoke fee calculation strategy to determine the total fee.
    3. Retrieve the `ParkingSpot` linked to the ticket and set status to AVAILABLE.
    4. Update ticket status to COMPLETED and return the updated ticket/receipt.

## Phase 5: Concurrency and Thread Safety
**Goal:** Ensure the system handles concurrent operations safely (especially during allocation and check-out).
- [ ] Identify critical sections (e.g., checking spot availability and marking it as occupied).
- [ ] Apply synchronization techniques (e.g., `synchronized` blocks, `ReentrantLock`, or `ConcurrentHashMap` for active tickets and spot registries in Java) to prevent race conditions like double-booking a single spot.

## Phase 6: Testing and Validation
**Goal:** Verify the robustness and correctness of the system.
- [ ] Write unit tests for data models and state transitions.
- [ ] Write unit tests for `SpotAllocationStrategy` with various vehicle types and spot availability scenarios.
- [ ] Write unit tests for `FeeCalculationStrategy` ensuring correct duration and fee math.
- [ ] Write integration tests for the full Check-In and Check-Out flow.
- [ ] Write multi-threaded tests to simulate concurrent vehicle entries/exits to validate thread safety.
