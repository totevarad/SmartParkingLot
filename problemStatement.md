# Smart Parking Lot System - Low-Level Design (LLD)

## 1. Objective
Design the low-level architecture for a backend system of a smart parking lot. This system should handle vehicle entry and exit management, parking space allocation, and fee calculation.

## 2. Problem Statement
Imagine a parking lot in an urban area with multiple floors and numerous parking spots. Your task is to create a low-level design for a system that efficiently manages the parking process. The system should:
- **Automatically assign** parking spots based on vehicle size and availability.
- **Track the time** each vehicle spends in the parking lot.
- **Calculate parking fees** upon exit.

## 3. Functional Requirements

### 3.1 Parking Spot Allocation
Automatically assign an available parking spot to a vehicle when it enters, based on the vehicle’s size. The system should handle different vehicle types, such as:
- Motorcycle
- Car
- Bus

### 3.2 Check-In and Check-Out
- **Check-In:** Record the exact entry time of vehicles and generate a parking ticket or token.
- **Check-Out:** Record the exact exit time of vehicles when they leave the premises.

### 3.3 Parking Fee Calculation
Calculate fees accurately based on the duration of stay and the specific vehicle type. Different vehicle types may have different pricing tiers (e.g., hourly or flat rates).

### 3.4 Real-Time Availability Update
Update the availability of parking spots in real-time as vehicles enter and leave. The system must always reflect the correct state of the parking lot.

## 4. Design Aspects to Consider

### 4.1 Data Model
Design a robust class diagram or database schema to manage the following entities:
- **Parking Spots:** Floor number, spot type (small, compact, large), and current status (available, occupied).
- **Vehicles:** License plate number and vehicle type.
- **Parking Tickets/Transactions:** Entry timestamp, exit timestamp, assigned parking spot, and total fee calculated.

### 4.2 Algorithm for Spot Allocation
Develop an efficient algorithm to assign parking spots to incoming vehicles. Consider strategies such as finding the spot closest to the entrance or assigning specific floors based on vehicle type.

### 4.3 Fee Calculation Logic
Implement modular and extensible logic to calculate fees based on parking duration and vehicle type. The logic should be adaptable to potential future changes in pricing structures.

### 4.4 Concurrency Handling
Ensure the system can handle multiple vehicles entering or exiting simultaneously without issues. This requires implementing thread safety (for in-memory systems) or proper transactional locks (for database-backed systems) to prevent race conditions, such as assigning the same parking spot to two different vehicles at the same time.
