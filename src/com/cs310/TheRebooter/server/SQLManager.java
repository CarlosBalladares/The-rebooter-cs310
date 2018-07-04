package com.cs310.TheRebooter.server;


import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import com.google.appengine.api.utils.SystemProperty;
public class SQLManager {
	private static final String databaseIP = "173.194.244.58";
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private final String userName="admin";
	private final String password="rebootermysqlpassword";
	 Logger log = Logger.getLogger(BusinessServiceImpl.class.getName());


	public SQLManager()
	{
		try {
			connect = getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//blatantly inspired by http://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);
		conn = DriverManager.getConnection(
				"jdbc:mysql://" +
						databaseIP +
						":3306/",
						connectionProps);
		System.out.println("Connected to database");
		return conn;
	}
	
	public Connection getSuperConnection(){
		Connection conn = null;
	    try {
	      if (SystemProperty.environment.value() ==
	          SystemProperty.Environment.Value.Production) {
	        // Load the class that provides the new "jdbc:google:mysql://" prefix.
	        Class.forName("com.mysql.jdbc.GoogleDriver");
	        String url = "jdbc:google:mysql://mythic-display-87621:rebootersql/mysql?user="+this.userName;
	        conn = DriverManager.getConnection(url);
	      } else {
	        // Local MySQL instance to use during development.
	    	  Class.forName("com.mysql.jdbc.Driver");
	       conn=getConnection();
	        
	        // Alternatively, connect to a Google Cloud SQL instance using:
	        // jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/guestbook?user=root
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	      return null;
	    }
	    
	    return conn;
	}
	
	public Connection appengineconnection(){
		Connection conn=null;
		
		try {
		      
		        // Load the class that provides the new "jdbc:google:mysql://" prefix.
		        Class.forName("com.mysql.jdbc.GoogleDriver");
		        String url = "jdbc:google:mysql://cloudsql/therebooter-908:rebootersql/mysql?user=admin";
		        conn = DriverManager.getConnection(url);
		      
		    } catch (SQLException e) {
		     e.printStackTrace();
		     log.warning("lol: "+ e.getMessage()+"sql");
		    } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			     log.warning("lol: "+ e.getMessage()+"class");
			     log.warning(e.getStackTrace().toString());

			}
		
		return conn;
	}
	
	public void writeCSVFileToDataBase(String csvFile,String dataTable) throws SQLException {
		try {
			Parser parser = new Parser(csvFile,dataTable);
			ArrayList<String> statements = (ArrayList<String>) parser.ReadCSV();
			System.out.println("CSV Parsed");
			int[] successes;
			int success=0;
			
			statement = connect.createStatement();
			statement.addBatch("USE mysql");
			while(statements.size()>=2000){
				for(int i = 0;i<2000;i++)
				{
					statement.addBatch(statements.get(statements.size()-1));
					statements.remove(statements.size()-1);
				}
				successes = statement.executeBatch();
				statement.clearBatch();

				for(int i: successes)
				{
					if(i>=0)
					{
						success++;
					}
				}
				System.out.print("2000 statements processed");
			}
			for(String state: statements)
			{
				statement.addBatch(state);
			}
			successes = statement.executeBatch();
			statement.clearBatch();

			for(int i: successes)
			{
				if(i>=0)
				{
					success++;
				}
			}

			System.out.println(success+" of "+statements.size()+" statements executed successfully");

		} catch (SQLException e) {
			throw e;
		} 
	}

}
