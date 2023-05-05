package troubleTickets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dao {
	// instance fields
	static Connection connect = null;
	Statement statement = null;

	// constructor
	public Dao() {
	  
	}

	public Connection getConnection() {
		// Setup the connection with the DB
		try {
			connect = DriverManager
					.getConnection("jdbc:mysql://www.papademas.net:3307/tickets?autoReconnect=true&useSSL=false"
							+ "&user=fp411&password=411");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connect;
	}

	// CRUD implementation

	public void createTables() {
		// variables for SQL Query table creations
		final String createTicketsTable = "CREATE TABLE kcain_tickets(ticket_id INT AUTO_INCREMENT PRIMARY KEY, ticket_issuer VARCHAR(30), ticket_description VARCHAR(200))";
		final String createUsersTable = "CREATE TABLE kcain_users(uid INT AUTO_INCREMENT PRIMARY KEY, uname VARCHAR(30), upass VARCHAR(30), admin int)";

		try {

			// execute queries to create tables

			statement = getConnection().createStatement();

			statement.executeUpdate(createTicketsTable);
			statement.executeUpdate(createUsersTable);
			System.out.println("Created tables in given database...");

			// end create table
			// close connection/statement object
			statement.close();
			connect.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// add users to user table
		addUsers();
	}

	public void addUsers() {
		// add list of users from userlist.csv file to users table

		// variables for SQL Query inserts
		String sql;

		Statement statement;
		BufferedReader br;
		List<List<String>> array = new ArrayList<>(); // list to hold (rows & cols)

		// read data from file
		try {
			br = new BufferedReader(new FileReader(new File("./userlist.csv")));

			String line;
			while ((line = br.readLine()) != null) {
				array.add(Arrays.asList(line.split(",")));
			}
		} catch (Exception e) {
			System.out.println("There was a problem loading the file");
		}

		try {

			// Setup the connection with the DB

			statement = getConnection().createStatement();

			for (List<String> rowData : array) {

				sql = "insert into kcain_users(uname,upass,admin) " + "values('" + rowData.get(0) + "'," + " '"
						+ rowData.get(1) + "','" + rowData.get(2) + "');";
				statement.executeUpdate(sql);
			}
			System.out.println("Inserts completed in the given database...");

			// close statement object
			statement.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public int insertRecords(String ticketName, String ticketDesc) {
		int id = 0;
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate("Insert into kcain_tickets" + "(ticket_issuer, ticket_description) values(" + " '"
					+ ticketName + "','" + ticketDesc + "')", Statement.RETURN_GENERATED_KEYS);

			// retrieve ticket id number newly auto generated upon record insertion
			ResultSet resultSet = null;
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				// retrieve first field in table
				id = resultSet.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;

	}

	public ResultSet readRecords() {

		ResultSet results = null;
		try {
			statement = connect.createStatement();
			results = statement.executeQuery("SELECT * FROM kcain_tickets");
			//connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return results;
	}
public void deleteTicket(int id) {

		
		
		try {
			Statement preparedStatement = null;
			String sql = "DELETE FROM kcain_tickets " + "WHERE tid = ?";
			preparedStatement = connect.prepareStatement(sql);
			((PreparedStatement) preparedStatement).setInt(1, id);
			preparedStatement.executeUpdate(sql);
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
		
	}
	
	/**
	 * closeTicket() takes in the id of the ticket and changes the ticket's status from open to close and adds a end date to signify the time stamp of when the ticket is closed.
	 * @param id the id of the ticket that the user wants to close
	 */
	public void closeTicket(int id) {
		
		java.sql.Timestamp date2 = new java.sql.Timestamp(new java.util.Date().getTime());
		
		try {
			PreparedStatement preparedStatement = null;
			String sql = "UPDATE kcain_tickets " + "SET status = ?, end_date = ? WHERE tid = ?";
			preparedStatement = connect.prepareStatement(sql);
			preparedStatement.setString(1, "close");
			preparedStatement.setTimestamp(2, date2);
			preparedStatement.setInt(3, id);
			preparedStatement.executeUpdate();
			
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
		
	}
	
	/**
	 * updateTicket takes the id of the ticket and a String that holds what the user wants to update, with timestap
	 * @param id the id of the ticket that the user wants to update
	 * @param updates the things that the user wants to update to the ticket_desc
	 */
	public void updateTicket(int id, String updates) {
		
		java.sql.Timestamp date2 = new java.sql.Timestamp(new java.util.Date().getTime());
		
		try {
			statement = connect.createStatement();
		
			
			
			String sql = "SELECT ticket_desc FROM lchen_finalTicketSystem WHERE tid = '"+id+"'";
			ResultSet rs = statement.executeQuery(sql);
			
			String oldDesc = "";
			
			if(rs.next()) {
				oldDesc = rs.getString(1);
			}
			
			String newDesc = oldDesc + " \n\n" + updates + "\n" + "Updates as of " + date2;
			
			PreparedStatement preparedStatement = null;
			sql = "UPDATE kcain_tickets " + "SET ticket_desc = ? WHERE tid = ?";
			preparedStatement = connect.prepareStatement(sql);
			preparedStatement.setString(1, newDesc);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();

		    
		}
		catch (SQLException se) {
			se.printStackTrace();
		}

		
	}
	



	
	
	
}