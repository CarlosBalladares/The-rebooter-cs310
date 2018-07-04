package com.cs310.TheRebooter.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cs310.TheRebooter.shared.AbstractUser;
import com.cs310.TheRebooter.shared.Address;
import com.cs310.TheRebooter.shared.RegularUser;

/**
 * <p>	UserManager is a class that holds all active users in the app
 * 		in a Map, with their JSESSIONID as a String and the AbstractUser 
 * 		corresponding to the JSESSIONID. Note JSESSONID is not an identifier 
 * 		for the App,its just an identifier(a cookie) for  the web container to
 * 		identify the browser. </p>
 * */
public class UserManager {
	
	/**
	 * <p>This maps online users, users that have logged in, with their browser's cookie(JSESSIONID).
	 * 		This way we know which browser is calling the App and know which user corresponds to the browser that calls the app
	 * 		unless the cookie was stolen, but i think that is outside the scope of the course(to fix that we need to buy ssl certificate).</p>
	 * */
	private Map<String, AbstractUser> onlineUserMap;
	private static UserManager instance;
	
	private UserManager(){
		onlineUserMap= new HashMap<String, AbstractUser>();
	}
	public static UserManager getInstance()
	{
		if(instance!=null)
		{
			return instance;
		}else
		{
			return new UserManager();
		}
	}
	
	/**
	 * <p> Adds a User to the online user map<p>
	 * 
	 * @param user 
	 * */
	public void addUser(AbstractUser user){
		getOnlineUserMap().put(user.getSessionID(), user);
		printUsers();
	}
	
	public void removeUser(String jSessionID){
		getOnlineUserMap().remove(jSessionID);
		printUsers();
	}
	
	public boolean hasUser(AbstractUser user){
		printUsers();
		return getOnlineUserMap().keySet().contains(user.getSessionID());
		
	}
	public boolean hasUser (String sessionID){
		printUsers();
		return getOnlineUserMap().keySet().contains(sessionID);
	}
	
	public void printUsers(){
		System.out.println("\n\n========================\n\n");
		System.out.println("User list at"+ new Date());
		System.out.println("\n---------------------\n");
		int count=onlineUserMap.size();
		for(String s: onlineUserMap.keySet()){
			System.out.println("#"+count+" userName: "+onlineUserMap.get(s).getName()+",  user session id"+onlineUserMap.get(s).getSessionID()+" user as object"+onlineUserMap.get(s));
			for(Address anAddress: onlineUserMap.get(s).getAddresses()){
				System.out.println("user's addressses: "+anAddress.getFullAddress());
			}
			System.out.println("");
			count++;
		}
		System.out.println("======END=====");
		
		
		
	}

	public Map<String, AbstractUser> getOnlineUserMap() {
		return onlineUserMap;
	}

	public void setOnlineUserMap(Map<String, AbstractUser> onlineUserMap) {
		this.onlineUserMap = onlineUserMap;
	}
	
	public AbstractUser getUser(String jSessionID){
		if(jSessionID!=null&&hasUser(jSessionID)){
			AbstractUser u = onlineUserMap.get(jSessionID);
			u.setSessionID(jSessionID);
			return u;
		}else{
		return null;
		}
	}
	
	public AbstractUser getRPCUser(String jSessionID){
		AbstractUser u =getUser(jSessionID);
		AbstractUser resp= new RegularUser();
		if(u!=null){
			
			
			resp.setAddresses(new ArrayList<Address>(u.getAddresses()));
			for(Address a:resp.getAddresses()){
				a.setUser(resp);
			}
			resp.setEmail(u.getEmail());
			resp.setName(u.getName());
			resp.setSessionID(u.getSessionID());
			resp.setSessionProvider(u.getSessionProvider());
			return resp;
		}else
			return null;
	}
	
	public AbstractUser editUser(AbstractUser u){
		if(u==null || !onlineUserMap.containsKey(u.getSessionID()))
			return null;
		AbstractUser fromMap = onlineUserMap.get(u.getSessionID());
		
		fromMap.setName(u.getName());
		//fromMap.setAddresses(u.getAddresses());
		
	
		return fromMap;
		
	}
	
	public void addAddress(AbstractUser u , Address a){
		if(u!=null)
			addAddress(u.getSessionID(), a);
	}
	
	public void addAddress(String JsessionID, Address a){
		if(JsessionID!=null && a!=null&&hasUser(JsessionID)){
			a.setUser(getUser(JsessionID));
			getUser(JsessionID).getAddresses().add(a);
		}
	}
	
	public boolean removeAddress(AbstractUser u , Address a){
		if(u!=null)
			return removeAddress(u.getSessionID(), a);
		return false;
	}
	
	public boolean removeAddress(String JsessionID, Address a){
		if(JsessionID!=null && a!=null&&hasUser(JsessionID)){
			return getUser(JsessionID).getAddresses().remove(a);
		}
		return false;
	}
	public Address getAddress(AbstractUser u , Address a){
		if(u!=null)
			return getAddress(u.getSessionID(), a);
		else return null;
	}
	
	public Address getAddress(String JsessionID, Address a){
		if(JsessionID!=null && a!=null&&hasUser(JsessionID)){
			int i =getUser(JsessionID).getAddresses().indexOf(a);
			if(i>=0)return getUser(JsessionID).getAddresses().get(i);
		}
		return null;

	}
	
	public void setCopy(AbstractUser u){
		if(u==null||!hasUser(u))
			return;
		addUser(u);
	}
	
//	public AbstractUser getabsUser(String s){
//		return getOnlineUserMap().get(s);
//	}
	
}
