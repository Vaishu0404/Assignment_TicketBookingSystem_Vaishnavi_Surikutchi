package com.hexaware.entity;

import java.time.LocalTime;

public class Movie extends Event {
    private String genre;
    private String actorName;
    private String actressName;

    public Movie(int eventId,String eventName, java.util.Date eventDate, LocalTime eventTime,int venueId, int totalSeats,
            int availableSeats, EventType eventType, int numTicketsSold, String genre, String actorName,
            String actressName) {
        super(eventId,eventName, eventDate, eventTime,venueId, totalSeats, availableSeats, eventType, numTicketsSold);
        this.genre = genre;
        this.actorName = actorName;
        this.actressName = actressName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActressName() {
        return actressName;
    }

    public void setActressName(String actressName) {
        this.actressName = actressName;
    }

    @Override
    public void printEventDetails(int eventId) {
        super.printEventDetails(eventId);
        System.out.println("Genre: " + genre);
        System.out.println("Actor Name: " + actorName);
        System.out.println("Actress Name: " + actressName);
    }

    @Override
    public String toString() {
        return "Movie [genre=" + genre + ", actorName=" + actorName + ", actressName=" + actressName + "]";
    }
}
