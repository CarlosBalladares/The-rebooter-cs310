package com.cs310.TheRebooter.server;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.cs310.TheRebooter.client.AddressConverterService;
import com.cs310.TheRebooter.client.UserService;
import com.cs310.TheRebooter.shared.Coordinate;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class AddressConverterImpl extends RemoteServiceServlet implements AddressConverterService{
	final static String apiKey = "AIzaSyB9TrS_s826OwbQvJytvTPxaWr7JDAwAz4";
	final static String url = "https://maps.googleapis.com/maps/api/geocode/json?address=";
	//https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=API_KEY

	public Coordinate getLatLon(String address)
	{
		ArrayList<Double> coord = getLatLonFromString(address);
		return new Coordinate(coord.get(0),coord.get(1));
	}
	
	private static String getLatLonString(String address)
	{
		ArrayList<Double> latLon = new ArrayList<Double>();
		String gUrl = url +address+"&key="+apiKey;
		String json;
		URL geocode;
		try {
			geocode = new URL(gUrl);
			InputStream read = geocode.openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(read, Charset.forName("UTF-8")));
			json = readAll(rd);
		}catch (IOException e)
		{
			json = e.getMessage();
			e.printStackTrace();
		}
		String subLatLon = json.substring(json.indexOf("\"location\" : {"),json.indexOf("\"location_type\"")-3);

		return subLatLon;
	}
	private static ArrayList<Double> getLatLonFromString(String address)
	{
		ArrayList<Double> latlon = new ArrayList<Double>();
		String latlonString = "";
		latlonString = getLatLonString(address);
		String latString = latlonString.substring(latlonString.indexOf("\"lat\" : ")+7, latlonString.indexOf(","));
		String lonString = latlonString.substring(latlonString.indexOf("\"lng\" : ")+7, latlonString.indexOf("},"));
		latlon.add(Double.parseDouble(latString));
		latlon.add(Double.parseDouble(lonString));
		return latlon;
	}

	//from http://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}


}
