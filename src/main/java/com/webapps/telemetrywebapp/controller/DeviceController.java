package com.webapps.telemetrywebapp.controller;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.telemetrywebapp.features.SecureTokenGenerator;
import com.webapps.telemetrywebapp.model.device.Device;
import com.webapps.telemetrywebapp.model.device.DeviceRepository;

@Controller
@RequestMapping(path="/device")
public class DeviceController {
  @Autowired 
  private DeviceRepository deviceRepository;

  @Value("${spring.profiles.active}")
  private String activeProfile;

  @PostMapping(path="/add")
  public @ResponseBody String addNewDevice (@RequestParam String name) {

    Device d = new Device();
    d.setName(name);
    String token = SecureTokenGenerator.generateToken();
    d.setToken(token);
    d.setTimestamp(new Timestamp(System.currentTimeMillis()));
    deviceRepository.save(d);
    return "table";
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Device> getAllDevices() {
    return deviceRepository.findAll();
  }

  @DeleteMapping(path="/delete/{id}")
  public @ResponseBody String deleteDevice(@PathVariable UUID id) {
    deviceRepository.deleteById(id);
    return "Success";
  }

  @GetMapping("/newestID")
  public @ResponseBody UUID getNewestDevice() {
    if(activeProfile.equals("dev")) {
      return deviceRepository.getNewestDeviceIdMySQL();
    } else {
      return deviceRepository.getNewestDeviceIdMariaDB();
    }
  }

  @GetMapping("/{id}/name")
  public @ResponseBody String getDeviceName(@PathVariable UUID id) {
    Device d = new Device();
    d = deviceRepository.getDeviceFromUUID(id);
    return d.getName();
  }
  
    
}
