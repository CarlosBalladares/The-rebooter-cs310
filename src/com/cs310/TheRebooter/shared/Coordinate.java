package com.cs310.TheRebooter.shared;

import java.io.Serializable;

public class Coordinate implements Serializable{
	private double latitude;
	private double longitude;
	public Coordinate()
	{
		latitude = 0;
		longitude = 0;
	}
	public Coordinate(double lat, double lon)
	{
		latitude = lat;
		longitude = lon;
	}
	public double getLatitude()
	{
		return latitude;
	}
	public double getLongitude()
	{
		return longitude;
	}
	public double findRectilinearDistanceTo(Coordinate coordinate)
	{
		return Math.abs( (coordinate.latitude - latitude)) + Math.abs(coordinate.longitude-longitude);
	}
	public double findDirectDistanceTo(Coordinate coordinate)
	{
		return Math.sqrt(Math.pow(Math.abs( (coordinate.latitude - latitude)),2) + Math.pow(Math.abs(coordinate.longitude-longitude),2));
	}

}
