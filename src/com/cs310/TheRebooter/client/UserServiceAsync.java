package com.cs310.TheRebooter.client;

import com.cs310.TheRebooter.shared.AbstractUser;
import com.cs310.TheRebooter.shared.Address;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	void isLoggedIn(String sessionID, AsyncCallback<Boolean> callback);

	void userLogout(String user, AsyncCallback<Void> callback);

	void getVerification(String token, AsyncCallback<Void> callback);

	void getLoginURL(String hostPageBaseURL, String provider,
			AsyncCallback<String> asyncCallback);

	void getLoginCredentials(String jsessionID,
			AsyncCallback<AbstractUser> callback);

	void editInfo(AbstractUser c, AsyncCallback<AbstractUser> callback);

	void addAddress(AbstractUser u, Address a,
			AsyncCallback<AbstractUser> callback);

	void removeAddress(AbstractUser u, Address a,
			AsyncCallback<AbstractUser> callback);

}
