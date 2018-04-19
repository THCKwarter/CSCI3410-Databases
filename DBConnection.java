//Matthew Johnston & Michael Keeton
//CSCI 3410 Databases Lab3

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static String DB_URL = "jdbc:mysql://localhost:3306/mysql";
	private static String DB_USER = "root";
	private static String DB_PASSWORD = "root";
	
	public static Connection getConnection() throws SQLException{
		Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		System.err.println("Connected to database.");
		return conn;
	}
}