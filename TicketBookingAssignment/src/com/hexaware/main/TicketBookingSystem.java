package com.hexaware.main;

import java.text.ParseException;
import java.util.Scanner;

import com.hexaware.controller.Booking;

public class TicketBookingSystem {
    Booking booking = new Booking();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ParseException {
        TicketBookingSystem ticketBookingSystem = new TicketBookingSystem();

        while (true) {
            System.out.println("Welcome to the Ticket Booking System");
            System.out.println("1. Create Venue");
            System.out.println("2. Create Event");
            System.out.println("3. Display Event Details");
            System.out.println("4. Create Customer");
            System.out.println("5. Book Tickets");
            System.out.println("6. Cancel Tickets");
            System.out.println("7. Display Booking Details");
            System.out.println("8. Exit");
            System.out.println("Enter your choice:");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
            	case 1:
	                ticketBookingSystem.createVenue();
	                break;
                case 2:
                    ticketBookingSystem.createEvent();
                    break;
                case 3:
                    ticketBookingSystem.printEventDetails();
                    break;
                case 4:
                    ticketBookingSystem.registercustomer();
                    break;
                case 5:
                    ticketBookingSystem.bookTickets();
                    break;
                case 6:
                    ticketBookingSystem.cancelTickets();
                    break;
                case 7:
                    ticketBookingSystem.printBookingDetails();
                    break;
                case 8:
                    System.out.println("Thank you for using the Ticket Booking System. Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createEvent() throws ParseException {
        booking.addEvent();
    }
    
    
    private void createVenue() throws ParseException {
        booking.createVenueDetails();
    }

    private void printBookingDetails() {
        booking.displayBookingDetails1();
    }

    private void printEventDetails() {
        booking.displayEventDetails();
    }
    private void bookTickets() {
        booking.book_tickets();
    }

    private void cancelTickets() {
        booking.cancelTickets();
    }
    
    private void registercustomer() {
    	booking.createCustomer();
    }
    
}
