package com.webapps.telemetrywebapp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;

@Entity // This tells Hibernate to make a table out of this class
public class Telemetry {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  private double temperature;

  private double humidity;

  private Timestamp timestamp;

  public Integer getId() {
    return id;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public double getTemp() {
    return temperature;
  }

  public void setTemp(double temperature) {
    this.temperature = temperature;
  }

  public double getHumid() {
    return humidity;
  }

  public void setHumid(double humidity) {
    this.humidity = humidity;
  }
}
