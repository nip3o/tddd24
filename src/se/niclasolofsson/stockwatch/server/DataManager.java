package se.niclasolofsson.stockwatch.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import se.niclasolofsson.stockwatch.client.DelistedException;

public class DataManager {
	private Connection conn;
	
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:populations.db");

		} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
		} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
		}
	}
	
	public void create() {
		Statement s;
		try {
			s = conn.createStatement();
			
			s.executeUpdate("DROP TABLE IF EXISTS populations");
			s.executeUpdate("CREATE TABLE populations (name STRING, population INTEGER)");
			
			s.executeUpdate("INSERT INTO populations VALUES ('Sweden', 9540065)");
			s.executeUpdate("INSERT INTO populations VALUES ('USA', 314159265)");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getPopulation(String countryName) throws DelistedException {
		PreparedStatement s;
		int res = 0;
		
		try {
			s = conn.prepareStatement("SELECT * FROM populations WHERE name = ?");
			s.setString(1, countryName);
			ResultSet rs = s.executeQuery();
			
			if(rs.next()) {
				res = rs.getInt("population");
			} else {
				 throw new DelistedException(countryName);
			}
			
	        rs.close();
        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public void addPopulationsFromString(String data) {
		String[] rows = data.split("\n");
		String[] attrs;
		
		try {
			PreparedStatement s = conn.prepareStatement("INSERT INTO populations VALUES (?, ?)");

			for(String row : rows) {
				attrs = row.split(" ");
				s.setString(1, attrs[0]);
				s.setInt(2, Integer.parseInt(attrs[1]));
				s.execute();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
        try {
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
