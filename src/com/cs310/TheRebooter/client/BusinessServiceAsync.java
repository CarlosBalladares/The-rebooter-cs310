package com.cs310.TheRebooter.client;

import java.util.List;

import com.cs310.TheRebooter.shared.Business;
import com.cs310.TheRebooter.shared.BusinessServiceResponse;
import com.cs310.TheRebooter.shared.Coordinate;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BusinessServiceAsync {
	void getBusinessesInVicinity(BusinessServiceResponse resp,
			AsyncCallback<BusinessServiceResponse> result);
}
