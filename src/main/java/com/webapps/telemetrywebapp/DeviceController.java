package com.webapps.telemetrywebapp;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/device")
public class DeviceController {
  @Autowired 
  private DeviceRepository deviceRepository;

  @PostMapping(path="/add")
  public @ResponseBody String addNewDevice (@RequestParam String name) {

    Device d = new Device();
    d.setName(name);
    String token = SecureTokenGenerator.generateToken();
    d.setToken(token);
    d.setTimestamp(new Timestamp(System.currentTimeMillis()));
    deviceRepository.save(d);
    return "Saved";
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Device> getAllDevices() {
    return deviceRepository.findAll();
  }
    
}
