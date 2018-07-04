package com.cs310.TheRebooter.shared;
import static org.junit.Assert.*;

import org.junit.*;

import com.cs310.TheRebooter.server.AddressConverterImpl;



public class AddressTests {

	@SuppressWarnings("deprecation")
	@Test
	public void testAddresses()
	{
		Coordinate A = (new AddressConverterImpl()).getLatLon("2314+W+15th+Ave,+Vancouver,+BC+V6K+2Y8");
		Coordinate B = new Coordinate(49.257948,-123.158501);
		assertEquals(A.getLatitude(),B.getLatitude(),0.005);
		assertEquals(A.getLongitude(),B.getLongitude(),0.005);
	}
}
