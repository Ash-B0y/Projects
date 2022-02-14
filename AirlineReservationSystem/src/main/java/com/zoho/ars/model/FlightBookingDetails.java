package com.zoho.ars.model;

import java.util.List;

public class FlightBookingDetails {
	
	private Flight flightToBeBooked;
	private List<Passenger> passengers;
	
	public Flight getFlightToBeBooked() {
		return flightToBeBooked;
	}
	public void setFlightToBeBooked(Flight flightToBeBooked) {
		this.flightToBeBooked = flightToBeBooked;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

}
