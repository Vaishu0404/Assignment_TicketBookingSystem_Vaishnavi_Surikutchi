package com.hexaware.controller;

import java.text.ParseException;

import com.hexaware.entity.Customer;
import com.hexaware.entity.Venue;

public interface BookingInterface {
	
	public void addEvent() throws ParseException ;
	public Customer createCustomer();
	public Venue createVenueDetails();
	public void book_tickets();
	public void cancelTickets();

	public int getAvailableNoOfTickets();
	
	void displayBookingDetails1();
	void displayEventDetails();

}
