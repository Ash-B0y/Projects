package com.zoho.ars.controller;


import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zoho.ars.model.FlightBookingDetails;
import com.zoho.ars.model.Passenger;
import com.zoho.ars.model.TicketDetails;
import com.zoho.ars.model.UpdateFlightBookingDetails;
import com.zoho.ars.service.BookingService;



@RestController
@PropertySource("classpath:configuration.properties")
public class BookingController {
	@Autowired
	BookingService bookingService;
	@Autowired
    private Environment environment;
	
	@PostMapping("/book")
	public ResponseEntity<?> bookTickets(@RequestBody FlightBookingDetails flightbookingdetails,BindingResult bindingResult,HttpServletRequest request)
	{
		if(request.getSession().getAttribute("Logged-In-User")==null)
			return new ResponseEntity<>(environment.getProperty("BookingService.USER_NOT_LOGGED_IN"), HttpStatus.BAD_REQUEST);
		
		if(request.getSession().getAttribute("availableFlights")==null)
			return new ResponseEntity<>(environment.getProperty("FlightService.DETAILS_NOT_SEARCHED"), HttpStatus.BAD_REQUEST);
		List<TicketDetails> ticketDetails = bookingService.bookTickets(flightbookingdetails,(Calendar)request.getSession().getAttribute("journeyDate"),request.getSession().getAttribute("Logged-In-User").toString());
		return new ResponseEntity<>(ticketDetails, HttpStatus.OK);
		
	}
	
	@PutMapping(value = "/modifyBooking")
	public ResponseEntity<?> modifyBooking(@RequestBody UpdateFlightBookingDetails updateflightbookingdetails,BindingResult bindingResult,HttpServletRequest request)
	{
		if(request.getSession().getAttribute("Logged-In-User")==null)
			return new ResponseEntity<>(environment.getProperty("BookingService.MODIFY.USER_NOT_LOGGED_IN"), HttpStatus.BAD_REQUEST);
	
		bookingService.modifyBookedTicket(updateflightbookingdetails);
				return new ResponseEntity<>(environment.getProperty("BookingService.MODIFY_BOOKING"), HttpStatus.OK);
	}
	
	@PutMapping(value = "/cancelBooking")
	public ResponseEntity<?> cancelBooking(@RequestBody Passenger passenger,HttpServletRequest request)
	{
		if(request.getSession().getAttribute("Logged-In-User")==null)
			return new ResponseEntity<>(environment.getProperty("BookingService.CANCEL.USER_NOT_LOGGED_IN"), HttpStatus.BAD_REQUEST);
	
		bookingService.cancelTicket(passenger);
				return new ResponseEntity<>(environment.getProperty("BookingService.CANCEL_BOOKING"), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getBookingHistory")
	public ResponseEntity<?> fetchBookingHistory(@RequestParam(name="userId",required=true) String userId,HttpServletRequest request)
	{
		if(request.getSession().getAttribute("Logged-In-User")==null)
			return new ResponseEntity<>(environment.getProperty("BookingService.BOOKING_HISTORY.USER_NOT_LOGGED_IN"), HttpStatus.BAD_REQUEST);
	
		List<TicketDetails> bookingHistory = bookingService.fetchBookingHistory(userId);
		if(null==bookingHistory || bookingHistory.isEmpty())
			return new ResponseEntity<>(environment.getProperty("BookingService.BOOKING_HISTORY.EMPTY"), HttpStatus.OK);
		
		return new ResponseEntity<>(bookingHistory, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getCancelHistory")
	public ResponseEntity<?> fetchCancelHistory(@RequestParam(name="userId",required=true) String userId,HttpServletRequest request)
	{
		if(request.getSession().getAttribute("Logged-In-User")==null)
			return new ResponseEntity<>(environment.getProperty("BookingService.CANCEL_HISTORY.USER_NOT_LOGGED_IN"), HttpStatus.BAD_REQUEST);
	
		List<TicketDetails> cancelHistory = bookingService.fetchcancelHistory(userId);
		if(null==cancelHistory || cancelHistory.isEmpty())
			return new ResponseEntity<>(environment.getProperty("BookingService.BOOKING_HISTORY.EMPTY"), HttpStatus.OK);
		
		return new ResponseEntity<>(cancelHistory, HttpStatus.OK);
	}
	
	
}
