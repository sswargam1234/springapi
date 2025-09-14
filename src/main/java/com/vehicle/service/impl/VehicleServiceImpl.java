package com.vehicle.service.impl;

import com.vehicle.model.Vehicle;
import com.vehicle.repository.VehicleRepository;
import com.vehicle.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() {
        log.debug("Fetching all vehicles");
        return vehicleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vehicle> getVehicleById(Long id) {
        log.debug("Fetching vehicle with id: {}", id);
        return vehicleRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vehicle> getVehicleByRegistrationNumber(String registrationNumber) {
        log.debug("Fetching vehicle with registration number: {}", registrationNumber);
        return vehicleRepository.findByRegistrationNumber(registrationNumber);
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        log.debug("Saving new vehicle: {}", vehicle);
        if (existsByRegistrationNumber(vehicle.getRegistrationNumber())) {
            throw new IllegalArgumentException("Vehicle with registration number " + vehicle.getRegistrationNumber() + " already exists");
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle updateVehicle(Long id, Vehicle vehicle) {
        log.debug("Updating vehicle with id: {}", id);
        return vehicleRepository.findById(id)
            .map(existingVehicle -> {
                // Check if registration number is being changed and if new one already exists
                if (!existingVehicle.getRegistrationNumber().equals(vehicle.getRegistrationNumber()) &&
                    existsByRegistrationNumber(vehicle.getRegistrationNumber())) {
                    throw new IllegalArgumentException("Vehicle with registration number " + vehicle.getRegistrationNumber() + " already exists");
                }
                
                existingVehicle.setMake(vehicle.getMake());
                existingVehicle.setModel(vehicle.getModel());
                existingVehicle.setYear(vehicle.getYear());
                existingVehicle.setRegistrationNumber(vehicle.getRegistrationNumber());
                existingVehicle.setColor(vehicle.getColor());
                existingVehicle.setType(vehicle.getType());
                
                return vehicleRepository.save(existingVehicle);
            })
            .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + id));
    }

    @Override
    public void deleteVehicle(Long id) {
        log.debug("Deleting vehicle with id: {}", id);
        if (!vehicleRepository.existsById(id)) {
            throw new EntityNotFoundException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByRegistrationNumber(String registrationNumber) {
        return vehicleRepository.existsByRegistrationNumber(registrationNumber);
    }
}