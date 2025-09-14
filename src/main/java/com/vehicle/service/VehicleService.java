package com.vehicle.service;

import com.vehicle.model.Vehicle;
import java.util.List;
import java.util.Optional;

public interface VehicleService {
    List<Vehicle> getAllVehicles();
    Optional<Vehicle> getVehicleById(Long id);
    Optional<Vehicle> getVehicleByRegistrationNumber(String registrationNumber);
    Vehicle saveVehicle(Vehicle vehicle);
    Vehicle updateVehicle(Long id, Vehicle vehicle);
    void deleteVehicle(Long id);
    boolean existsByRegistrationNumber(String registrationNumber);
}