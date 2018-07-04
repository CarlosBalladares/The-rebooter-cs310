package com.cs310.TheRebooter.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.cs310.TheRebooter.shared.AbstractUser;
import com.google.appengine.api.datastore.Key;



@PersistenceCapable(detachable="true")
public class Address implements Serializable

{
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    public Key key;
	@Persistent
	private AbstractUser user;
	
	@Persistent
	private String fullAddress;
	
	@Persistent
	private int unit;
	
	@Persistent
	private String street;
	
	@Persistent
	private String city;
	
	@Persistent
	private String province;
	
	@Persistent
	private String postalCode;
	
	public Address(AbstractUser c, String s){
		fullAddress = s;
		unit = 0;
		street = null;
		city = null;
		province = null;
		postalCode = null;
		user = c;
	}
	
	public Address(){
		fullAddress = "";
		unit = 0;
		street = null;
		city = null;
		province = null;
		postalCode = null;
		user = new RegularUser();
	}
	
	public void setUser(AbstractUser u){
		if(u!=null)
		this.user=u;
	}
	
	public int getUnit()
	{
		return this.unit;
	}
	
	public String getStreet()
	{
		return this.street;
	}
	
	public String getCity()
	{
		return this.city;
	}
	
	public String getProvince()
	{
		return this.province;
	}
	
	public String getPostalCode()
	{
		return this.postalCode;
	}
	
	public String getFullAddress()
	{
		return this.fullAddress;
	}
	
	public void setUnit( int unit )
	{
		this.unit = unit;
	}
	
	public void setStreet( String street )
	{
		this.street = street;
	}
	
	public void setCity( String city )
	{
		this.city = city;
	}
	
	public void setProvince( String province )
	{
		this.province = province;
	}
	
	public void setPostalCode( String postalCode )
	{
		this.postalCode = postalCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fullAddress == null) ? 0 : fullAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (fullAddress == null) {
			if (other.fullAddress != null)
				return false;
		} else if (!fullAddress.equals(other.fullAddress))
			return false;
		return true;
	}
	
	public AbstractUser getUser(){
		return this.user;
	}
	
	//override equals
	
	
}

