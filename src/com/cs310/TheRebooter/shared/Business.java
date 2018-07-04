package com.cs310.TheRebooter.shared;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Business implements Serializable{

	private String type;
	@Id private String businessName;
	private String address;
	private Coordinate location;
	private double latitude;
	private double longitude;

	public Business(){

	}
	public Business(String type, String businessName, String address, double latitude, double longitude)
	{
		this.type = type;
		this.businessName = businessName;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.location = new Coordinate(latitude, longitude);
	}
	public Business(String LicenceRSN,	String LicenceNumber,	String LicenceRevisionNumber,	String BusinessName,	String BusinessTradeName,	String Status,	String IssuedDate,	String ExpiredDate,String 	BusinessType,	String BusinessSubType,	String Unit,	String UnitType,	String House,	String Street,	String City,	String Province,	String Country, String	PostalCode,	String LocalArea,	String NumberOfEmployees,	Double Latitude, Double	Longitude,	String FeePaid,	String ExtractDate)
	{ 
		this.type = BusinessType;
		this.businessName = BusinessName;
		this.address = Unit+" "+UnitType+" "+House+" "+Street+" "+City+" "+Province+" "+PostalCode ;
		this.latitude = Latitude;
		this.longitude = Longitude;
		this.location = new Coordinate(latitude, longitude);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getAddress()
	{
		return address;
	}
	public Coordinate getLocation()
	{
		return location;
	}
	public double getLatitude()
	{
		return latitude;
	}
	public double getLongitude()
	{
		return longitude;
	}
	public String toString()
	{
		return type+" "+businessName+" "+address+" "+location.toString();
	}

}
