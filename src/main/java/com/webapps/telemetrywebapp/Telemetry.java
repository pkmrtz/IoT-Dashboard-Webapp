package com.webapps.telemetrywebapp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.sql.Timestamp;
import java.math.BigDecimal;

@Entity // This tells Hibernate to make a table out of this class
public class Telemetry {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)  // Änderung hier
  private Device device;

  private BigDecimal temperature;

  private BigDecimal humidity;

  private Timestamp timestamp;

  public Integer getId() {
    return id;
  }

  public Device getDevice() {
    return device;
  }

  public void setDevice(Device device) {
    this.device = device;
  }
  
  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public BigDecimal getTemp() {
    return temperature;
  }

  public void setTemp(BigDecimal temperature) {
    this.temperature = temperature;
  }

  public BigDecimal getHumid() {
    return humidity;
  }

  public void setHumid(BigDecimal humidity) {
    this.humidity = humidity;
  }
}
