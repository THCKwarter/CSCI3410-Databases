//Matthew Johnston & Michael Keeton
//CSCI 3410 Databases Lab3

import java.sql.SQLException;
import java.util.Scanner;

public class Lab3Driver{
	public static void main(String[] args) throws SQLException{
		
		Scanner scan = new Scanner(System.in);
		boolean finished = false;
		String userQuery = "";
		int input = 0;
		
		//Connect to database
		Metadata.printGeneralMetadata();
		
		while(finished == false){
			//Get formula type and formula
			System.out.println("======================");
			System.out.println("Select an operation: ");
			System.out.println("Type '0' to exit.");
			System.out.println("Type '1' for metadata.");
			System.out.println("Type '2' to insert data.");
			System.out.println("Type '3' to delete data.");
			System.out.println("Type '4' to run an SQL query.");
			System.out.println("======================");
			input = scan.nextInt();
			scan.nextLine();
			
			//Example queries:
			//Example insert: INSERT INTO company.employee VALUES ('Test', 'Testerson', 111222333, null, 1)
			//Example deletion: delete from company.employee where fname='franklin'
			//Example search: select e.fname,e.lname,e.dno from company.employee as e where dno='5'
			
			switch(input){
				case(0):
					System.out.println("Goodbye.");
					finished = true;
					break;
				case(1):
					System.out.println("\n");
					//Print metadata
					try{
						Metadata.getTablesMetadata();
					}catch (SQLException e){
						System.err.println("There was an error retrieving the metadata properties: " + e.getMessage());
					}
					break;
				case(2):
					//Example insert
					//INSERT INTO company.employee VALUES ('Test', 'Testerson', 111222333, null, 1)
					System.out.println("Input your insertion query: ");
					userQuery = scan.nextLine();
					
					if(userQuery == ""){
						System.out.println("ERROR: Query was empty.");
						break;
					}else {
						Metadata.insert(userQuery);
					}
					break;
				case(3):
					//Franklin Wong 333445555 888665555 5
					//Example delete
					//DELETE FROM company.employee WHERE ssn='111222333'
					System.out.println("Input your deletion query: ");
					userQuery = scan.nextLine();
					
					if(userQuery == ""){
						System.out.println("ERROR: Query was empty.");
						break;
					}else {
						Metadata.delete(userQuery);
					}
					break;
				case(4):
					//Example query
					//select e.fname,e.lname,e.dno from company.employee as e where dno='5'
					System.out.println("Input your query: ");
					userQuery = scan.nextLine();
					
					if(userQuery == ""){
						System.out.println("ERROR: Query was empty.");
						break;
					}else {
						Metadata.search(userQuery);
					}
					break;
				default:
					System.out.println("Invalid input.");
			}
		}
	}
}