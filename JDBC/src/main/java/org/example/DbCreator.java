package org.example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class DbCreator {

    static Connection con;
    static String connectionString = "jdbc:hsqldb:file:db-data/mydatabase;user=admin123;password=admin123";

	public void createAndLoadData() throws Exception {
		String createContacts = readToString("sql/HR_create.sql");
		String populateContacts = readToString("sql/HR_loadData.sql");

		System.out.println("Attempting to create contacts DB ... ");

		try {
			con = DriverManager.getConnection(connectionString, "admin123", "admin123");

			con.createStatement().executeUpdate(createContacts);
			con.createStatement().executeUpdate(populateContacts);

		} catch (SQLException e) {
			throw e;
		} finally {
			con.close();
		}
	}

	public static String readToString(String fname) throws Exception {
		File file = new File(fname);
		return FileUtils.readFileToString(file, "utf-8");
	}
	
	
}
