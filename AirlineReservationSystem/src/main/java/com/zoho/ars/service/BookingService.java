package com.zoho.ars.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoho.ars.model.FlightBookingDetails;
import com.zoho.ars.model.Passenger;
import com.zoho.ars.model.TicketDetails;
import com.zoho.ars.model.UpdateFlightBookingDetails;
import com.zoho.ars.repository.BookingRepository;
import com.zoho.ars.repository.FlightRepository;
import com.zoho.ars.repository.TicketRepository;

@Service
public class BookingService {
	@Autowired
	BookingRepository bookingRepository;
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	FlightRepository flightRepository;
	
	public List<TicketDetails> bookTickets(FlightBookingDetails flightBookingDetails,Calendar journeyDate,String userId)
	{
		Random random = new Random();
		ZoneId zoneId = ZoneId.of("Asia/Kolkata");
		LocalDate today = LocalDate.now(zoneId);
		String date = today.toString() ;
		List<TicketDetails> bookedTickets = new ArrayList<>();
		for(Passenger passenger:flightBookingDetails.getPassengers())
		{
			passenger.setPnr("P"+Integer.toString(random.nextInt(999999)));
			bookingRepository.save(passenger);
			TicketDetails ticketDetails = new TicketDetails();
			ticketDetails.setPnr(passenger.getPnr());
			ticketDetails.setBookedDate(date);
			ticketDetails.setDepartureDate(Integer.toString(journeyDate.get(Calendar.YEAR))+"-"+Integer.toString(journeyDate.get(Calendar.MONTH)+1)+"-"+Integer.toString(journeyDate.get(Calendar.DATE)));
			ticketDetails.setDepartureTime(flightBookingDetails.getFlightToBeBooked().getDeparturetime());
			ticketDetails.setFlightId(flightBookingDetails.getFlightToBeBooked().getFlightId());
			ticketDetails.setUserId(userId);
			ticketDetails.setTotalFare(flightBookingDetails.getFlightToBeBooked().getFare());
			ticketDetails.setBookingStatus("Booked");
			ticketDetails.setTotalSeats(flightBookingDetails.getPassengers().size());
			bookedTickets.add(ticketDetails);
			ticketRepository.save(ticketDetails);
			
		}
		flightRepository.updateSeatDetails(flightBookingDetails.getFlightToBeBooked().getFlightId(),flightBookingDetails.getPassengers().size());
	    return bookedTickets;
	}
	
	public void modifyBookedTicket(UpdateFlightBookingDetails updateflightbookingdetails)
	{
		int additionalCost = 0;
		for(String addOns:updateflightbookingdetails.getAddOns())
		{
			if(addOns.toLowerCase().equals("food"))
				additionalCost+=1500;
			else if(addOns.toLowerCase().equals("extraluggage"))
				additionalCost+=700;
			else if(addOns.toLowerCase().equals("priorityboarding"))
				additionalCost+=890;
			else if(addOns.toLowerCase().equals("airportlounges"))
				additionalCost+=1250;
			else if(addOns.toLowerCase().equals("seatreservation"))
				additionalCost+=1000;
			else if(addOns.toLowerCase().equals("travelinsurance"))
				additionalCost+=1000;
		}
		ticketRepository.updateTicketDetails(updateflightbookingdetails.getPassenger().getPnr(), Double.valueOf(additionalCost));
		
	}
	
	public void cancelTicket(Passenger passenger)
	{
		
		ticketRepository.cancelTicket(passenger.getPnr(), "Cancelled");
		Optional<TicketDetails> cancelledticketdetails = ticketRepository.findById(passenger.getPnr());
		flightRepository.updateSeatDetails(cancelledticketdetails.get().getFlightId(), -1);
		
	}
	
	public List<TicketDetails> fetchBookingHistory(String userId)
	{
		List<TicketDetails> bookingHistory = ticketRepository.findAll();
		
		if(bookingHistory==null)
			return null;
		
		return bookingHistory.stream().filter(type->type.getUserId().equals(userId) && type.getBookingStatus().equals("Booked")).collect(Collectors.toList());

    }
	
	public List<TicketDetails> fetchcancelHistory(String userId)
	{
		List<TicketDetails> cancelHistory = ticketRepository.findAll();
		
		if(cancelHistory==null)
			return null;
		
		return cancelHistory.stream().filter(type->type.getUserId().equals(userId) && type.getBookingStatus().equals("Cancelled")).collect(Collectors.toList());

    }
}
