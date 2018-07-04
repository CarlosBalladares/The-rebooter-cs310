package com.cs310.TheRebooter.client;

import com.cs310.TheRebooter.shared.Coordinate;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("addressconverter")
public interface AddressConverterService extends RemoteService{
	public Coordinate getLatLon(String address);

}
