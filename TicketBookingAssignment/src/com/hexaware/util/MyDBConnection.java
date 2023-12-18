package com.hexaware.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDBConnection {

	static Connection con;
	public static Connection getMyDbConnection() {
		try {
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/TicketBookingSystem", "root", "mulio@05");
		} catch (SQLException e) {
		
			e.printStackTrace();
		}	
		
		return con;
	}
	
	public static void main(String[] args) {
		System.out.println(getMyDbConnection());

	}

}
