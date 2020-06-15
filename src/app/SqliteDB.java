package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteDB {
	
	Connection c = null;
	Statement stmt = null;
	
	SqliteDB() {
		// establishing connection to DB
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:InformationDB.sqlite");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	/* ---------------- Users --------------- */
	
	public void listUsers() {
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
			    for (int i = 1; i <= columnsNumber; i++) {
			        if (i > 1) System.out.print(",  ");
			        String columnValue = rs.getString(i);
			        System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
			    }
			    System.out.println("");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void newUser(String username) {
		try {
			this.stmt = c.createStatement();
			stmt.executeUpdate("INSERT INTO Users ('username') VALUES ('" + username + "')");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void enterBooked(User cur, Boolean state) {
		try {
			this.stmt = c.createStatement();
			int val = 0;
			if (state == true) val = 1;
			
			stmt.executeUpdate(
					"UPDATE Users SET booked = '" + val + "' "
						+ "WHERE id = " + cur.getIndex() 
					);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public boolean userExists(String username) {
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT EXISTS (SELECT 1 FROM Users WHERE username='" + username + "' LIMIT 1)");
			while (rs.next()) {
				if (rs.getBoolean(1) == true) return true;
				else return false;
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return false;
	}
	
	
	/* ---------------- Flights --------------- */
	
	public void listFlights() {
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Flights");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
			    for (int i = 1; i <= columnsNumber; i++) {
			        if (i > 1) System.out.print(",  ");
			        String columnValue = rs.getString(i);
			        String columnTitle = rsmd.getColumnName(i).substring(0, 1).toUpperCase() + rsmd.getColumnName(i).substring(1);
			        if (rsmd.getColumnName(i).equals("id")) columnTitle = "ID";
			        if (rsmd.getColumnName(i).equals("price")) columnValue = "$" + columnValue;
			        if (rsmd.getColumnName(i).equals("type")) {
			        	if (columnValue.equals("0")) columnValue = "One Way";
			        	else columnValue = "Round Trip";
			        }
			        System.out.print(columnTitle + ": " + columnValue);
			    }
			    System.out.println("");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	
	/* ---------------- Other --------------- */
	
	public void executeQuery(String query) {
		try {
			this.stmt = c.createStatement();
			stmt.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void closeConnection() {
		try {
			c.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
	}
	
	public void enterInfo(User cur, String table, String data, String info) {
		try {
			this.stmt = c.createStatement();
			stmt.executeUpdate(
					"UPDATE " + table + " "
					+ "SET " + data + " = '" + info + "' "
							+ "WHERE id = " + cur.getIndex() 
					);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
}