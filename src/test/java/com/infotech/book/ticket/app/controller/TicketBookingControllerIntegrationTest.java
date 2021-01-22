package com.infotech.book.ticket.app.controller;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infotech.book.ticket.app.TicketBookingManagementApplication;
import com.infotech.book.ticket.app.controllers.TicketBookingController;
import com.infotech.book.ticket.app.entities.Ticket;

//@AutoConfigureMockMvc //OK
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketBookingManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketBookingControllerIntegrationTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TicketBookingController ticketBookingController;

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	private HttpHeaders headers = new HttpHeaders();
	

	//@Autowired
	//private MockMvc mockMvc;

	/*@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello, World")));
	}*/
	
	@Test
	public void contextLoads() throws Exception {
		Assertions.assertThat(ticketBookingController).isNotNull();
		//Assertions.assertThat(mockMvc).isNotNull();
	}
	
	/**
	 * ##################################
	 * Insert Operation
	 * ##################################
	 */
	
	@Test
	public void test_CreateTicket() throws Exception {
		
		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		ticket.setPassengerName("Punyasmruti");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("punyasmruti@gmail.com");
		String expectedJsonStr = this.mapToJsonString(ticket);
		
		String URIToCreateTicket = "/api/tickets/create";
		HttpEntity<Ticket> entity = new HttpEntity<Ticket>(ticket, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(formFullURLWithPort(URIToCreateTicket),	HttpMethod.POST, entity, String.class);
		String responseInJson = response.getBody();
		Assertions.assertThat(responseInJson).isEqualTo(expectedJsonStr);
	}
	
	/**
	 * ##################################
	 * Select Operation
	 * ##################################
	 */
	
	

	@Test
	//@Sql("/test.sql")
	public void test_GetTicketById_1() throws Exception {
		
		Ticket ticket = new Ticket();
		ticket.setTicketId(2);
		ticket.setPassengerName("Pankaj");
		ticket.setSourceStation("Delhi");
		ticket.setDestStation("Chennai");
		ticket.setBookingDate(new Date());
		ticket.setEmail("pankaj@gmail.com");
		String expectedJsonStr = this.mapToJsonString(ticket);
		
		String URIToCreateTicket = "/api/tickets/create";
		HttpEntity<Ticket> entity = new HttpEntity<Ticket>(ticket, headers);
		ResponseEntity<String> resp = testRestTemplate.exchange(formFullURLWithPort(URIToCreateTicket),HttpMethod.POST, entity, String.class);
		Assert.assertNotNull(resp.getBody());
	
		String URI = "/api/tickets/ticketId/2";
	    String bodyJsonResponse = testRestTemplate.getForObject(formFullURLWithPort(URI), String.class);
	    Assertions.assertThat(bodyJsonResponse).isEqualTo(expectedJsonStr);
	}
	
	@Test
	@Sql("/test.sql")
	public void testGetTicketById_2() throws Exception {
		String URI = "/api/tickets/ticketId/11";
	    ResponseEntity<Ticket> t  = testRestTemplate.getForEntity(formFullURLWithPort(URI), Ticket.class);
	    Assert.assertNotNull(t.getBody());
	    Assertions.assertThat(t.getBody().getTicketId()).isEqualTo(11);
	    Assertions.assertThat(t.getBody().getPassengerName()).isEqualTo("punya");
	    Assertions.assertThat(t.getBody().getEmail()).isEqualTo("punya@gmail.com");
	}
	

	@Test
	public void testGetTicketByEmail() throws Exception {
		
		Ticket ticket = new Ticket();
		ticket.setTicketId(3);
		ticket.setPassengerName("Marry Johnson");
		ticket.setSourceStation("Delhi");
		ticket.setDestStation("Mumbai");
		ticket.setBookingDate(new Date());
		ticket.setEmail("marrry.j2017@gmail.com");
		
		String URI = "/api/tickets/create";
		
	    String inputInJson = this.mapToJsonString(ticket);
		
		HttpEntity<Ticket> entity = new HttpEntity<Ticket>(ticket, headers);
		testRestTemplate.exchange(formFullURLWithPort(URI),	HttpMethod.POST, entity, String.class);
		
		String URIToGetTicket = "/api/tickets/email/marrry.j2017@gmail.com";
		String bodyJsonResponse = testRestTemplate.getForObject(formFullURLWithPort(URIToGetTicket), String.class);
		Assertions.assertThat(bodyJsonResponse).isEqualTo(inputInJson);
	}
	
	/**
	 * ##################################
	 * Update Operation
	 * ##################################
	 */
	
	/**
	 * ##################################
	 * Delete Operation
	 * ##################################
	 */
	
	

	/**
	 * this utility method Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJsonString(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

    /**
     * This utility method to create the url for given uri. It appends the RANDOM_PORT generated port
     */
	private String formFullURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}