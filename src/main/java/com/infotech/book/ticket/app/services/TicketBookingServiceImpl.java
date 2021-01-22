package com.infotech.book.ticket.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotech.book.ticket.app.entities.Ticket;
import com.infotech.book.ticket.app.repository.TicketBookingRepository;

@Service
public class TicketBookingServiceImpl implements TicketBookingService{

	@Autowired
	private TicketBookingRepository ticketBookingDao;

	@Override
	public Ticket createTicket(Ticket ticket) {
		return ticketBookingDao.save(ticket);
	}

	@Override
	public Ticket getTicketById(Integer ticketId) {
		// return ticketBookingDao.findOne(ticketId); // springboot 1.5.x
		return ticketBookingDao.findById(ticketId).get(); // springboot 2.x.x
	}
	
	@Override
	public Ticket getTicketByEmail(String email) {
		return ticketBookingDao.findByEmail(email);
	}

	@Override
	public Iterable<Ticket> getAllBookedTickets() {
		return ticketBookingDao.findAll();
	}

	@Override
	public Ticket updateTicket(Integer ticketId, String newEmail) {
		Ticket ticketFromDb = ticketBookingDao.findById(ticketId).get();
		ticketFromDb.setEmail(newEmail);
		Ticket upadedTicket = ticketBookingDao.save(ticketFromDb);
		return upadedTicket;
	}
	
	@Override
	public void deleteTicket(Integer ticketId) {
		// ticketBookingDao.delete(ticketId); // springboot 1.5.x
		ticketBookingDao.deleteById(ticketId); // springboot 2.x.x
	}
}
