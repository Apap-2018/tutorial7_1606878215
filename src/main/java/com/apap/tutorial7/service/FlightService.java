package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.FlightModel;

/**
 * FlightService
 */
public interface FlightService {
    FlightModel addFlight(FlightModel flight);
    
    void deleteByFlightNumber(String flightNumber);
    
    List<FlightModel> getFlightList();

    Optional<FlightModel> getFlightDetailByFlightNumber(String flightNumber);

	Optional<FlightModel> getFlightDetailById(long id);

	void deleteFlight(FlightModel flight);

	void updateFlight(FlightModel flight);
}