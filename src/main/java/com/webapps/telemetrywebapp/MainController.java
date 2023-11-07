package com.webapps.telemetrywebapp;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/telemetry")
public class MainController {
  @Autowired 
  private TelemetryRepository telemetryRepository;

  @PostMapping(path="/add")
  public @ResponseBody String addNewTelemetry (@RequestParam float temperature, @RequestParam float humidity) {

    Telemetry n = new Telemetry();
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

  @GetMapping(path="/csrf")
  public @ResponseBody CsrfToken csrf(CsrfToken csrfToken) {
      return csrfToken;
  }
  
  @GetMapping(path="/temp")
  public @ResponseBody List<Object[]> getTemp(@RequestParam int minutes) {
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
    return telemetryRepository.getTempFromMinutes(minutes, mod);
  }

  @GetMapping(path="/humid")
  public @ResponseBody List<Object[]> getHumid(@RequestParam int minutes) {
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
    return telemetryRepository.getHumidFromMinutes(minutes, mod);
  }

  @GetMapping(path="/latest/temp")
  public @ResponseBody double getLatestTemp(){
    return telemetryRepository.getLatestTemp();
  }

  @GetMapping(path="/latest/humid")
  public @ResponseBody double getLatestHumid(){
    return telemetryRepository.getLatestHumid();
  }
  
}