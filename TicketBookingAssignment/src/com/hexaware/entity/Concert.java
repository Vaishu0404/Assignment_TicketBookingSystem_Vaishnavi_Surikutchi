package com.hexaware.entity;

import java.time.LocalTime;

public class Concert extends Event {

	private String type;
	private String artist;

	public Concert(int eventId,String eventName, java.util.Date eventDate, LocalTime eventTime,int venueId, int totalSeats,
            int availableSeats, EventType eventType, int numTicketsSold,String artist,String type) {
    	 super(eventId,eventName, eventDate, eventTime,venueId, totalSeats, availableSeats, eventType, numTicketsSold);
        this.artist = artist;
        this.type = type;
    }
   
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	 @Override
	    public void printEventDetails(int eventId) {
	        super.printEventDetails(eventId);
	        System.out.println("Concert Type: " + type);
	        System.out.println("Artist Name: " + artist);
	 }

    @Override
	public String toString() {
		return "Concert [artist=" + artist + ", type=" + type + "]";
	}
}

