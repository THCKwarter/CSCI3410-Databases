import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

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
}