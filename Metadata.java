//Matthew Johnston & Michael Keeton
//CSCI 3410 Databases Lab3

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Metadata {
	static Connection connection = null;
	static DatabaseMetaData metadata = null;
	
	// Static block for initialization
	static {
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			System.err.println("There was an error getting the connection: "
					+ e.getMessage());
		}

		try {
			metadata = connection.getMetaData();
		} catch (SQLException e) {
			System.err.println("There was an error getting the metadata: "
					+ e.getMessage());
		}
	}

	/**
	 * Prints in the console the general metadata.
	 * 
	 * @throws SQLException
	 */
	public static void printGeneralMetadata() throws SQLException {
		System.out.println("Database Product Name: "
				+ metadata.getDatabaseProductName());
		System.out.println("Database Product Version: "
				+ metadata.getDatabaseProductVersion());
		System.out.println("Logged User: " + metadata.getUserName());
		System.out.println("JDBC Driver: " + metadata.getDriverName());
		System.out.println("Driver Version: " + metadata.getDriverVersion());
		System.out.println("Connected to database.");
		System.out.println("\n");
	}
	
	public static void getTablesMetadata() throws SQLException {
		java.sql.Statement stmt = null;
		String query = "show tables from company";
		
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Tables in company database: ");
			System.out.println("======================");
			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
			System.out.println("======================");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(stmt != null) {
				stmt.close();
			}
		}
	}
	
	public static void insert(String q) throws SQLException{
		java.sql.Statement stmt = null;
		String query = q;
		
		//Example insert
		//INSERT INTO company.employee VALUES ('Test', 'Testerson', 111222333, null, 1)
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);
			
			System.out.println("======================");
			System.out.println("Insertion successful.");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(stmt != null) {
				stmt.close();
			}
		}
	}
	
	public static void delete(String q) throws SQLException{
		java.sql.Statement stmt = null;
		String query = q;
		char choice;
		
		//Example insert
		//delete from company.employee where fname='franklin'
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);
			
			System.out.println("======================");
			System.out.println("Deletion successful.");
		}catch(SQLException e) {
			if(e instanceof MySQLIntegrityConstraintViolationException){
				Scanner scan = new Scanner(System.in);
				String input = "";
				System.out.println("The employee you wish to delete is an active manager. Proceed anyway? (y/n)");
				input = scan.next();
				choice = input.charAt(0);
				
				if(choice == 'y'){
					stmt.execute("SET FOREIGN_KEY_CHECKS=0");
					stmt.executeUpdate(query);
					
					System.out.println("======================");
					System.out.println("Deletion successful.");
				}else{
					System.out.println("Employee not deleted.");
				}
				//e.printStackTrace();
			}
		}finally {
			if(stmt != null) {
				stmt.close();
			}
		}
	}
	
	public static void search(String q) throws SQLException{
		java.sql.Statement stmt = null;
		String query = q;
		System.out.println("======================");
		
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(q);
	        System.out.println("Results for query: ");
	        
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int columnsNumber = rsmd.getColumnCount();
	        
	        System.out.print("| ");
	        for(int i = 1; i <= columnsNumber; i++){
	        	System.out.print(rsmd.getColumnName(i) + " | ");
	        }
	        System.out.println("");
	        
	        while (rs.next()) {
	            for (int i = 1; i <= columnsNumber; i++) {
	                if (i > 1) System.out.print(",  ");
	                String columnValue = rs.getString(i);
	                System.out.print(columnValue + " ");
	            }
	            System.out.println("");
	        }
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(stmt != null) {
				stmt.close();
			}
		}
	}
}