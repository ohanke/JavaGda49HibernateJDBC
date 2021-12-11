package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private String connectionString = "jdbc:hsqldb:file:db-data/mydatabase";
	private String userName = "admin123";
	private String password = "admin123";

	public Connection getConnection(){
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(connectionString, userName, password);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			if(connection != null ) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return connection;
	}
}
