package com.hexaware.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.hexaware.entity.Concert;
import com.hexaware.entity.Customer;
import com.hexaware.entity.Event;
import com.hexaware.entity.Movie;
import com.hexaware.entity.Sports;
import com.hexaware.entity.Venue;
import com.hexaware.dao.*;
import com.hexaware.exception.CustomerExistException;
import com.hexaware.exception.EventExistsException;
import com.hexaware.exception.EventNotFoundException;
import com.hexaware.exception.InvalidBookingIDException;
import com.hexaware.exception.VenueExistsException;

public class Booking  extends Exception implements BookingInterface{
    private Event event = new Event();
    private Customer customer;
    private Venue venue = new Venue();
    private Scanner scanner = new Scanner(System.in);
    private Bookingdao bookingdao = new Bookingdao();


    public void addEvent() throws ParseException {
    	
    	try {
        System.out.println("Enter the Event Id");
        int eventId = scanner.nextInt();
        
        if (bookingdao.eventExists(eventId)) {
            throw new EventExistsException("Event with ID " + eventId + " exist.");
        }
    	
        System.out.println("Enter the Event name");
        String eventName = scanner.next();
        scanner.nextLine();

        System.out.println("Enter Event date (dd/MM/yyyy): ");
        String dateStr = scanner.next();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date eventDate = sdf.parse(dateStr);

        System.out.println("Enter Event time: ");
        String eventTime = scanner.next();
        LocalTime localTime = LocalTime.parse(eventTime);
        

        System.out.println("Enter Venue Id: ");
        int venueId = scanner.nextInt();
        
        System.out.println("Enter the Event Type (MOVIE/CONCERT/SPORTS):");
        String eventTypeStr = scanner.next();
        Event.EventType eventType = Event.EventType.valueOf(eventTypeStr.toUpperCase());

        switch (eventType) {
            case MOVIE:
                event = createMovie(eventId,eventName, eventDate, localTime,venueId);
                break;

            case CONCERT:
                event = createConcert(eventId,eventName, eventDate, localTime,venueId);
                break;
            case SPORTS:
                event = createSports(eventId,eventName, eventDate, localTime,venueId);
                break;

            default:
                System.out.println("Unsupported event type: " + eventTypeStr);
                return;
        }

        System.out.println("Enter total no. of seats: ");
        int totalSeats = scanner.nextInt();
        event.setTotalSeats(totalSeats);

        System.out.println("Enter the Available seats: ");
        int availableSeats = scanner.nextInt();
        event.setAvailableSeats(availableSeats);

        System.out.println("Enter the Ticket Price:");
        double ticketPrice = scanner.nextDouble();
        event.setTicketPrice(ticketPrice);     
        
        bookingdao.addEvent(event);
    	} catch (Exception e) {
        	System.out.println(e.getMessage());
        }
    }

    private Movie createMovie(int eventId,String eventName, Date eventDate, LocalTime eventTime,int venueId) {
        System.out.println("Enter movie genre: ");
        String genre = scanner.next();

        System.out.println("Enter actor name: ");
        String actorName = scanner.next();

        System.out.println("Enter actress name: ");
        String actressName = scanner.next();

        return new Movie(eventId,eventName, eventDate, eventTime,venueId, 0, 0, Event.EventType.MOVIE, 0, genre, actorName, actressName);
    }

    private Concert createConcert(int eventId,String eventName, Date eventDate, LocalTime eventTime,int venueId) {
        System.out.println("Enter artist name: ");
        String artistName = scanner.next();

        System.out.println("Enter music genre: ");
        String musicGenre = scanner.next();

        return new Concert(eventId,eventName, eventDate, eventTime,venueId, 0, 0, Event.EventType.CONCERT, 0, artistName, musicGenre);
    }

    private Sports createSports(int eventId,String eventName, Date eventDate, LocalTime eventTime,int venueId) {
        System.out.println("Enter sport type: ");
        String sportType = scanner.next();

        System.out.println("Enter Teams Name: ");
        String matchDescription = scanner.next();
        scanner.nextLine();

        return new Sports(eventId,eventName, eventDate, eventTime,venueId, 0, 0, Event.EventType.SPORTS, 0, sportType, matchDescription);
    }
    
    
    public Customer createCustomer() {
    	
    	try {
    		System.out.println("Enter Customer Id: ");
            int customerId = scanner.nextInt();
            if (bookingdao.CustomerExists(customerId)) {
                throw new CustomerExistException("Customer with ID " + customerId + " exist.");
            }
        System.out.println("Enter Customer Name: ");
        String customerName = scanner.next();

        System.out.println("Enter Customer Email: ");
        String email = scanner.next();

        System.out.println("Enter Customer Phone Number: ");
        String phoneNumber = scanner.next();

        Customer customer = new Customer(customerName, email, phoneNumber, customerId);
        bookingdao.addCustomer(customer);
        
    	}catch (Exception e) {
        	System.out.println(e.getMessage());
        }

        return customer;
    	
    }


    public Venue createVenueDetails() {
        try {
    	System.out.println("Enter the Venue Name:");
        String venueName = scanner.nextLine();
      
        System.out.println("Enter the Venue Address:");
        String address = scanner.nextLine();
        
    	
        System.out.println("Enter the Venue Id");
        int venueId = scanner.nextInt();
        scanner.nextLine();
        if (bookingdao.venueExists(venueId)) {
        	throw new VenueExistsException("Venue ID " + venueId + " already exist.");
            
        }
        
        Venue newVenue = new Venue(venueId, venueName, address);
        System.out.println("Venue ID to be inserted: " + newVenue.getVenueId());

        bookingdao.addVenue(newVenue);
        }
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
		return venue;
   
    }
  
    public void book_tickets() {
        try {
            System.out.println("Enter the Event Id: ");
            int eventId = scanner.nextInt();

            if (!bookingdao.eventExists(eventId)) {
                throw new EventNotFoundException("Event with ID " + eventId + " does not exist.");
            }

            System.out.println("Enter the Customer Id: ");
            int customerId = scanner.nextInt();

            
            if (!bookingdao.customerExists(customerId)) {
            	throw new CustomerExistException("Customer with ID " + customerId + " does not exist.");
            }

            int availableTickets = bookingdao.getAvailableTickets(eventId);
            System.out.println("Available Tickets for Event ID " + eventId + ": " + availableTickets);
            
            System.out.println("Enter the no. of tickets to book: ");
            int num = scanner.nextInt();

            
            double booked = event.bookTickets(num,eventId);
            double cost = event.calculateTotalRevenue(booked);
            bookingdao.bookTickets(eventId,customerId,booked,cost);

        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
    }

    public void cancelTickets() {
        try {
            System.out.println("Enter the Event Id: ");
            int eventId = scanner.nextInt();
            
            if (!bookingdao.eventExists(eventId)) {
            	throw new EventNotFoundException("Event with ID " + eventId + " does not exist.");
            }
            
            System.out.println("Enter the Customer Id: ");
            int customerId = scanner.nextInt();
 
            if (!bookingdao.customerExists(customerId)) {
            	throw new CustomerExistException("Customer with ID " + customerId + " does not exist.");
            }

            System.out.println("Enter the no. of tickets to be canceled: ");
            int numTickets = scanner.nextInt();

            event.cancelBooking( numTickets,eventId,customerId);
            bookingdao.cancelTickets(eventId,customerId,numTickets);

        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
    }


    public int getAvailableNoOfTickets() {
        return event.getAvailableSeats();
    }
    
 
    @Override
    public void displayEventDetails() {
        try {
            System.out.println("Enter the Event ID: ");
            int eventId = scanner.nextInt();
            
            if (!bookingdao.eventExists(eventId)) {
            	throw new EventNotFoundException("Event with ID " + eventId + " does not exist.");
            }
            event.printEventDetails(eventId);
            
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
    }

    @Override
    public void displayBookingDetails1() {
        try {
            System.out.println("Enter the Booking Id: ");
            int bookingId = scanner.nextInt();

            if (!bookingdao.BookingIdExists(bookingId)) {
                throw new InvalidBookingIDException("Booking with ID " + bookingId + " does not exist.");
            }
            
          
            bookingdao.displayBookingDetails(bookingId);

        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
    }


    }
