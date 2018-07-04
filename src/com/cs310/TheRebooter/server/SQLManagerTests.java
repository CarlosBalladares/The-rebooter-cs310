package com.cs310.TheRebooter.server;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.*;

public class SQLManagerTests {


	@Test
	public void testSQLConnection()
	{
		SQLManager manager = new SQLManager();
		try {
			manager.getConnection();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testWriteSQL()
	{
		SQLManager manager = new SQLManager();
		try {
			// this test will take a very very very long time to run.
			//manager.writeCSVFileToDataBase("business_licences.csv", "business_licences");
			throw new SQLException();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}