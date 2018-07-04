package com.cs310.TheRebooter.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.cs310.TheRebooter.shared.*;

public interface AddressServiceAsync 
{
	// Void because we don't expect any data returned to us
	public void addAddress(String address, String JSessionID, AsyncCallback<Void> async); 
	public void removeAddress(String address, String JSessionID, AsyncCallback<Void> async);
	
	public void getAddresses(String JSessionID, AsyncCallback<List<Address>> async);
}


/*
To add an AsyncCallback parameter to all of our service methods, you must define a new 
interface as follows:

1. It must have the same name as the service interface, appended with Async (for example, 
		StockPriceServiceAsync).
2. It must be located in the same package as the service interface.
3. Each method must have the same name and signature as in the service interface with an 
important difference: the method has no return type and the last parameter is an 
		AsyncCallback object.
*/