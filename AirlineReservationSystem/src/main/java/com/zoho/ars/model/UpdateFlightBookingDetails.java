package com.zoho.ars.model;

import java.util.ArrayList;


public class UpdateFlightBookingDetails {
	private Passenger passenger;
	private ArrayList<String> addOns;
	
	public Passenger getPassenger() {
		return passenger;
	}
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	public ArrayList<String> getAddOns() {
		return addOns;
	}
	public void setAddOns(ArrayList<String> addOns) {
		this.addOns = addOns;
	}
	

}
