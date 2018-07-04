package com.cs310.TheRebooter.client;

import com.cs310.TheRebooter.shared.Coordinate;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AddressConverterServiceAsync {
	void getLatLon(String address,AsyncCallback<Coordinate> callback);
}
