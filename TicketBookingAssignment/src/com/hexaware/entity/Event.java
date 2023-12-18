package com.hexaware.entity;

import java.util.Date;

import com.hexaware.dao.Bookingdao;

import java.time.LocalTime;

public class Event {
	
	private int eventId;
	private String eventName;
    private Date eventDate;
    private LocalTime eventTime;
    private int venueId;
    private int totalSeats;
    private int availableSeats;
    private static double ticketPrice;
    private EventType eventType;
    private int numTicketsSold;
    
    private Bookingdao bookingdao = new Bookingdao();

    public enum EventType {
        MOVIE, SPORTS, CONCERT
    }

    public Event() {
        
    }

    public Event(int eventId,String eventName, Date eventDate, LocalTime eventTime, int venueId,int totalSeats,
            int availableSeats, EventType eventType, int numTicketsSold) {
        super();
    	this.eventId=eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.venueId = venueId; 
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.eventType = eventType;
        this.numTicketsSold = numTicketsSold;
    }
    
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public int getVenueId() {
		return venueId;
	}

	public void setVenueId(int venueId) {
		this.venueId = venueId;
	}
	
    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void printEventDetails(int eventId) {
    	Event eventDetails = bookingdao.getEventDetails(eventId);
    	
    	if (eventDetails != null) {
            System.out.println("Event Id: " + eventDetails.getEventId());
            System.out.println("Event Name: " + eventDetails.getEventName());
            System.out.println("Event Date: " + eventDetails.getEventDate());
            System.out.println("Event Time: " + eventDetails.getEventTime());
            System.out.println("Venue Id: " + eventDetails.getVenueId());
            System.out.println("Total Seats: " + eventDetails.getTotalSeats());
            System.out.println("Available Seats: " + eventDetails.getAvailableSeats());
            System.out.println("Ticket Price: $" + eventDetails.getTicketPrice());
            System.out.println("Event Type: " + eventDetails.getEventType());
            
        } else {
            System.out.println("No event found for the given event ID.");
        }
    }

    private int num = 0;

    public int getBookedNoOfTickets() {
        return num;
    }

    public int getNumTicketsSold() {
        return totalSeats - getBookedNoOfTickets();
    }

    public double calculateTotalRevenue(double booked) {
        return booked * ticketPrice;
    }

    public double bookTickets(int numTickets,int eventId) {
    	availableSeats=bookingdao.getAvailableTickets(eventId);
        if (numTickets > 0 && numTickets <= availableSeats) {
            availableSeats = availableSeats - numTickets;
            num += numTickets;
            System.out.println(numTickets + " tickets booked successfully!");
        } else {
            System.out.println("Invalid number of tickets to book or insufficient seats available.");
        }
        
		return numTickets;
    }

    public double cancelBooking(int numTickets,int eventId,int customerId) {
    	availableSeats=bookingdao.getAvailableTickets(eventId);
        if (numTickets > 0 && numTickets <= availableSeats) {
            availableSeats += numTickets;
            System.out.println(numTickets + " tickets canceled successfully!");
        } else {
            System.out.println("Invalid number of tickets to cancel or insufficient booked tickets.");
        }
        return numTickets;
    }
    @Override
	public String toString() {
		return "Event [eventId=" + eventId + ", eventName=" + eventName + ", eventDate=" + eventDate + ", eventTime="
				+ eventTime + ", venueId=" + venueId + ", totalSeats=" + totalSeats + ", availableSeats="
				+ availableSeats + ", eventType=" + eventType + ", numTicketsSold=" + numTicketsSold + ", num=" + num
				+ "]";
	}
    
   
}
