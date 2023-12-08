package com.webapps.telemetrywebapp;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DeviceRepository extends CrudRepository<Device, Integer> {
    
    @Query(nativeQuery = true, value = "SELECT COUNT(d.id) FROM device d WHERE d.id = :id")
    int countDevices(@Param("id") UUID id);

    @Query(nativeQuery = true, value = "SELECT d.token FROM device d WHERE d.id = :id")
    String findTokenForDevice(@Param("id") UUID id);

    @Query(nativeQuery = true, value = "SELECT d FROM device d WHERE d.id = :id")
    Device getDeviceFromUUID(@Param("id") UUID id);
}

