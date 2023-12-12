package com.webapps.telemetrywebapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class MvcConfig implements WebMvcConfigurer {

	@Autowired 
  	private DeviceRepository deviceRepository;

	/* 
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("index");
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/devices").setViewName("table");
	}	*/

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