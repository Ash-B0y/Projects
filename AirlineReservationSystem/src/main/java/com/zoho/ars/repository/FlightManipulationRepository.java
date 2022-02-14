package com.zoho.ars.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.zoho.ars.model.Flight;

@Repository
public interface FlightManipulationRepository extends PagingAndSortingRepository<Flight,String>{

}
