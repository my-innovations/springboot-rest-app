package com.infotech.book.ticket.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.infotech.book.ticket.app.entities.Ticket;

public interface TicketBookingRepository extends CrudRepository<Ticket, Integer>{
	Ticket findByEmail(String email);
}
