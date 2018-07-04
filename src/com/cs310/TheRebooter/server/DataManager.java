package com.cs310.TheRebooter.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cs310.TheRebooter.shared.Business;
import com.cs310.TheRebooter.shared.Coordinate;
import com.google.appengine.api.utils.SystemProperty;
//Some code borrowed from https://github.com/GoogleCloudPlatform/appengine-cloudsql-native-mysql-datanucleus-jdo-demo-java/blob/master/src/main/java/com/google/appengine/demos/DatanucleusJdoServlet.java
public class DataManager extends HttpServlet {
	private static final String databaseIP = "173.194.244.58";
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private final String userName="admin";
	private final String password="rebootermysqlpassword";
	private final SQLManager manager = new SQLManager();
	
	private static final Logger log = Logger.getLogger(DataManager.class.getName()); 
	
	//Distance must be a positive number
	public List<Business> getBusinessesInVicinity(Coordinate baseLocation,double distanceInDegrees) throws SQLException
	{
		//Note: this method selects in a SQUARE area around the given base location, due to limitations of SQL and JDOQL
	/*	Map<String, String> properties = new HashMap();

			properties.put("javax.jdo.option.ConnectionDriverName",
					"com.mysql.jdbc.GoogleDriver");
			properties.put("javax.jdo.option.ConnectionURL",
					System.getProperty("jdbc:mysql://173.194.244.58/mysql"));
			properties.put("javax.jdo.option.ConnectionUserName",userName);
			properties.put("javax.jdo.option.ConnectionPassword",password);

		PersistenceManagerFactory pmf =
				JDOHelper.getPersistenceManagerFactory(properties);

		// Insert a few rows.
		PersistenceManager pm = pmf.getPersistenceManager();
	
		Transaction tx = pm.currentTransaction();
		Map<String, String> paramValues = new HashMap();
		double upperLat,lowerLat,upperLon,lowerLon;
		upperLat = baseLocation.getLatitude()+distanceInDegrees;
		lowerLat = baseLocation.getLatitude()-distanceInDegrees;
		upperLon = baseLocation.getLongitude()+distanceInDegrees;
		lowerLon = baseLocation.getLongitude()-distanceInDegrees;
		paramValues.put("lowerlat", String.valueOf(lowerLat));
		paramValues.put("upperlat", String.valueOf(upperLat));
		paramValues.put("lowerlon", String.valueOf(lowerLon));
		paramValues.put("upperlon", String.valueOf(upperLon));
		Query query = pm.newQuery(javax.jdo.Query.JDOQL,"SELECT * from Business business_licences WHERE (Latitude >= "+lowerLat+") && (Latitude <= "+upperLat+") && (Longitude >= "+lowerLon+") && (Longitude <= "+upperLon+")");
		query.setResultClass(Business.class);
		List<Business> results = null;
		System.out.println(query.toString());
		try{
		System.out.println(query.execute());
		results = (List<Business>) query.execute();
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw e;
		}
		//",
		
		return results; */
		return getBusinessesSQL(baseLocation,distanceInDegrees);
	}
	private List<Business> getBusinessesSQL(Coordinate baseLocation,double distanceInDegrees)
		    throws SQLException {
		double upperLat,lowerLat,upperLon,lowerLon;
		upperLat = baseLocation.getLatitude()+distanceInDegrees;
		lowerLat = baseLocation.getLatitude()-distanceInDegrees;
		upperLon = baseLocation.getLongitude()+distanceInDegrees;
		lowerLon = baseLocation.getLongitude()-distanceInDegrees;
		
		log.info("read here: connecting to business dataset");
		Connection con = manager.getConnection();
		log.info("read here: connected to business dataset");

		Statement use = con.createStatement();
		use.execute("USE mysql");
		String dbName = "mysql";
	    Statement stmt = null;
	    String query = "select * " +                  
	                   "from " +"business_licences WHERE (Latitude >= "+lowerLat+") AND (Latitude <= "+upperLat+") AND (Longitude >= "+lowerLon+") AND (Longitude <= "+upperLon+")";
	    List<Business> businesses = new ArrayList<Business>();
	    try {
			log.info("read here: getting data from dataset");

	        stmt = con.createStatement();

	        ResultSet rs = stmt.executeQuery(query);
			log.info("read here: data fetched from dataset");

	        while (rs.next()) {

	        	String type,businessName,address;
	        	Double lat,lon;
	            type = rs.getString("BusinessType");
	            businessName = rs.getString("BusinessName");
	            address = rs.getString("Unit")+" "+rs.getString("UnitType")+" "+rs.getString("House")+" "+rs.getString("Street")+" "+rs.getString("City")+" "+rs.getString("Province")+" "+rs.getString("PostalCode") ;
	            lat = rs.getDouble("Latitude");
	            lon = rs.getDouble("Longitude");
	            businesses.add(new Business(type,businessName,address,lat,lon));
	        }
	    } catch (SQLException e ) {
			log.warning("something went wrong");
			log.warning(e.getMessage());
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
		return businesses;
	}
	
	//Not for use
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		res.setContentType("text/plain");

		Map<String, String> properties = new HashMap();
		if (SystemProperty.environment.value() ==
				SystemProperty.Environment.Value.Production) {
			properties.put("javax.jdo.option.ConnectionDriverName",
					"com.mysql.jdbc.GoogleDriver");
			properties.put("javax.jdo.option.ConnectionURL",
					System.getProperty("jdbc:mysql://173.194.244.58/mysql"));
		} else {
			properties.put("javax.jdo.option.ConnectionDriverName",
					"com.mysql.jdbc.Driver");
			properties.put("javax.jdo.option.ConnectionURL",
					System.getProperty("jdbc:mysql://173.194.244.58/mysql"));
		}

		PersistenceManagerFactory pmf =
				JDOHelper.getPersistenceManagerFactory(properties, "Demo");

		// Insert a few rows.
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			//     pm.makePersistent(new Greeting("user", new Date(), "Hello!"));
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		// List all the rows.
		pm = pmf.getPersistenceManager();
		tx = pm.currentTransaction();
		try {
			tx.begin();
			Extent e = pm.getExtent(Business.class, true);
			Iterator iter = e.iterator();
			while (iter.hasNext()) {
				Business b = (Business) iter.next();
				res.getWriter().println(
						b.getBusinessName());	      }
			tx.commit();
		} catch (Exception e) {
			res.getWriter().println("Exception: " + e.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		res.getWriter().println("---");


	}
}






