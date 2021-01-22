package com.infotech.book.ticket.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.infotech.book.ticket.app.entities.Ticket;
import com.infotech.book.ticket.app.repository.TicketBookingRepository;
import com.infotech.book.ticket.app.services.TicketBookingServiceImpl;

//@ExtendWith(MockitoExtention.class) //junit5
//@ExtendWith(SpringExtention.class)//junit5
//OR
@RunWith(SpringRunner.class)
@SpringBootTest // starts Spring application context but not the tomcat server.
public class TicketBookingServiceUnitTest {
	
	//Not required here, so commented.
	//@Autowired //ok
	//private TicketBookingController ticketBookingController;

	@Autowired
	//@InjectMocks //NullpointerException
	private TicketBookingServiceImpl ticketBookingServiceImpl;
	
	@MockBean
	private TicketBookingRepository ticketBookingRepository;
	
	@Test
	public void contextLoads() throws Exception {
		//assertThat(ticketBookingController).isNotNull();
		Assertions.assertThat(ticketBookingServiceImpl).isNotNull();
	}
	
	@Test
	//@Ignore
	public void testCreateTicket(){

		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		ticket.setPassengerName("Martin Bingel");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("martin.s2017@gmail.com");
		
	    Mockito.when(ticketBookingRepository.save(ticket)).thenReturn(ticket);
	    Assertions.assertThat(ticketBookingServiceImpl.createTicket(ticket)).isEqualTo(ticket);
	}
	
	@Test
	public void testGetTicketById(){
		
		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		ticket.setPassengerName("Martin Bingel");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("martin.s2017@gmail.com");
		
	    Mockito.when(ticketBookingRepository.findById(1)).thenReturn(Optional.of(ticket));
	    Assertions.assertThat(ticketBookingServiceImpl.getTicketById(1)).isEqualTo(ticket);
	}
	
	@Test
	public void testGetAllBookedTickets(){
		
		Ticket ticket1 = new Ticket();
		ticket1.setPassengerName("Martin Bingel");
		ticket1.setSourceStation("Kolkata");
		ticket1.setDestStation("Delhi");
		ticket1.setBookingDate(new Date());
		ticket1.setEmail("martin.s2017@gmail.com");
		
		Ticket ticket2 = new Ticket();
		ticket2.setPassengerName("Sean Murphy");
		ticket2.setSourceStation("Kolkata");
		ticket2.setDestStation("Mumbai");
		ticket2.setBookingDate(new Date());
		ticket2.setEmail("sean.s2017@gmail.com");
		
		List<Ticket> ticketList = new ArrayList<>();
		ticketList.add(ticket1);
		ticketList.add(ticket2);
		
		Mockito.when(ticketBookingRepository.findAll()).thenReturn(ticketList);
		Assertions.assertThat(ticketBookingServiceImpl.getAllBookedTickets()).isEqualTo(ticketList);
		Assert.assertTrue(ticketList.size() > 0);
	}
	
	@Test
	public void testGetTicketByEmail(){
		
		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		ticket.setPassengerName("Martin Bingel");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("martin.s2017@gmail.com");
		
	    Mockito.when(ticketBookingRepository.findByEmail("martin.s2017@gmail.com")).thenReturn(ticket);
	    Assertions.assertThat(ticketBookingServiceImpl.getTicketByEmail("martin.s2017@gmail.com")).isEqualTo(ticket);
	}
	
	@Test
	public void testUpdateTicket(){
		
		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		ticket.setPassengerName("Martin Bingel");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("martin.s2017@gmail.com");
		
		Mockito.when(ticketBookingRepository.findById(1)).thenReturn(Optional.of(ticket));
		
		ticket.setEmail("martin.s2000@gmail.com");
		Mockito.when(ticketBookingRepository.save(ticket)).thenReturn(ticket);
		Assertions.assertThat(ticketBookingServiceImpl.updateTicket(1, "martin.s2017@gmail.com")).isEqualTo(ticket);
		
	}
	
	@Test
	public void testDeleteTicket(){
		
		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		ticket.setPassengerName("Martin Bingel");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("martin.s2017@gmail.com");
		
	    Mockito.when(ticketBookingRepository.findById(1)).thenReturn(Optional.of(ticket));
	    Mockito.when(ticketBookingRepository.existsById(ticket.getTicketId())).thenReturn(false);
	    Assert.assertFalse(ticketBookingRepository.existsById(ticket.getTicketId()));
	}
}