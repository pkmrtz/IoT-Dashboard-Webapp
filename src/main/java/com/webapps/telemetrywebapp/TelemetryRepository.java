package com.webapps.telemetrywebapp;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TelemetryRepository extends CrudRepository<Telemetry, Integer> {
    
    @Query(nativeQuery = true, value = "SELECT t.temperature, t.timestamp FROM telemetry t WHERE t.timestamp >= NOW() - INTERVAL :minutes MINUTE AND mod(t.id,:modulo)=0 AND device_id=:device_id")
    List<Object[]> getTempFromMinutes(@Param("minutes") int minutes, @Param("modulo") int modulo, @Param("device_id") UUID device_id);

    @Query(nativeQuery = true, value = "SELECT t.humidity, t.timestamp FROM telemetry t WHERE t.timestamp >= NOW() - INTERVAL :minutes MINUTE AND mod(t.id,:modulo)=0 AND device_id=:device_id")
    List<Object[]> getHumidFromMinutes(@Param("minutes") int minutes, @Param("modulo") int modulo, @Param("device_id") UUID device_id);

    @Query(nativeQuery = true, value = "SELECT t.temperature FROM telemetry t WHERE t.id = (SELECT MAX(t.id) FROM telemetrie.telemetry t) AND device_id=:device_id")
    double getLatestTemp(@Param("device_id") UUID device_id);

    @Query(nativeQuery = true, value = "SELECT t.humidity FROM telemetry t WHERE t.id = (SELECT MAX(t.id) FROM telemetrie.telemetry t) AND device_id=:device_id")
    double getLatestHumid(@Param("device_id") UUID device_id);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM telemetry t WHERE device_id=:device_id")
    int countTelemetry(@Param("device_id") UUID device_id);
}

