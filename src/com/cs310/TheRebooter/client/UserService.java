package com.cs310.TheRebooter.client;

import com.cs310.TheRebooter.shared.AbstractUser;
import com.cs310.TheRebooter.shared.Address;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {

	String getLoginURL(String hostPageBaseURL, String provider);
	void getVerification(String token);
	void userLogout(String user);
	Boolean isLoggedIn(String sessionID);
	AbstractUser getLoginCredentials(String jsessionID);
	AbstractUser editInfo(AbstractUser c);
	AbstractUser addAddress(AbstractUser u, Address a);
	AbstractUser removeAddress(AbstractUser u, Address a);
}
