package com.infotech.book.ticket.app.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infotech.book.ticket.app.validator.custom.SourceAndDestinationValidation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name="ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ticket_id")
	private Integer ticketId;
	
	@Length(min=5,max=50,message="passenger name should be of min 5 and max 50 chars")
	@NotEmpty(message = "Please provide passenger name")
	@Column(name="passenger_name",nullable=false,length=100)
	private String passengerName;
	
	@Email
	@Column(name="email",unique=true)
	private String email;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="booking_date",nullable=false)
	private Date bookingDate;
	
	@SourceAndDestinationValidation //custom validator
	@Column(name="source_station",nullable=false)
	private String sourceStation;
	
	@SourceAndDestinationValidation //custom validator
	@Column(name="dest_station",nullable=false)
	private String destStation;
	
	 /**
     * Password must be between 4 and 8 digits/characters long and include at least one numeric digit.
     */
    //@Pattern(regexp = "^(?=.*\\d).{4,8}$")
    //private String password;
	
	
	/*public Integer getTicketId() {
		return ticketId;
	}
	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getSourceStation() {
		return sourceStation;
	}
	public void setSourceStation(String sourceStation) {
		this.sourceStation = sourceStation;
	}
	public String getDestStation() {
		return destStation;
	}
	public void setDestStation(String destStation) {
		this.destStation = destStation;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", passengerName=" + passengerName + ", bookingDate=" + bookingDate
				+ ", sourceStation=" + sourceStation + ", destStation=" + destStation + ", email=" + email + "]";
	}*/
}
