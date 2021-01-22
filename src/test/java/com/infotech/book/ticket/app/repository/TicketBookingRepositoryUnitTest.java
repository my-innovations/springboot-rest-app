package com.infotech.book.ticket.app.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.infotech.book.ticket.app.entities.Ticket;

//@TestMethodOrder(OrderAnnotation.classs) //junit5
@AutoConfigureTestDatabase(replace = Replace.NONE)
@RunWith(SpringRunner.class)
@DataJpaTest
public class TicketBookingRepositoryUnitTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private TicketBookingRepository ticketBookingRepository;
	
	//This will execute before executing each test case.
	/*@Before
	public void setup() {
		
		Ticket ticket1 = new Ticket();
		ticket1.setPassengerName("Sean Murphy1");
		ticket1.setSourceStation("Delhi1");
		ticket1.setDestStation("Mumbai1");
		ticket1.setBookingDate(new Date());
		ticket1.setEmail("sean1.s2006@gmail.com");
		
		testEntityManager.persist(ticket1);
		
		Ticket ticket2 = new Ticket();
		ticket2.setPassengerName("Sean Murphy2");
		ticket2.setSourceStation("Delhi2");
		ticket2.setDestStation("Mumbai2");
		ticket2.setBookingDate(new Date());
		ticket2.setEmail("sean2.s2006@gmail.com");
		
		testEntityManager.persist(ticket2);
	}*/

	@Test
	//@Rollback(false)
	 //@Order(1) 
	public void test_SaveTicket_1() {
		
		Ticket ticket = getTicket();
		Ticket savedInDb = testEntityManager.persist(ticket);
		Ticket getFromDb = ticketBookingRepository.findById(savedInDb.getTicketId()).get();
		Assert.assertNotNull(getFromDb);
		Assert.assertTrue(getFromDb.getTicketId() > 0);
		Assertions.assertThat(getFromDb).isEqualTo(savedInDb);
	}
	
	@Test
	//@Rollback(false)
	//@Order(1) 
	public void test_SaveTicket_2() {
	
		Ticket ticket = getTicket2();
		Ticket getFromDb = ticketBookingRepository.save(ticket);
		Ticket savedInDb = (Ticket) testEntityManager.find(Ticket.class, getFromDb.getTicketId());
		Assertions.assertThat(getFromDb).isEqualTo(savedInDb);
	}

	private Ticket getTicket() {
		Ticket ticket = new Ticket();
		ticket.setPassengerName("Sean Murphy3");
		ticket.setSourceStation("Delhi");
		ticket.setDestStation("Mumbai");
		ticket.setBookingDate(new Date());
		ticket.setEmail("sean3.s2006@gmail.com");
		return ticket;
	}
	
	private Ticket getTicket2() {
		Ticket ticket = new Ticket();
		ticket.setPassengerName("Sean Murphy4");
		ticket.setSourceStation("Delhi");
		ticket.setDestStation("Mumbai");
		ticket.setBookingDate(new Date());
		ticket.setEmail("sean4.s2006@gmail.com");
		return ticket;
	}

	@Test
	//@Order(2)
	public void test_GetTicketById_1() {
		
		Ticket ticket = new Ticket();
		ticket.setPassengerName("Martin Bingel5");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("martin5.s2017@gmail.com");

		// Save ticket in DB
		Ticket ticketSavedInDb = testEntityManager.persist(ticket);

		// Get Ticket from DB
		Ticket ticketFromInDb = ticketBookingRepository.findById(ticketSavedInDb.getTicketId()).get();
		Assertions.assertThat(ticketSavedInDb).isEqualTo(ticketFromInDb);
		
		//Ticket ticketFromInDb = ticketBookingRepository.findById(1).get(); // Exception: No value Present
		//Assert.assertNotNull(ticketFromInDb);
		
	}
	
	@Test
	//@Order(2)
	public void test_GetTicketById_2() {
		Ticket ticket = new Ticket();
		ticket.setPassengerName("Martin Bingel5");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("martin5.s2017@gmail.com");

		// Save ticket in DB
		Ticket ticketSavedInDb = testEntityManager.persist(ticket);
		
		Ticket ticketFromInDb = ticketBookingRepository.findById(ticketSavedInDb.getTicketId()).get();
		Assert.assertNotNull(ticketFromInDb);
		
		//Ticket ticketFromInDb = ticketBookingRepository.findById(1).get();  // Exception: No value Present
		//Assert.assertNotNull(ticketFromInDb);
	}
	
	
	@Test
	//@Order(2)
	@Sql("/test.sql")
	public void test_GetTicketById_3() {
		Ticket ticketFromInDb = ticketBookingRepository.findById(11).get();
		Assert.assertNotNull(ticketFromInDb);
	}

	@Test
	//@Order(2)
	public void test_GetAllBookedTickets() {
		
		Ticket ticket1 = new Ticket();
		ticket1.setPassengerName("Martin Bingel6");
		ticket1.setSourceStation("Kolkata");
		ticket1.setDestStation("Delhi");
		ticket1.setBookingDate(new Date());
		ticket1.setEmail("martin6.s2017@gmail.com");

		Ticket ticket2 = new Ticket();
		ticket2.setPassengerName("Sean Murphy7");
		ticket2.setSourceStation("Kolkata");
		ticket2.setDestStation("Mumbai");
		ticket2.setBookingDate(new Date());
		ticket2.setEmail("sean7.s2017@gmail.com");

		// Save both tickets in DB
		testEntityManager.persist(ticket1);
		testEntityManager.persist(ticket2);

		Iterable<Ticket> allTicketsFromDb = ticketBookingRepository.findAll();
		List<Ticket> ticketList = new ArrayList<>();

		for (Ticket ticket : allTicketsFromDb) {
			ticketList.add(ticket);
		}
		//Assertions.assertThat(ticketList.size()).isEqualTo(2);
		Assertions.assertThat(ticketList.size()).isGreaterThan(0);
	}

	@Test
	//@Order(2)
	public void test_FindByEmail() {
		
		Ticket ticket = new Ticket();
		ticket.setPassengerName("Martin Bingel8");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("martin8.s2017@gmail.com");

		// Ticket in DB
		testEntityManager.persist(ticket);

		// Get ticket info from DB for specified email
		Ticket getFromDb = ticketBookingRepository.findByEmail("martin8.s2017@gmail.com");
		Assertions.assertThat(getFromDb.getPassengerName()).isEqualTo("Martin Bingel8");
	}

	@Test
	@Rollback(false)
	//@Order(3)
	public void test_UpdateTicket() {
		
		Ticket ticket = new Ticket();
		ticket.setPassengerName("Martin Bingel9");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("martin9.s2017@gmail.com");

		// save Ticket info in DB
		testEntityManager.persist(ticket);

		Ticket getFromDb = ticketBookingRepository.findByEmail("martin9.s2017@gmail.com");
		
		// update Email Address
		getFromDb.setEmail("martin9.s2000@gmail.com");
		testEntityManager.persist(getFromDb);
		
		Assertions.assertThat(getFromDb.getEmail()).isEqualTo("martin9.s2000@gmail.com");
	}
	
/*	@Test
	@Rollback(false)
	//@Order(4)
	public void test_DeleteTicketById_1() {
		
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
		
		// Save both tickets in DB
		Ticket persist = testEntityManager.persist(ticket1);
		testEntityManager.persist(ticket2);

		// delete one ticket from DB
		testEntityManager.remove(persist);

		Iterable<Ticket> allTicketsFromDb = ticketBookingRepository.findAll();
		List<Ticket> ticketList = new ArrayList<>();

		for (Ticket ticket : allTicketsFromDb) {
			ticketList.add(ticket);
		}
		Assertions.assertThat(ticketList.size()).isEqualTo(2);
	}*/
	
	@Test
	@Rollback(false)
	//@Order(4)
	public void test_DeleteTicketById_2() {
		
		Integer id = 1;
		boolean isPresent = ticketBookingRepository.findById(id).isPresent();
		
		if (isPresent) {
			ticketBookingRepository.deleteById(id);
			Assert.assertTrue(isPresent);
		}
		
		boolean isPresentAfterDelete = ticketBookingRepository.findById(id).isPresent();
		Assert.assertFalse(isPresentAfterDelete);
	}
	
	@Test
	@Rollback(false)
	//@Order(4)
	@Sql("/test.sql")
	public void test_DeleteTicketById_3() {
		
		Integer id = 11;
		boolean isPresent = ticketBookingRepository.findById(id).isPresent();
		
		if (isPresent) {
			ticketBookingRepository.deleteById(id);
			Assert.assertTrue(isPresent);
		}
		
		boolean isPresentAfterDelete = ticketBookingRepository.findById(id).isPresent();
		Assert.assertFalse(isPresentAfterDelete);
	}
}