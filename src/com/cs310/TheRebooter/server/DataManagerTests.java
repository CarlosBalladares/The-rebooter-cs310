package com.cs310.TheRebooter.server;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;
import java.io.IOException;

import org.junit.Test;

import com.cs310.TheRebooter.shared.Business;
import com.cs310.TheRebooter.shared.Coordinate;

public class DataManagerTests {
	
	@Test
	public void testGetInVicinity()
	{
		DataManager d = new DataManager();
		Coordinate base = new Coordinate(49.21569151659,	-123.1182884132);
		List<Business> businesses = null;
		try {
			businesses = d.getBusinessesInVicinity(base, .05);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		assertTrue(businesses.size()>0);
	}

}
