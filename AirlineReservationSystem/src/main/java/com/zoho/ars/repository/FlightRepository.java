package com.zoho.ars.repository;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zoho.ars.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String>{
	
	@Query("select f from Flight f where f.source=:source and f.destination=:destination and f.availableDate=:journeyDate and seatCount>0")
	List<Flight> searchFlights(@Param("source") String source,@Param("destination") String destination,@Param("journeyDate") Calendar journeyDate);
    
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update Flight set seatCount = seatCount-:noOfSeats where flightId=:flightId")
	void updateSeatDetails(@Param("flightId") String flightId,@Param("noOfSeats") int noOfSeats);

}
