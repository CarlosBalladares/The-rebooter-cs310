package com.cs310.TheRebooter.shared;

import java.io.Serializable;
import java.util.List;

public class BusinessServiceResponse implements Serializable {

	private List<Business> 	businesses;
	private Address			requestAddress;
	private Coordinate		coord;
	private double distance;
	
	
	public BusinessServiceResponse(){
		setDistance(0.7);
	}
	
	public BusinessServiceResponse(Address a, Coordinate c, double d){
		setRequestAddress(a);
		setDistance(d);
		setCoord(c);
		setBusinesses(null);
	}
	
	public List<Business> getBusinesses() {
		return businesses;
	}
	public void setBusinesses(List<Business> businesses) {
		this.businesses = businesses;
	}
	public Address getRequestAddress() {
		return requestAddress;
	}
	public void setRequestAddress(Address requestAddress) {
		this.requestAddress = requestAddress;
	}
	public Coordinate getCoord() {
		return coord;
	}
	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	
	
}
