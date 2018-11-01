package com.apap.tutorial7.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.apap.tutorial7.model.FlightModel;
import com.apap.tutorial7.rest.Setting;
import com.apap.tutorial7.service.FlightService;

/**
 * FlightController
 */
@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;
    
    @Autowired
    RestTemplate restTemplate;
    
    @RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("home", true);
		return "home";
	}
    
    @PostMapping(value = "/add")
    public FlightModel addFlightSubmit(@RequestBody FlightModel flight) {
        return flightService.addFlight(flight);
        
    }

    @GetMapping(value = "/all")
    public List<FlightModel> viewAllFlight() {
        List<FlightModel> flightList = flightService.getFlightList();
        return flightList;
    }
    
    @GetMapping(value = "/view/{flightNumber}")
    public FlightModel viewFlight(@PathVariable("flightNumber") String flightNumber) {
        FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber).get();
        return flight;
    }

    @DeleteMapping(value = "/delete")
    public String deleteFlight(@RequestParam("flightId") long flightId) {
        FlightModel flight = flightService.getFlightDetailById(flightId).get();
        flightService.deleteFlight(flight);
        return "flight has been deleted";
    }

    @PutMapping(value = "/update/{flightId}")
    public String updateFlightSubmit(@PathVariable("flightId") long flightId,
    		@RequestParam("destination") String destination,
    		@RequestParam("origin") String origin,
    		@RequestParam("time") Date time) {
    	FlightModel flight = flightService.getFlightDetailById(flightId).get();
        if (flight.equals(null)) {
        	return "Couldn't find your flight";
        }
        
        flight.setDestination(destination);
        flight.setOrigin(origin);
        flight.setTime(time);
        flightService.updateFlight(flight);
        return "flight update success";
    }
    
    @Bean
    public RestTemplate rest2() {
    	return new RestTemplate();
    }
    
    @GetMapping(value = "/airport/{namaKota}", produces = "application/json")
    public String getAirport(@PathVariable("namaKota") String namaKota) throws Exception {
    	String path = Setting.amadeusUrl + "&term=" + namaKota + "&country=ID&all_airports=true";
    	return restTemplate.getForEntity(path, String.class).getBody();
    }
}