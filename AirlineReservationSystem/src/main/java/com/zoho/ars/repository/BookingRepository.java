package com.zoho.ars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zoho.ars.model.Passenger;

@Repository
public interface BookingRepository extends JpaRepository<Passenger,String>{

}
