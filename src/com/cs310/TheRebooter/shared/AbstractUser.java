package com.cs310.TheRebooter.shared;

import java.io.Serializable;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cs310.TheRebooter.shared.Address;


@PersistenceCapable(detachable="true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)

public abstract class AbstractUser implements Serializable {
	
	@PrimaryKey
	private String email;
	@Persistent
	private String name; 
	@Persistent
	private String sessionID;
	@Persistent
	private String sessionProvider;

	@Persistent(mappedBy="user", defaultFetchGroup="true")
	@Element(dependent = "true")
	private List<Address> addresses;

	

	public AbstractUser()
	{
		sessionID = "";
		sessionProvider = "";
		name = "";
		email = "";
		addresses = new java.util.ArrayList<Address>();
		//addresses.add(new Address(this, "first address"));
	}
	
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getSessionProvider() {
		return sessionProvider;
	}

	public void setSessionProvider(String sessionProvider) {
		this.sessionProvider = sessionProvider;
	}
	
	public List<Address> getAddresses()
	{
		return this.addresses;
	}
	
	public void setAddresses(java.util.ArrayList<Address> addresses)
	{
		this.addresses = addresses;
	}
}
