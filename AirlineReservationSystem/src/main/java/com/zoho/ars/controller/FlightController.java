package com.zoho.ars.controller;


import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zoho.ars.model.Flight;
import com.zoho.ars.service.FlightService;
import com.zoho.ars.utility.CalendarUtility;



@RestController
@PropertySource("classpath:configuration.properties")
public class FlightController {
	@Autowired
	FlightService flightService;
	@Autowired
    private Environment environment;
	
	@GetMapping(value = "/searchFlights")
	public ResponseEntity<?> getFlightDetails(@RequestParam(name="source", required = true) String source,@RequestParam(name="destination", required = true) String destination,@RequestParam(name="flightAvailableDate", required = true) String flightAvailableDate,HttpServletRequest request) throws ParseException
	{
		
		Calendar journeyDate = CalendarUtility.getCalendarFromString(flightAvailableDate);
		if(Calendar.getInstance().after(journeyDate))
			return new ResponseEntity<>(environment.getProperty("FlightService.INVALID_JOURNEY_DATE"), HttpStatus.BAD_REQUEST);
		if(source.toLowerCase().equals(destination.toLowerCase()))
				return new ResponseEntity<>(environment.getProperty("FlightService.INVALID_SOURCE_DESTINATION"), HttpStatus.BAD_REQUEST);
		
		List<Flight> flightDetails = flightService.searchFlights(source, destination, journeyDate);
		if(flightDetails.isEmpty())
			return new ResponseEntity<>(environment.getProperty("FlightService.DETAILS_UNAVAILABLE"), HttpStatus.BAD_REQUEST);
		request.getSession().setAttribute("source",source);
		request.getSession().setAttribute("destination",destination);
		request.getSession().setAttribute("journeyDate",journeyDate);
		request.getSession().setAttribute("availableFlights",flightDetails);
		return new ResponseEntity<>(flightDetails, HttpStatus.OK);
}
	
	@GetMapping(value = "/paginateFlights")
	public ResponseEntity<?> getFlightDetails(@RequestParam(name="pageNo", required = true) int pageNo,@RequestParam(name="pageSize", required = true) int pageSize,@RequestParam(name="parameter", required = true) String parameter)
	{
		List<Flight> flightDetails = flightService.paginateFlights(pageNo,pageSize,parameter);
		if(flightDetails.isEmpty())
			return new ResponseEntity<>(environment.getProperty("FlightService.DETAILS_UNAVAILABLE"), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(flightDetails, HttpStatus.OK);
}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/filterFlights")
	public ResponseEntity<?> filterFlightDetails(@RequestParam(name="filterList",required=true) String[] filterList,HttpServletRequest request)
	{
		if(request.getSession().getAttribute("availableFlights")==null)
			return new ResponseEntity<>(environment.getProperty("FlightService.DETAILS_NOT_SEARCHED"), HttpStatus.BAD_REQUEST);
		List<Flight> flightDetails = (List<Flight>) request.getSession().getAttribute("availableFlights");
		List<Flight> filteredFlights = flightService.filterFlights(flightDetails,filterList);
		if(filteredFlights.isEmpty())
			return new ResponseEntity<>(environment.getProperty("FlightService.FILTER_DETAILS_UNAVAILABLE"), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(filteredFlights, HttpStatus.OK);
		
}
		
		
}
	

