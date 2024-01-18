package com.webapps.telemetrywebapp.model.device;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DeviceRepository extends CrudRepository<Device, UUID> {
    
    @Query(nativeQuery = true, value = "SELECT COUNT(d.id) FROM device d WHERE d.id = :id")
    int countDevices(@Param("id") UUID id);

    @Query(nativeQuery = true, value = "SELECT d.token FROM device d WHERE d.id = :id")
    String findTokenForDevice(@Param("id") UUID id);

    @Query(nativeQuery = true, value = "SELECT * FROM device d WHERE d.id = :id")
    Device getDeviceFromUUID(@Param("id") UUID id);

    @Query(nativeQuery = true, value = "SELECT * FROM device d ORDER BY d.timestamp DESC")
    List<Device> getDevicesList();

    @Query(nativeQuery = true, value = "DELETE FROM device d WHERE hex(d.id) = :id")
    void deleteDeviceByHexId(@Param("id") UUID id);

    @Query(nativeQuery = true, value = "SELECT BIN_TO_UUID(d.id) FROM device d ORDER BY d.timestamp DESC LIMIT 1")
    UUID getNewestDeviceIdMySQL();

    @Query(nativeQuery = true, value = "SELECT d.id FROM device d ORDER BY d.timestamp DESC LIMIT 1")
    UUID getNewestDeviceIdMariaDB();

}

