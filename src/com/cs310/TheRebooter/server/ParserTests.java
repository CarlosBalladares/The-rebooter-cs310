package com.cs310.TheRebooter.server;

import java.util.ArrayList;

import org.junit.Test;

public class ParserTests {
	
	@Test
	public void testParse()
	{
		Parser parser = new Parser("business_licences.csv","business_licences");
		ArrayList<String> statements = (ArrayList<String>) parser.ReadCSV();
		System.out.print(statements.get(0));
	}
	@Test
	public void testMethod()
	{
		String s = "Calhoun's Bakery Cafe & Catering";
		System.out.println(s.replaceAll("'", ""));
		System.out.println(s);
	}

}
