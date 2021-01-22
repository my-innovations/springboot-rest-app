package com.infotech.book.ticket.app.services;

import com.infotech.book.ticket.app.entities.Ticket;

public interface TicketBookingService {
	
	public Ticket createTicket(Ticket ticket);
	public Ticket getTicketById(Integer ticketId);
	public Ticket getTicketByEmail(String email);
	public Iterable<Ticket> getAllBookedTickets();
	public Ticket updateTicket(Integer ticketId, String newEmail);
	public void deleteTicket(Integer ticketId);

}
