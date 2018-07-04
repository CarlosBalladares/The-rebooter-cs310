package com.cs310.TheRebooter.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cs310.TheRebooter.client.AddressService;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.scribe.builder.api.FacebookApi;

import com.cs310.TheRebooter.client.NotLoggedInException;
import com.cs310.TheRebooter.client.UserService;
import com.cs310.TheRebooter.server.UserManager;
import com.cs310.TheRebooter.server.utils.Google2Api;
import com.cs310.TheRebooter.shared.AbstractUser;
import com.cs310.TheRebooter.shared.Address;
import com.cs310.TheRebooter.shared.RegularUser;

public class AddressServiceImpl extends RemoteServiceServlet implements AddressService
{
	private UserManager userManager = UserManager.getInstance();
	private static final Logger LOG = Logger.getLogger(AddressServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF =
	      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	      
	List<Address> dummy1 = new ArrayList<Address>();
	
	public void addAddress( String address, String JSessionID ) throws NotLoggedInException
	{
		//checkLoggedIn(JSessionID);
		//PersistenceManager pm = getPersistenceManager();
		
		try
		{
//			// pm.makePersistent(new Address(user, address));
//			AbstractUser u = pm.getObjectById(AbstractUser.class, getUser(JSessionID).getEmail());
//			//AbstractUser u = getUser(JSessionID);
////			AbstractUser u = new RegularUser();
////			
////			Address dummy2 = new Address(u, "2118 W48th Avenue");
////			dummy1.add(dummy2);
////			u.setAddresses(dummy1);
//			
//			List<Address> a = new ArrayList<Address>();
//			
//			// copy over the elements in the old Array List
//			a.addAll(u.getAddresses());
//			
//			Address newAddress = new Address(getUser(JSessionID), address);
//			a.add(newAddress);
//			u.setAddresses((ArrayList<Address>)a);
			
			userManager.addAddress(JSessionID, new Address(null, address));
			persistUserToDataStore(userManager.getUser(JSessionID));
			
			
			
		}
		finally
		{
			//pm.close();
		}
	}
	
	public void removeAddress( String address, String JSessionID ) throws NotLoggedInException
	{
		checkLoggedIn(JSessionID);
	    PersistenceManager pm = getPersistenceManager();
	    try 
	    {
	    	AbstractUser u = pm.getObjectById(AbstractUser.class, getUser(JSessionID).getEmail());
	    	List<Address> a = new ArrayList<Address>();
			
			// copy over the elements in the old Array List
			a.addAll(u.getAddresses());
			
			a.remove(u.getAddresses().size()-1);
			u.setAddresses((ArrayList<Address>)a);
	    } 
	    finally 
	    {
	      pm.close();
	    }

	}
	
	public List<Address> getAddresses(String JSessionID) throws NotLoggedInException
	{
		PersistenceManager pm = getPersistenceManager();
	    List<Address> addresses = new ArrayList<Address>();
	    try 
	    {
//	      Query q = pm.newQuery(AbstractUser.class, "email");
//	      q.declareParameters("com.cs310.TheRebooter.shared.AbstractUser");
//	      List<AbstractUser> users = (List<AbstractUser>) q.execute(getUser(email));
//	      for (AbstractUser u : users) 
//	        addresses.addAll(u.getAddresses());
	    	AbstractUser u = pm.getObjectById(AbstractUser.class, getUser(JSessionID).getEmail());
	    	
	    	addresses.addAll(u.getAddresses());
	    	
	    } 
	    finally 
	    {
	      pm.close();
	    }
	    return addresses;
	}
	

	private AbstractUser getUser(String JSessionID)
	{
		return userManager.getUser(JSessionID);
	}

//	private ClientCredentials getUser(String JSessionID)
//	{
//		//return userManager.getUser(JSessionID);
//	}

	
	private void persistUserToDataStore(Object user){
		PersistenceManager pm= null;
		try{
			pm=PMF.getPersistenceManager();
			pm.makePersistent(user);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("DataStore NOT FOUND\nDataStore NOT FOUND\nDataStore NOT FOUND\n");
			// TODO handle exception service unavailable for some reason
		}finally{
			if(pm!=null)
				pm.close();
		}
	}
	
	private void checkLoggedIn(String JSessionID) throws NotLoggedInException
	{
		if(getUser(JSessionID) == null)
			throw new NotLoggedInException("Not logged in.");
	}
	
	private PersistenceManager getPersistenceManager()
	{
		return PMF.getPersistenceManager();
	}
	
}