package com.cs310.TheRebooter.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.cs310.TheRebooter.shared.*;

@RemoteServiceRelativePath("addresses") // associates the service with a default path relative to the module base URL
public interface AddressService extends RemoteService
{
	public void addAddress( String address, String JSessionID ) throws NotLoggedInException;
	public void removeAddress( String address, String JSessionID ) throws NotLoggedInException;
	
	public List<Address> getAddresses(String JSessionID) throws NotLoggedInException;
}