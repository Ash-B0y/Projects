package com.zoho.ars.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zoho.ars.model.TicketDetails;

@Repository
public interface TicketRepository extends JpaRepository<TicketDetails,String>{
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update TicketDetails set totalFare = totalFare+:additionalCost where pnr=:pnr")
	void updateTicketDetails(@Param("pnr") String pnr,@Param("additionalCost") Double additionalCost);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update TicketDetails set bookingStatus = :bookingStatus where pnr=:pnr")
	void cancelTicket(@Param("pnr") String pnr,@Param("bookingStatus") String bookingStatus);


}
