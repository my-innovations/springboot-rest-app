package com.infotech.book.ticket.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infotech.book.ticket.app.controllers.TicketBookingController;
import com.infotech.book.ticket.app.entities.Ticket;
import com.infotech.book.ticket.app.services.TicketBookingServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TicketBookingController.class, secure = false) // This will load full Spring application context is started but without the server.
public class TicketBookingControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;
	
	@SuppressWarnings("unused")
	@Autowired
	private TicketBookingController ticketBookingController;

	@MockBean
	private TicketBookingServiceImpl ticketBookingService;

	@Test
	public void testCreateTicket() throws Exception {

		Ticket mockTicket = new Ticket();
		mockTicket.setTicketId(1);
		mockTicket.setPassengerName("Martin Bingel");
		mockTicket.setSourceStation("Chennai");
		mockTicket.setDestStation("Mumbai");
		mockTicket.setBookingDate(new Date());
		mockTicket.setEmail("martin.s2017@gmail.com");

		String inputInJson = this.mapToJson(mockTicket);

		String URI = "/api/tickets/create";

		Mockito.when(ticketBookingService.createTicket(Mockito.any(Ticket.class))).thenReturn(mockTicket);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON).content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		Assertions.assertThat(outputInJson).isEqualTo(inputInJson);
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void testGetTicketById() throws Exception {
		
		Ticket mockTicket = new Ticket();
		mockTicket.setTicketId(1);
		mockTicket.setPassengerName("punya");
		mockTicket.setSourceStation("Chennai");
		mockTicket.setDestStation("Delhi");
		mockTicket.setBookingDate(new Date());
		mockTicket.setEmail("martin.s2017@gmail.com");
		
		Mockito.when(ticketBookingService.getTicketById(Mockito.anyInt())).thenReturn(mockTicket);
		
		String URI = "/api/tickets/ticketId/1";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(mockTicket);
		String outputInJson = result.getResponse().getContentAsString();
		Assertions.assertThat(outputInJson).isEqualTo(expectedJson);
		JSONAssert.assertEquals(expectedJson, outputInJson, false);
	}

	@Test
	public void testGetAllBookedTickets() throws Exception {

		Ticket mockTicket1 = new Ticket();
		mockTicket1.setTicketId(1);
		mockTicket1.setPassengerName("Martin Bingel");
		mockTicket1.setSourceStation("Kolkata");
		mockTicket1.setDestStation("Delhi");
		mockTicket1.setBookingDate(new Date());
		mockTicket1.setEmail("martin.s2017@gmail.com");

		Ticket mockTicket2 = new Ticket();
		mockTicket2.setPassengerName("Sean Murphy");
		mockTicket2.setSourceStation("Kolkata");
		mockTicket2.setDestStation("Mumbai");
		mockTicket2.setBookingDate(new Date());
		mockTicket2.setEmail("sean.s2017@gmail.com");

		List<Ticket> ticketList = new ArrayList<>();
		ticketList.add(mockTicket1);
		ticketList.add(mockTicket2);

		Mockito.when(ticketBookingService.getAllBookedTickets()).thenReturn(ticketList);

		String URI = "/api/tickets/alltickets";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(ticketList);
		String outputInJson = result.getResponse().getContentAsString();
		
		Assertions.assertThat(outputInJson).isEqualTo(expectedJson);
	}

	@Test
	public void testGetTicketByEmail() throws Exception {
	
		Ticket mockTicket = new Ticket();
		mockTicket.setTicketId(1);
		mockTicket.setPassengerName("Martin Bingel");
		mockTicket.setSourceStation("Kolkata");
		mockTicket.setDestStation("Delhi");
		mockTicket.setBookingDate(new Date());
		mockTicket.setEmail("martin.s2017@gmail.com");

		String expectedJson = this.mapToJson(mockTicket);

		Mockito.when(ticketBookingService.getTicketByEmail(Mockito.anyString())).thenReturn(mockTicket);

		String URI = "/api/tickets/email/martin.s2017@gmail.com";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		Assertions.assertThat(outputInJson).isEqualTo(expectedJson);

	}

	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
