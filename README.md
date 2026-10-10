# Smart Parking Lot System

A robust, low-level design (LLD) implementation of a Smart Parking Lot backend system in Java.

## Features
- **Spot Allocation:** Dynamically assigns spots based on vehicle type and availability using a Nearest-Spot strategy.
- **Fee Calculation:** Calculates parking fees dynamically based on duration of stay.
- **Concurrency Support:** Handles concurrent vehicle check-ins and check-outs using thread-safe data structures (`ConcurrentHashMap`) and compare-and-swap synchronization patterns to prevent double-booking.

## Prerequisites
- Java 11 or higher
- Maven 3.6+

## How to Run

This project uses Maven for dependency management and building.

**1. Compile the Project:**
```bash
mvn compile
```

**2. Run the Main Demonstration:**
There is a `Main.java` class provided to demonstrate the core functionality of the parking lot manager.
```bash
mvn exec:java -Dexec.mainClass="com.smartparkinglot.Main"
```

**3. Run the Test Suite:**
The project includes unit tests written in JUnit 5 to validate spot allocation, check-ins, and check-outs.
```bash
mvn test
```
