package com.zoho.ars.service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zoho.ars.repository.FlightManipulationRepository;
import com.zoho.ars.repository.FlightRepository;
import com.zoho.ars.model.Flight;


@Service
public class FlightService {

	@Autowired
	private FlightRepository flightRepository;
	@Autowired
	private FlightManipulationRepository flightManipulationRepository ;
	
	public List<Flight> searchFlights(String source, String destination, Calendar journeyDate)
	{
		List<Flight> avilableFlights = flightRepository.searchFlights(source, destination, journeyDate);
		return avilableFlights;	
		
	}
	
	public List<Flight> paginateFlights(int pageNo, int pageSize, String parameter)
	{
		Pageable paging = PageRequest.of(pageNo,pageSize,Sort.by(parameter));
		Page<Flight> pagedResult = flightManipulationRepository.findAll(paging);
		
		if(pagedResult.hasContent())
			return pagedResult.getContent();
        else 
            return null;	
	}
	
	public List<Flight> filterFlights(List<Flight> availableFlights,String[] filterList)
	{
		List<Flight> airlinesFilter=null;
		List<Flight> stopTypeFilter=null;
		List<Flight> journeyDurationFilter=null;
		List<Flight> fareFilter=null;
		List<Flight> arrivalTimeFilter=null;
		List<Flight> departureTimeFilter=null;
		List<Flight> filteredFlights=null;
		
		for(String parameters: Arrays.asList(filterList))
		{
			if(parameters.split("=")[0].toLowerCase().equals("airlines"))
				airlinesFilter = availableFlights.stream().filter(type->type.getAirlines().equals(parameters.split("=")[1])).collect(Collectors.toList());
			
			else if(parameters.split("=")[0].toLowerCase().equals("stoptype"))
				stopTypeFilter = availableFlights.stream().filter(type->type.getStopType().toLowerCase().equals(parameters.split("=")[1].toLowerCase())).collect(Collectors.toList());
			
			else if(parameters.split("=")[0].toLowerCase().equals("journeyduration"))
				journeyDurationFilter = availableFlights.stream().filter(type->type.getJourneyDuration()<=Float.parseFloat(parameters.split("=")[1])||type.getJourneyDuration()>=Float.parseFloat(parameters.split("=")[1])).collect(Collectors.toList());
			
			else if(parameters.split("=")[0].toLowerCase().equals("fare"))
				fareFilter = availableFlights.stream().filter(type->type.getFare()<=Double.parseDouble(parameters.split("=")[1])||type.getFare()>=Double.parseDouble(parameters.split("=")[1])).collect(Collectors.toList());
			
			else if(parameters.split("=")[0].toLowerCase().equals("arrivaltime"))
				arrivalTimeFilter= availableFlights.stream().filter(type->LocalTime.parse(type.getArrivalTime()).compareTo(LocalTime.parse(parameters.split("=")[1])) < 0||LocalTime.parse(type.getArrivalTime()).compareTo(LocalTime.parse(parameters.split("=")[1])) > 0).collect(Collectors.toList());
			
			else if(parameters.split("=")[0].toLowerCase().equals("departuretime"))
				departureTimeFilter= availableFlights.stream().filter(type->LocalTime.parse(type.getDeparturetime()).compareTo(LocalTime.parse(parameters.split("=")[1])) < 0||LocalTime.parse(type.getDeparturetime()).compareTo(LocalTime.parse(parameters.split("=")[1])) > 0).collect(Collectors.toList());
		
		}
	
		filteredFlights = Stream.of(airlinesFilter,stopTypeFilter,journeyDurationFilter,fareFilter,arrivalTimeFilter,departureTimeFilter).flatMap(x -> x == null? null : x.stream()).distinct().collect(Collectors.toList());
	    return filteredFlights;
			
	}
	

}
