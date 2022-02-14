package com.zoho.ars.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name="flight_details")
public class Flight implements Serializable{

	@Id
	@Column(name="flight_id")
	private String flightId;
	private String airlines;
	private String source;
	private String destination;
	private double fare;
	@Column(name="stop_type")
	private String stopType;
	@Column(name="journey_duration")
	private float journeyDuration;
	@Column(name="flight_available_date")
	private Calendar availableDate;
	@Column(name="arrival_time")
	private String arrivalTime;
	@Column(name="departure_time")
	private String departuretime;
	@Column(name="seat_count")
	private int seatCount;
	
	public Flight() {
		super();
	}
	
	public String getFlightId() {
		return flightId;
	}
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}
	public String getAirlines() {
		return airlines;
	}
	public void setAirlines(String airlines) {
		this.airlines = airlines;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	public String getStopType() {
		return stopType;
	}
	public void setStopType(String stopType) {
		this.stopType = stopType;
	}
	public float getJourneyDuration() {
		return journeyDuration;
	}
	public void setJourneyDuration(float journeyDuration) {
		this.journeyDuration = journeyDuration;
	}
	public Calendar getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(Calendar availableDate) {
		this.availableDate = availableDate;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getDeparturetime() {
		return departuretime;
	}
	public void setDeparturetime(String departuretime) {
		this.departuretime = departuretime;
	}
	public int getSeatCount() {
		return seatCount;
	}
	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	@Override
	public String toString() {
		return "Flight [flightId=" + flightId + ", airlines=" + airlines + ", source=" + source + ", destination="
				+ destination + ", fare=" + fare + ", stopType=" + stopType + ", journeyDuration=" + journeyDuration
				+ ", availableDate=" + availableDate + ", arrivalTime=" + arrivalTime + ", departuretime="
				+ departuretime + ", seatCount=" + seatCount + "]";
	}
	
	
}
