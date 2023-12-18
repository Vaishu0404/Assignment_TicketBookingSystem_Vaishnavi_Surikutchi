create database TicketBookingSystem;

use TicketBookingSystem;

create table Venue (venue_id int PRIMARY KEY,venue_name VARCHAR(255),address VARCHAR(255));
create table event (event_id int PRIMARY KEY,event_name VARCHAR(255),event_date DATE,event_time TIME,venue_id int,total_seats int,available_seats int,ticket_price DECIMAL,
event_type ENUM('Movie', 'Sports', 'Concert'),booking_id INT);

create table customer (customer_id INT PRIMARY KEY,customer_name VARCHAR(255),email VARCHAR(255),phone_number VARCHAR(20),booking_id INT);
create table Booking (booking_id INT PRIMARY KEY,customer_id INT,event_id INT,num_tickets INT,total_cost DECIMAL,booking_date TIMESTAMP);

alter table Booking add constraint fkey FOREIGN KEY(customer_id) references Customer(customer_id);
alter table Booking add constraint foreignkey FOREIGN KEY(event_id) references Event(event_id);

select * from Venue;
select * from Event;
select * from Booking;
select * from customer;