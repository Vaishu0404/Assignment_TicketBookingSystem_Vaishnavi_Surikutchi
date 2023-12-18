package com.hexaware.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.hexaware.entity.Customer;
import com.hexaware.entity.Event;
import com.hexaware.entity.Venue;
import com.hexaware.util.MyDBConnection;

public class Bookingdao {
	Connection connection;
	Statement statement;
	ResultSet rs;

    public void addEvent(Event event) {
        try {
        	connection = MyDBConnection.getMyDbConnection();
        	 
            String query = "INSERT INTO event (event_id, event_name, event_date, event_time, venue_id, total_seats, available_seats, ticket_price, event_type) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, event.getEventId());
                ps.setString(2, event.getEventName());
                java.sql.Date sqlEventDate = new java.sql.Date(event.getEventDate().getTime());
                ps.setDate(3, sqlEventDate);
                ps.setTime(4, java.sql.Time.valueOf(event.getEventTime()));
                ps.setInt(5, event.getVenueId());
                ps.setInt(6, event.getTotalSeats());
                ps.setInt(7, event.getAvailableSeats());
                ps.setDouble(8, event.getTicketPrice());
                ps.setString(9, event.getEventType().name());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Event added to the database successfully.");
                } else {
                    System.out.println("Failed to add event to the database.");
                }
        	 }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addVenue(Venue venue) {
        try {
            connection = MyDBConnection.getMyDbConnection();

            String query = "INSERT INTO venue (venue_id, venue_name, address) " +
                    "VALUES (?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
            	ps.setInt(1, venue.getVenueId());
                ps.setString(2, venue.getVenueName());
                ps.setString(3, venue.getAddress());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Venue added to the database successfully.");
                } else {
                    System.out.println("Failed to add venue to the database.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addCustomer(Customer customer) {
        try {
            connection = MyDBConnection.getMyDbConnection();

            String query = "INSERT INTO customer (customer_name, email, phone_number, customer_id) " +
                    "VALUES (?, ?, ?, ?)";
			    try (PreparedStatement ps = connection.prepareStatement(query)) {
			        ps.setString(1, customer.getCustomerName());
			        ps.setString(2, customer.getEmail());
			        ps.setString(3, customer.getPhoneNumber());
			        ps.setInt(4, customer.getCustomerId());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Customer added to the database successfully.");
                } else {
                    System.out.println("Failed to add customer to the database.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean eventExists(int eventId) {
        boolean exists = false;
        try {
            
            Connection connection = MyDBConnection.getMyDbConnection();
            String query = "SELECT * FROM event WHERE event_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, eventId);

                try (ResultSet rs = ps.executeQuery()) {
                    
                    if (rs.next()) {
                        exists = true; 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return exists;
    }
    public boolean customerExists(int customerId) {
        boolean exists = false;
        try {
            
            Connection connection = MyDBConnection.getMyDbConnection();

            String query = "SELECT * FROM customer WHERE customer_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, customerId);

                try (ResultSet rs = ps.executeQuery()) {
                    
                    if (rs.next()) {
                        exists = true; 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return exists;
    }
    public int getAvailableTickets(int eventId) {
        try {
            connection = MyDBConnection.getMyDbConnection();

            String query = "SELECT available_seats FROM event WHERE event_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, eventId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("available_seats");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public void bookTickets(int eventId, int customerId, double booked,double cost) {
        try {
            connection = MyDBConnection.getMyDbConnection();
            int availableTickets = getAvailableTickets(eventId);
            if (booked > 0 && booked <= availableTickets) {
            	int bookingId = generateUniqueBookingId();

                LocalDate currentDate = LocalDate.now();
                String bookingQuery = "INSERT INTO booking (booking_id, event_id, customer_id, num_tickets,total_cost,booking_date) VALUES (?, ?, ?, ?,?,?)";
                try (PreparedStatement bookingPs = connection.prepareStatement(bookingQuery)) {
                    bookingPs.setInt(1, bookingId);
                    bookingPs.setInt(2, eventId);
                    bookingPs.setInt(3, customerId);
                    bookingPs.setDouble(4, booked);
                    bookingPs.setDouble(5, cost);
                    java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
                    bookingPs.setDate(6, sqlDate);
                    bookingPs.executeUpdate();
                }

                String updateEventQuery = "UPDATE event SET available_seats = available_seats - ?, booking_id = ? WHERE event_id = ?";
                try (PreparedStatement updateEventPs = connection.prepareStatement(updateEventQuery)) {
                    updateEventPs.setDouble(1, booked);
                    updateEventPs.setInt(2, bookingId);
                    updateEventPs.setInt(3, eventId);
                    updateEventPs.executeUpdate();
                }

                String updateCustomerQuery = "UPDATE customer SET booking_id = ? WHERE customer_id = ?";
                try (PreparedStatement updateCustomerPs = connection.prepareStatement(updateCustomerQuery)) {
                    updateCustomerPs.setInt(1, bookingId);
                    updateCustomerPs.setInt(2, customerId);
                    updateCustomerPs.executeUpdate();
                }

                // Display the unique booking ID
                System.out.println("Booking ID: " + bookingId);

            } else {
                System.out.println("Invalid number of tickets to book or insufficient seats available.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int generateUniqueBookingId() throws SQLException {
        String maxBookingIdQuery = "SELECT MAX(booking_id) FROM booking";
        try (PreparedStatement maxBookingIdPs = connection.prepareStatement(maxBookingIdQuery);
             ResultSet resultSet = maxBookingIdPs.executeQuery()) {
            if (resultSet.next()) {
                int maxBookingId = resultSet.getInt(1);
                return maxBookingId + 1;
            } else {
                return 1000;
            }
        }
    }
    
    public void cancelTickets(int eventId, int customerId, int numTickets) {
        try {
            connection = MyDBConnection.getMyDbConnection();
            int bookingId = getBookingIdForCustomer(customerId);
            
            if (bookingId > 0) {
                String updateEventQuery = "UPDATE event SET available_seats = available_seats + ?, booking_id = NULL WHERE event_id = ?";
                try (PreparedStatement updateEventPs = connection.prepareStatement(updateEventQuery)) {
                    updateEventPs.setInt(1, numTickets);
                    updateEventPs.setInt(2, eventId);
                    updateEventPs.executeUpdate();
                }

                String deleteBookingQuery = "DELETE FROM booking WHERE booking_id = ?";
                try (PreparedStatement deleteBookingPs = connection.prepareStatement(deleteBookingQuery)) {
                    deleteBookingPs.setInt(1, bookingId);
                    deleteBookingPs.executeUpdate();
                }

               
                String updateCustomerQuery = "UPDATE customer SET booking_id = NULL WHERE customer_id = ?";
                try (PreparedStatement updateCustomerPs = connection.prepareStatement(updateCustomerQuery)) {
                    updateCustomerPs.setInt(1, customerId);
                    updateCustomerPs.executeUpdate();
                }

                System.out.println("Booking canceled successfully!");
            } else {
                System.out.println("No valid booking found for the customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getBookingIdForCustomer(int customerId) {
        try {
            String query = "SELECT booking_id FROM customer WHERE customer_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, customerId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("booking_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void displayBookingDetails(int bookingId) {
        try {
            connection = MyDBConnection.getMyDbConnection();
            String query = "SELECT * FROM booking b JOIN event e ON b.event_id = e.event_id " +
                           "JOIN venue v ON e.venue_id = v.venue_id " +
                           "WHERE b.booking_id=?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, bookingId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int booking_Id = rs.getInt("booking_id");
                        int numTickets = rs.getInt("num_tickets");
                        int eventId = rs.getInt("event_id");
                        int customerId = rs.getInt("customer_id");
                        double totalCost = rs.getDouble("total_cost");
                        LocalDate bookingDate = rs.getDate("booking_date").toLocalDate();

                        int venueId = rs.getInt("venue_id");
                        String venueName = rs.getString("venue_name");
                        String venueAddress = rs.getString("address");

                        System.out.println("Booking ID: " + bookingId);
                        System.out.println("Event ID: " + eventId);
                        System.out.println("Customer ID: " + customerId);
                        System.out.println("Number of Tickets: " + numTickets);
                        System.out.println("Total Cost: $" + totalCost);
                        System.out.println("Booking Date: " + bookingDate);
                        System.out.println("Venue ID: " + venueId);
                        System.out.println("Venue Name: " + venueName);
                        System.out.println("Venue Address: " + venueAddress);
                    } else {
                        System.out.println("No booking found for the given event and customer.");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Event getEventDetails(int eventId) {
        Event event = null;
        try {
            connection = MyDBConnection.getMyDbConnection();

            String query = "SELECT * FROM event WHERE event_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, eventId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        event = new Event();
                        event.setEventId(rs.getInt("event_id"));
                        event.setEventName(rs.getString("event_name"));
                        event.setEventDate(rs.getDate("event_date"));
                        event.setEventTime(rs.getTime("event_time").toLocalTime());
                        event.setVenueId(rs.getInt("venue_id"));
                        event.setTotalSeats(rs.getInt("total_seats"));
                        event.setAvailableSeats(rs.getInt("available_seats"));
                        event.setTicketPrice(rs.getDouble("ticket_price"));
                        event.setEventType(Event.EventType.valueOf(rs.getString("event_type").toUpperCase()));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }
    public boolean venueExists(int venueId) {
        boolean exists = false;
        try {
            
            Connection connection = MyDBConnection.getMyDbConnection();
            String query = "SELECT * FROM venue WHERE venue_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, venueId);

                try (ResultSet rs = ps.executeQuery()) {
                    
                    if (rs.next()) {
                        exists = true; 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return exists;
    }
	public boolean CustomerExists(int customerId) {
		boolean exists = false;
        try {
            
            Connection connection = MyDBConnection.getMyDbConnection();
            String query = "SELECT * FROM customer WHERE customer_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, customerId);

                try (ResultSet rs = ps.executeQuery()) {
                    
                    if (rs.next()) {
                        exists = true; 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return exists;
	}
	
	public boolean BookingIdExists(int bookingId) {
		boolean exists = false;
        try {
            
            Connection connection = MyDBConnection.getMyDbConnection();
            String query = "SELECT * FROM booking WHERE booking_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, bookingId);

                try (ResultSet rs = ps.executeQuery()) {
                    
                    if (rs.next()) {
                        exists = true; 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return exists;
	}

}
