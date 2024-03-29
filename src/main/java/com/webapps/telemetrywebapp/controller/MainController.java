package com.webapps.telemetrywebapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.webapps.telemetrywebapp.model.device.Device;
import com.webapps.telemetrywebapp.model.device.DeviceRepository;

@Controller
public class MainController implements WebMvcConfigurer {

	@Autowired 
  	private DeviceRepository deviceRepository;

	@GetMapping(value={"/","/home"})
    public String getDevicesForCharts(Model model) {
        List<Device> deviceList = deviceRepository.getDevicesList();
        model.addAttribute("deviceList", deviceList);
        return "index";
    }

	@GetMapping("/devices")
    public String getDevicesForTable(Model model) {
        List<Device> deviceList = deviceRepository.getDevicesList();
        model.addAttribute("deviceList", deviceList);
        return "table";
    }

}