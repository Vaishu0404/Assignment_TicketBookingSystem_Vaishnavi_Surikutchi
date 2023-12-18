package com.hexaware.entity;

import java.time.LocalTime;

public class Sports extends Event{
	
	private String sportName;
	private String teamsName;


	public Sports(int eventId,String eventName, java.util.Date eventDate, LocalTime eventTime,int venueId,int totalSeats,
            int availableSeats, EventType eventType, int numTicketsSold, String sportName, String teamsName) {
	    	super(eventId,eventName, eventDate, eventTime,venueId, totalSeats, availableSeats, eventType, numTicketsSold);
	        this.sportName = sportName;
	        this.teamsName = teamsName;
	    }
	    
	
	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public String getTeamsName() {
		return teamsName;
	}

	public void setTeamsName(String teamsName) {
		this.teamsName = teamsName;
	}
	 @Override
	    public void printEventDetails(int eventId) {
	        super.printEventDetails(eventId);
	        System.out.println("Sport Name: " + sportName);
	        System.out.println("Teams Name: " + teamsName);
	       
	    }
	 
	    @Override
		public String toString() {
			return "Sports [sportName=" + sportName + ", teamsName=" + teamsName + "]";
		}

}
