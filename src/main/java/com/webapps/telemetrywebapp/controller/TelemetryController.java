package com.webapps.telemetrywebapp.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.telemetrywebapp.model.device.Device;
import com.webapps.telemetrywebapp.model.device.DeviceRepository;
import com.webapps.telemetrywebapp.model.telemetry.Telemetry;
import com.webapps.telemetrywebapp.model.telemetry.TelemetryRepository;

@Controller
@RequestMapping(path="/telemetry")
public class TelemetryController {
  @Autowired 
  private TelemetryRepository telemetryRepository;

  @Autowired
  private DeviceRepository deviceRepository;

  @PostMapping(path="/add")
  public @ResponseBody String addNewTelemetry (@RequestParam BigDecimal temperature, @RequestParam BigDecimal humidity, @RequestParam UUID device_id, @RequestParam String token) {
    if (deviceRepository.countDevices(device_id) == 0) {
      return "Device not found";
    } else if (!deviceRepository.findTokenForDevice(device_id).equals(token)) {
      return "False Token"+deviceRepository.findTokenForDevice(device_id);
    }
    Device d = deviceRepository.getDeviceFromUUID(device_id); 
    Telemetry n = new Telemetry();
    n.setDevice(d);
    n.setTemp(temperature);
    n.setHumid(humidity);
    n.setTimestamp(new Timestamp(System.currentTimeMillis()));
    telemetryRepository.save(n);
    return "Saved";
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Telemetry> getAllTelemetry() {
    return telemetryRepository.findAll();
  }
  
  @GetMapping(path="/temp")
  public @ResponseBody List<Object[]> getTemp(@RequestParam int minutes, @RequestParam UUID device_id) {
    int mod = 0; //modulo -> gibt an in welchen Abständen Werte abgefragt werden
    switch(minutes){
      case 1:
        mod = 1; //jeder Wert aus der letzten Minute wird abgefragt
        break;
      case 30:
        mod = 5;
        break;
      case 60:
        mod = 10;
        break;
      case 300:
        mod = 20;
        break;
      case 720:
        mod = 30;
        break;
      case 1440:
        mod = 60;
        break;
      case 10080:
        mod = 200;
        break;
      case 43200:
        mod = 1000;
        break;
    }
    return telemetryRepository.getTempFromMinutes(minutes, mod, device_id);
  }

  @GetMapping(path="/humid")
  public @ResponseBody List<Object[]> getHumid(@RequestParam int minutes, @RequestParam UUID device_id) {
    int mod = 0; //modulo -> gibt an in welchen Abständen Werte abgefragt werden
    switch(minutes){
      case 1:
        mod = 1; //jeder Wert aus der letzten Minute wird abgefragt
        break;
      case 30:
        mod = 5; //jeder 5. Wert aus den letzten 30 Minuten wird abgefragt
        break;
      case 60:
        mod = 10;
        break;
      case 300:
        mod = 20;
        break;
      case 720:
        mod = 30;
        break;
      case 1440:
        mod = 60;
        break;
      case 10080:
        mod = 200;
        break;
      case 43200:
        mod = 1000;
        break;
    }
    return telemetryRepository.getHumidFromMinutes(minutes, mod, device_id);
  }

  @GetMapping(path="/latest/temp")
  public @ResponseBody String getLatestTemp(@RequestParam UUID device_id){
    if (telemetryRepository.countTelemetry(device_id) > 0) {
      return telemetryRepository.getLatestTemp(device_id)+" °C";
    } else {
      return "keine Werte vorhanden";
    }
  }

  @GetMapping(path="/latest/humid")
  public @ResponseBody String getLatestHumid(@RequestParam UUID device_id){
    if (telemetryRepository.countTelemetry(device_id) > 0) {
      return telemetryRepository.getLatestHumid(device_id)+" %";
    } else {
      return "keine Werte vorhanden";
    }
  }
  
}