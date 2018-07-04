package com.cs310.TheRebooter.server;
import java.io.IOException;
import java.util.ArrayList;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.builder.api.GoogleApi;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.cs310.TheRebooter.client.UserService;
import com.cs310.TheRebooter.server.utils.Google2Api;
import com.cs310.TheRebooter.shared.AbstractUser;
import com.cs310.TheRebooter.shared.Address;
import com.cs310.TheRebooter.shared.RegularUser;
import com.google.gwt.rpc.server.RPC;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {
	private UserManager userManager = UserManager.getInstance();
	private final OAuthService googleService= new ServiceBuilder()
													.provider(Google2Api.class)
													.apiKey(OAuthServiceParams.GOOGLE_CLIENT_ID)
													.apiSecret(OAuthServiceParams.GOOGLE_API_SECRET)
													.callback(OAuthServiceParams.OAUTH_RESPONSE_HANDLER+"google")
													.scope(OAuthServiceParams.GOOGLE_API_SCOPES)
													.debug()
													.build();
	public final OAuthService facebookService= new ServiceBuilder().provider(FacebookApi.class)
													.apiKey(OAuthServiceParams.FACEBOOK_API_KEY)
													.apiSecret(OAuthServiceParams.FACEBOOK_API_SECRET)
													.scope(OAuthServiceParams.FACEBOOK_API_SCOPES)
													.callback(OAuthServiceParams.OAUTH_RESPONSE_HANDLER+"facebook")
													.debug()
													.build();
	public final OAuthService githubService= null;
	
	private static final PersistenceManagerFactory pmf= JDOHelper.getPersistenceManagerFactory("transactions-optional");

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)//smells bad but works re-factor if needed
			throws ServletException, IOException {
		String code = (String)req.getAttribute("code");
		String serviceName=(String)req.getAttribute("service");
		if(code!=null||serviceName!=null){
			Verifier v = new Verifier(code);
			AbstractUser u =null;
			try{
				if (serviceName.equals("google"))
					u=getGoogleUser(v);
				else if(serviceName.equals("facebook"))
					u=getFacebookUser(v);
				if(u!=null){
					u.setSessionID(req.getSession().getId());
					loginUser(u);
				}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(e);
				System.out.println(e.getMessage());
				System.out.println("code redeemed or user didnt give permision to some of the required params");
			}
		}
		resp.sendRedirect("TheRebooter.html");
		
	}
	@Override
	public String getLoginURL(String url, String provider) {
		String authURL=url;
		if(provider.equals("google"))
			authURL = googleService.getAuthorizationUrl(null);
		else if(provider.equals("facebook"))
			authURL = facebookService.getAuthorizationUrl(null);
		return authURL;
	}
	@Override
	public void userLogout(String JSessionId) {
		userManager.removeUser(JSessionId);
	}
	@Override
	public Boolean isLoggedIn(String sessionID) {
		
		return userManager.hasUser(sessionID);
	}
	@Override
	public void getVerification(String URI) {
		// TODO Auto-generated method stub
	}
	
	// helpers
	private void loginUser(AbstractUser u){
		if(u==null)
			return;
		AbstractUser userToAdd = getUserFromDataStore(u.getEmail());
		if(userToAdd!=null){
			for(Address a:userToAdd.getAddresses()){
				System.out.println("noise Address:"+a);
			}
			userToAdd.setSessionID(u.getSessionID());
			for(Address a:userToAdd.getAddresses()){
				a.setUser(userToAdd);
			}
			//userToAdd.setAddresses(u.getAddresses());
			//userToAdd.getAddresses().add(new Address(userToAdd,"some address in vancouver"));
			userManager.addUser(userToAdd);
		}
		else{
			userManager.addUser(persistUserToDataStoreAndGetDetachedCopy(u));
		}
			
		
	}
	private AbstractUser getGoogleUser(Verifier v) throws ParseException{
		Token accessToken = googleService.getAccessToken(null, v);
		OAuthRequest request = new OAuthRequest(Verb.GET, OAuthServiceParams.GOOGLE_API_NEEDED_INFO);
		googleService.signRequest(accessToken, request);
		Response response = request.send();
		AbstractUser u = parseGoogleUser(response.getBody());
		
		return u;
	}
	private AbstractUser parseGoogleUser(String JSON) throws ParseException{
		JSONObject jsonObject ;
		JSONParser jsonParser = new JSONParser();
		jsonObject = (JSONObject)jsonParser.parse(JSON);
		String name = (String) jsonObject.get("name");
		String email = (String) jsonObject.get("email");
		if(name!=null&email!=null){
			RegularUser u = new RegularUser();
			u.setName(name);
			u.setEmail(email);
			u.setSessionProvider("google");
			return u;
		}else{
			return null;
		}
	}
	private AbstractUser getFacebookUser(Verifier v) throws ParseException {
		Token accessToken = facebookService.getAccessToken(null, v);
		OAuthRequest request =new OAuthRequest(Verb.GET, OAuthServiceParams.FACEBOOK_API_NEEDED_INFO);
		facebookService.signRequest(accessToken, request);
		Response response = request.send();
		AbstractUser u = parseFacebookUser(response.getBody());
		return u;
	}
	private AbstractUser parseFacebookUser(String JSON) throws ParseException{
		JSONObject jsonObject ;
		JSONParser jsonParser = new JSONParser();
		jsonObject = (JSONObject)jsonParser.parse(JSON);
		String name = (String) jsonObject.get("name");
		String email = (String) jsonObject.get("email");
		if(name!=null&email!=null){
			RegularUser u = new RegularUser();
			u.setName(name);
			u.setEmail(email);
			u.setSessionProvider("facebook");
			return u;
		}else{
			return null;
		}
	}
	private AbstractUser getUserFromDataStore(String email){
		PersistenceManager pm=null;
		AbstractUser u;
		try{
			pm=pmf.getPersistenceManager();
			RegularUser r = pm.getObjectById(RegularUser.class, email);
			u=r;
			for(Address a:u.getAddresses())
				while(a.getFullAddress().equals(""))
					System.out.println("waiting for datastor in getuser");
			
//			System.out.println(r);
//			u = (AbstractUser) pm.getObjectById(RegularUser.class, email);
////			AbstractUser u4 = pm.detachCopy(u);
////			u=u4;
//			System.out.println(u4);
		}catch(JDOObjectNotFoundException e){
			System.out.println("user not found");
			u=null;
		}finally{
			if(pm!=null)
				pm.close();
		}
		return u;
	}
	private AbstractUser persistUserToDataStoreAndGetDetachedCopy(AbstractUser user){
		PersistenceManager pm= null;
		AbstractUser resp = null;
		try{
			//pm=JDOHelper.getPersistenceManager(user);
			pm=JDOHelper.getPersistenceManager(user);
			if(pm==null)
			pm=pmf.getPersistenceManager();
			
			AbstractUser u =pm.makePersistent(user);
			resp= pm.detachCopy(u);
			//pm.currentTransaction().commit();

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("DataStore NOT FOUND\nDataStore NOT FOUND\nDataStore NOT FOUND\n");
			// TODO handle exception service unavailable for some reason
		}finally{
			if(pm!=null)
				pm.close();
		}
		return resp;
	}
	private AbstractUser changeNameInDatastore(AbstractUser user){
		PersistenceManager pm= null;
		AbstractUser resp = null;
		try{
			pm=pmf.getPersistenceManager();
			resp= pm.getObjectById(RegularUser.class, user.getEmail());
			resp.setName(user.getName());
			resp.setSessionID(user.getSessionID());
			for(Address a:resp.getAddresses())
				while(a.getFullAddress().equals(""))
					System.out.println("waiting for datastor in getuser");
			pm.makePersistent(resp);

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("DataStore NOT FOUND\nDataStore NOT FOUND\nDataStore NOT FOUND\n");
			// TODO handle exception service unavailable for some reason
		}finally{
			if(pm!=null)
				pm.close();
		}
		return resp;
	}
	
	private void removeFromDataStore(Object o){
		PersistenceManager  pm = null;
		try{
			pm=pmf.getPersistenceManager();
			pm.deletePersistent(o);
		}catch(JDOObjectNotFoundException e){

		}finally{
			if(pm!=null)
				pm.close();
		}
	}
	
	
	@Override
	public AbstractUser getLoginCredentials(String jSessionID) {
		System.out.println("printing RPC USER addresses");
		AbstractUser u = userManager.getRPCUser(jSessionID);
		if (u==null)
			return u;
		
		for(Address a: userManager.getRPCUser(jSessionID).getAddresses()){
			if(a!=null)
				System.out.println(a.getFullAddress());
		}
		return userManager.getRPCUser(jSessionID);
	}
	@Override
	public AbstractUser editInfo(AbstractUser user) {
//		userManager.editUser(user);		
//		persistUserToDataStoreAndGetDetachedCopy(userManager.getUser(user.getSessionID()));
//		userManager.setCopy(persistUserToDataStoreAndGetDetachedCopy(userManager.getUser(user.getSessionID())));		
//		return userManager.getRPCUser(user.getSessionID()); 
		
		AbstractUser newuser = changeNameInDatastore(user);
		if(newuser!=null)
			userManager.setCopy(newuser);
		return userManager.getRPCUser(newuser.getSessionID());
	}
	@Override
	public AbstractUser addAddress(AbstractUser user, Address a) {
		/**if(userManager.hasUser(user)){
			userManager.addAddress(user, a);
//			AbstractUser u = userManager.getUser(user.getSessionID());
//			System.out.println("Add email"+u.getEmail());
//			persistUserToDataStoreAndGetDetachedCopy(u);
			userManager.setCopy(persistUserToDataStoreAndGetDetachedCopy(userManager.getUser(user.getSessionID())));
			
		}
		return userManager.getRPCUser(user.getSessionID());**/
		AbstractUser newuser = addAdress2(user, a);
		if(newuser!=null)
			userManager.setCopy(newuser);
		return userManager.getRPCUser(newuser.getSessionID());
		
	}
	@Override
	public AbstractUser removeAddress(AbstractUser user, Address a) {
/**		if(userManager.hasUser(user)){
			Address toDelete =userManager.getAddress(user, a);
			System.out.println(toDelete);
//			boolean delete = userManager.removeAddress(user,a);
			AbstractUser u = userManager.getUser(user.getSessionID());
			u.getAddresses().remove(a);
			
			
			
			userManager.setCopy(persistUserToDataStoreAndGetDetachedCopy(userManager.getUser(user.getSessionID())));

//			AbstractUser u = userManager.getUser(user.getSessionID());
//			System.out.println("Remove email"+u.getEmail());
//
//			persistUserToDataStoreAndGetDetachedCopy(u);
//			loginUser(getUserFromDataStore(user.getEmail()));
		}
			
		return userManager.getRPCUser(user.getSessionID());
		
		**/
		
		AbstractUser newuser = removeAddress2(user, a);
		
		if(newuser!=null)
			userManager.setCopy(newuser);
		return userManager.getRPCUser(newuser.getSessionID());
	}
	
	public AbstractUser removeAddress2(AbstractUser user, Address a) {
		if(!userManager.hasUser(user))
			return null;
		
		PersistenceManager  pm = null;
		RegularUser u =null;
		try{
			pm=pmf.getPersistenceManager();
			u = pm.getObjectById(RegularUser.class, user.getEmail());
			for(Address anAddress:u.getAddresses())
				anAddress.setUser(u);
			for(Address anAddress:u.getAddresses())
				if(anAddress.equals(a))
					u.getAddresses().remove(anAddress);
			
			u.setSessionID(user.getSessionID());
			pm.makePersistent(u);
		}catch(JDOObjectNotFoundException e){

		}catch(Exception e){
			
		}finally{
			if(pm!=null)
				pm.close();
		}
		return u;
	}
	
	public AbstractUser addAdress2(AbstractUser user, Address a){
		PersistenceManager pm = null;
		RegularUser u = null;
		try{
			pm=pmf.getPersistenceManager();
			u=pm.getObjectById(RegularUser.class, user.getEmail());
			for(Address anAddress:u.getAddresses()){
					anAddress.setUser(u);
					while(anAddress.getFullAddress().equals("")){
						System.out.println("waiting for datastore");
					}
			}
			u.getAddresses().add(new Address(u,a.getFullAddress()));
			u.setSessionID(user.getSessionID());
			pm.makePersistent(u);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pm!=null)
				pm.close();
		}
		
		return u;
	}
}
