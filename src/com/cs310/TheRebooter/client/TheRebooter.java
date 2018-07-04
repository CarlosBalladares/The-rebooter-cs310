package com.cs310.TheRebooter.client;

import java.util.List;

import com.cs310.TheRebooter.shared.AbstractUser;
import com.cs310.TheRebooter.shared.Address;
import com.cs310.TheRebooter.shared.Business;
import com.cs310.TheRebooter.shared.BusinessServiceResponse;
import com.cs310.TheRebooter.shared.Coordinate;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;



/**
 * Entry point class for Home Finder, a CS310 project
 */
public class TheRebooter implements EntryPoint {
	
	private UserServiceAsync 		userService;
	private BusinessServiceAsync	businessService;
	
	private AbstractUser 	user;
	
	public UserPanel	 	userPanel;
	public AddressPanel 	addressPanel;
	public MapPanel			mapPanel;
	public VerticalPanel	interactionPanel;
		
	public void onModuleLoad(){
		initServices();
		initPanels();
		fetchUser();
	}
	

	public void login(String sessionProvider){
		userService.getLoginURL(ClientUtils.getBaseURL(), sessionProvider, new LoginAsync());
	}
	public void logout(){
		userService.userLogout(ClientUtils.getJsessionID(), new LogoutAsync(this));
	}
	public void fetchUser(){
		userService.getLoginCredentials(ClientUtils.getJsessionID(), new FetchUserAsync(this));
	}
	
	public void editUser(){
		userService.editInfo(user, new EditAsync(this));
	}
	
	public void addAddress(Address address){
		userService.addAddress(user, address, new AddAddressAsync(this));
	}
	
	public void removeAddress(Address address){
		userService.removeAddress(user, address, new RemoveAdressAsync(this));
	}
	
	public void getBusineeness(BusinessServiceResponse request){
		businessService.getBusinessesInVicinity(request, new GetBusinessesAsync(this));
	}
	
	private void initPanels(){
		//initUserPanel();
		//initAddressPanel();
		initMapPanel();
		initInteractionPanel();
	}
	
	private void initServices() {
		userService=GWT.create(UserService.class);
		businessService=GWT.create(BusinessService.class);
	}
	
	private void initUserPanel(){
		//TODO initialize user panel
		userPanel= new UserPanel(this);
		RootPanel.get(ClientConstants.USER_PANEL_CONTAINER).add(userPanel);
	}
	private void initAddressPanel() {
		// TODO initialize address panel
		addressPanel= new AddressPanel(this);
		RootPanel.get(ClientConstants.ADDRESS_PANEL_CONTAINER).add(addressPanel);
	}
	private void initMapPanel() {
		// TODO initialize map panel
		mapPanel= new MapPanel(this);
	}
	private void initInteractionPanel(){
		interactionPanel=new VerticalPanel();
		userPanel= new UserPanel(this);
		addressPanel= new AddressPanel(this);
		interactionPanel.add(userPanel);
		interactionPanel.add(addressPanel);
		interactionPanel.setStyleName(ClientConstants.INTERACTION_PANEL_ID);
		RootPanel.get(ClientConstants.INTERACTION_PANEL_CONTAINER).add(interactionPanel);
		
	}
	protected void refreshPanels() {
		// TODO Auto-generated method stub
		userPanel.refresh();
		addressPanel.refresh();
		mapPanel.refresh();
		
	}
	public AbstractUser getUser(){
		return user;
	}
	public boolean loggedIn(){
		return user!=null;
	}
	protected void setUser(AbstractUser user){
		this.user=user;
		refreshPanels();
	}
	protected void removeUser(){
		this.user=null;
		refreshPanels();
	}
	
	



	
	
	
//
//
//	private List<String> addresses = new ArrayList<String>();
//
//	
//	private AddressesWidget addressesOfficialTable= new AddressesWidget(this);
//	
//	// turns out cannot put final here
//	private final AddressServiceAsync addressService = GWT.create(AddressService.class);
//	
//	
//	public Map map;
//	
//	// LoginPanel, Buttons and userService
//	private final UserServiceAsync userService = GWT.create(UserService.class);
//	private AbstractUser user = null;
//	private VerticalPanel loginPanel = new VerticalPanel();
//	private FlexTable rankPanel = new FlexTable();
//	private Button facebookLoginButton = new Button("Facebook Login");
//	private Button googleLogin = new Button("Google Login");
//	private Button editButton = new Button("edit");
//	private Button logoutButton= new Button("logout");
//	private Button submitScoreButton = new Button("submit");
//	private Label loginDialog= new Label();
//	//Edit Panel and edit info
//	private VerticalPanel editPanel = new VerticalPanel();
//	private Label name=new Label("name: ");
//	private TextBox editName= new TextBox();
//	private Button doneEditingButton= new Button("save changes");
//    
//	private ListBox listBox1 = new ListBox();
//	private ListBox listBox2 = new ListBox();
//	private ListBox listBox3 = new ListBox();
//	private ListBox listBox4 = new ListBox();
//	private ListBox listBox5 = new ListBox();
//    private int numberofRestaurant = 0;
//    private int numberofentertainment = 0;
//    private int numberofeducation = 0;
//    private int numberofutility = 0;
//    private int numberofshopping = 0;
//	AddressConverterServiceAsync addressSVC = GWT.create(AddressConverterService.class);
//	BusinessServiceAsync businessSVC;
//	boolean addressReturned = false;
//	Coordinate coord=null;
//	List<Business> businesslist;
////	private Icon restaurantIcon = Icon.newInstance("http://maps.google.com/mapfiles/kml/pal2/icon34.png");
////    private Icon  shoppingIcon = Icon.newInstance("http://maps.google.com/mapfiles/kml/pal3/icon18.png");
////    private Icon  entertainmentIcon = Icon.newInstance("http://maps.google.com/mapfiles/kml/pal2/icon49.png");
////    private Icon  educationIcon = Icon.newInstance("http://maps.google.com/mapfiles/kml/pal2/icon14.png");
////    private Icon  utilityIcon = Icon.newInstance("http://maps.google.com/mapfiles/kml/pal5/icon28.png");
//
//
//
//
//	/**
//	 * This is the entry point method.
//	 */
//	public void onModuleLoad() {		
//
//		init();
//		
//		//renderMap();
////		submitScoreButton.addClickHandler(new ClickHandler() {
////			public void onClick(ClickEvent event) {
////				generateScore();
////			}
////		});
//		
//		//ButtonHandler();
//		
//		
//
//
//	}
//	
//	public void init (){
//		initAddressTable();
//		initLoginPane();
//		RootPanel.get("superlogin").add(loginPanel);
//		RootPanel.get("superlogin").add(editPanel);
//		editPanel.setVisible(false);
//		rankPanel.getRowFormatter().addStyleName(0,"scoreListHeader");
//		setEditPanel();
//		makeRankBox();
//		
//		MarkerPosition.createInstance();
//		map= new Map();
//	}
//
//
//	private void initAddressTable() {
//		// TODO Auto-generated method stub
//		addressesOfficialTable.setAction(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				String s =addressesOfficialTable.getText();
//				Address a = new Address(user, s);
//				if(user==null){
//					//notloggedin
//				}
//				userService.addAddress(user, a, new AsyncCallback<AbstractUser>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//						Window.alert("something went wrong adding address");
//					}
//
//					@Override
//					public void onSuccess(AbstractUser result) {
//						if(result== null)
//							return;
//						user=result;
//						assembleLoginPanel();
//						updateOfficialTable();
//					}
//				});
//				
//				map.plot(s);
//			}
//		});
//		
//		
//		RootPanel.get("superaddresses").add(addressesOfficialTable);
//	}
//	
//	public void removeAddress(Address a){
//		userService.removeAddress(user, a, new AsyncCallback<AbstractUser>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				Window.alert("something went wrong removing address");
//
//			}
//
//			@Override
//			public void onSuccess(AbstractUser result) {
//				if(result== null)
//					return;
//				user=result;
//				assembleLoginPanel();
//				updateOfficialTable();
//				
//			}
//		});
//	}
//	
//	private void updateOfficialTable(){
//		addressesOfficialTable.addAddressList(user.getAddresses());
//		//map.plot(s);
//	}
//
//
//	
//    private void makeListBox(ListBox lb){
//		lb.addItem("1");
//		lb.addItem("2");
//		lb.addItem("3");
//		lb.addItem("4");
//		lb.addItem("5");
//		lb.setVisibleItemCount(1);
//		lb.setSelectedIndex(0);
//    	
//    }
//	private void makeRankBox() {
//		makeListBox(listBox1);
//		makeListBox(listBox2);
//		makeListBox(listBox3);
//		makeListBox(listBox4);
//		makeListBox(listBox5);
//
//		rankPanel.setText(0, 0, "Restaurant");
//		rankPanel.setText(0, 1, "Shopping");
//		rankPanel.setText(0, 2, "Entertainmnet");
//		rankPanel.setText(0, 3,"Education");
//		rankPanel.setText(0, 4,"Utility");
//		
//
//		rankPanel.setWidget(1, 0, listBox1);
//		rankPanel.setWidget(1, 1, listBox2);   
//		rankPanel.setWidget(1, 2, listBox3);
//		rankPanel.setWidget(1, 3, listBox4);
//		rankPanel.setWidget(1, 4, listBox5);
//		rankPanel.setWidget(0, 5, submitScoreButton);
//		rankPanel.setBorderWidth(2);
//		rankPanel.setCellPadding(5);
//		rankPanel.setCellSpacing(5);
//		
//		RootPanel.get("rankbox").add(rankPanel);
//
//	}
//
//	
//	private Coordinate convertAddress(String address){
//		if (addressSVC == null) {
//			addressSVC = GWT.create(AddressConverterService.class);
//		}
//
//		// Set up the callback object.
//		AsyncCallback<Coordinate> callback = new AsyncCallback<Coordinate>() {
//			public void onFailure(Throwable caught) {
//				// TODO: Do something with errors.
//			}
//
//			public void onSuccess(Coordinate result) {
//				coord = result;
//				addressReturned = true;
//			}
//		};
//
//		// Make the call to the stock price service.
//		addressReturned = false;
//		addressSVC.getLatLon(address, callback);
//		//while(!addressReturned){}
//		addressReturned = false;
//		return coord;
//	}
//	private List<Business> getBusinesses(Coordinate base, double distance){
//		if (businessSVC == null) {
//			businessSVC = GWT.create(BusinessService.class);
//		}
//
//		// Set up the callback object.
//		AsyncCallback<List<Business>> callback = new AsyncCallback<List<Business>>() {
//			public void onFailure(Throwable caught) {
//				// TODO: Do something with errors.
//			}
//			@Override
//			public void onSuccess(List<Business> result) {
//				businesslist = result;
//				
//			}
//		};
//
//		// Make the call to the address service.
//		addressReturned = false;
//		businessSVC.getBusinessesInVicinity(base, distance, callback);
//		//while(!addressReturned){}
//		addressReturned = false;
//		return businesslist;
//	}
//	
//
//	
//	
//
//	
//	
//	
//
//
//	private void handleError(Throwable error)
//	{
//		Window.alert(error.getMessage());
//	}
//	
//	
//	
//	//LOGIN-LOGOUT
//	private void initLoginPane(){
//		System.out.println("RPC for user credentials: contacting db");
//		userService.getLoginCredentials(getJSessionID(), new AsyncCallback<AbstractUser>() {
//			@Override
//			public void onFailure(Throwable caught) {
//				System.out.println("RPC for user credentials :din't find anything");
//
//				user=null;
//				assembleLoginPanel();
//			}
//			@Override
//			public void onSuccess(AbstractUser result) {
//				System.out.println("RPC for user credentials :found user");
//
//				user=result;
//				assembleLoginPanel();
//				//refreshList();
//				updateOfficialTable();
//			}
//		});
//	}
//	private void assembleLoginPanel(){
//		facebookLoginButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				login("facebook");
//			}
//		});
//		googleLogin.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				login("google");
//			}
//		});
//		logoutButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				logout();
//			}
//		});
//
//		if(user==null){
//			loginDialog.setText("\n\nWelcome to Rate My New Home, the best app!\n\n");
//
//			loginPanel.add(loginDialog);
//			loginPanel.add(facebookLoginButton);
//			loginPanel.add(googleLogin);
//			
//		}else{
//			loginDialog.setText("Welcome: "+escapeHTML(user.getName()));
//			loginPanel.add(loginDialog);
//			loginPanel.add(editButton);
//			loginPanel.add(logoutButton);
//		}
//	}
//	private void login(String provider){
//		userService.getLoginURL(GWT.getHostPageBaseURL(), provider, new AsyncCallback<String>() {
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				Window.alert("failed to connect to userService");
//			}
//			@Override
//			public void onSuccess(String result) {
//				// TODO Auto-generated method stub
//				Window.Location.assign(result);
//			}
//		});
//	}
//	private void logout(){
//		userService.userLogout(getJSessionID(), new AsyncCallback<Void>() {
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.Location.reload();
//			}
//			@Override
//			public void onSuccess(Void result) {
//				Window.Location.reload();
//			}
//		});
//	}
//	private String escapeHTML(String s){
//		return SafeHtmlUtils.htmlEscape(s);
//	}
//	private String getJSessionID(){
//		return Cookies.getCookie("JSESSIONID");
//	}
//
//	//edit methods
//
//	private void setEditPanel(){
//		editButton.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				if(user!=null)
//					editName.setText(user.getName());
////				mainPanel.remove(loginPanel);
//				loginPanel.setVisible(false);
//				//RootPanel.get("superlogin").add(editPanel);
//				editPanel.setVisible(true);
//
//			}
//		});
//		doneEditingButton.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				String name =editName.getText().trim();
//				String regx="^[A-Za-z ]+$";
//				RegExp pattern = RegExp.compile(regx);
//				MatchResult matcher =pattern.exec(name);
//				boolean matchFound=(matcher != null);
//
//				if(matchFound){//name is  valid, refactor regex into static var
//					user.setName(name);
//					sendEditNameRequest(user);
//					editPanel.setVisible(false);
//					loginPanel.setVisible(true);
//
//				}else{
//					Window.alert("invalid name");
//
//				}
//
//			}
//		});
//		editPanel.add(name);
//		editPanel.add(editName);
//		editPanel.add(doneEditingButton);
//	}
//
//	private void sendEditNameRequest(AbstractUser u){
//		userService.editInfo(u, new AsyncCallback<AbstractUser>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//
//				Window.alert("couldn't connect with user service for editing");
//				Window.Location.reload();
//			}
//
//			@Override
//			public void onSuccess(AbstractUser result) {
//				user=result;
//				//Window.Location.reload();
//				assembleLoginPanel();
//
//			}
//
//		});
//
//	}
//	public void generateScore(){
//		int a = (listBox1.getSelectedIndex()+1);
//		int b = (listBox2.getSelectedIndex()+1);
//		int c = (listBox3.getSelectedIndex()+1);
//		int d = (listBox3.getSelectedIndex()+1);
//		int e = (listBox3.getSelectedIndex()+1);
//		int sum = a + b + c + d + e;
//		int sumofs = numberofRestaurant+
//	     numberofshopping+
//		numberofentertainment+
//	    numberofeducation;
//		
//		float score = (((numberofRestaurant/sumofs) * (a/sum))+
//				     ((numberofshopping/sumofs)*(b/sum))+
//					((numberofentertainment/sumofs)* (c/sum))+
//				    ((numberofeducation/sumofs)*(d/sum))+
//				    ((numberofutility/sumofs)*(e/sum)))*1000;
//		Window.alert("Your score is" + score);
//	}
//	
//	public void refreshList(){
//		if(user!=null){
//			//this.addresses= (List<Address>) user.getAddresses();
//			for(Address a :user.getAddresses()){
//				addresses.add(a.getFullAddress());
//			}
////			for(String s :addresses){
////				displayAddress(s);
////			}
//			
//		}
//	}
//	
//	
//	public void setAwesomeTable(){
//		
//		
//		
//	}
//	
//	****/
//	
//	
//
}

class LoginAsync implements AsyncCallback<String>{

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		ClientUtils.Alert(ClientConstants.LOGINASYNC_FAIL);
	}

	@Override
	public void onSuccess(String result) {
		if(result!=null)
			ClientUtils.redirect(result);
		
	}
	
}

class LogoutAsync implements AsyncCallback<Void>{

	private TheRebooter app;
	
	public LogoutAsync(TheRebooter theRebooter){
		app=theRebooter;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		ClientUtils.Alert(ClientConstants.LOGOUTASYNC_FAIL);
	}

	@Override
	public void onSuccess(Void result) {
		app.removeUser();
	}
	
}

class FetchUserAsync implements AsyncCallback<AbstractUser>{
	
	private TheRebooter app;

	public FetchUserAsync(TheRebooter theRebooter){
		app=theRebooter;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		ClientUtils.Alert(ClientConstants.FETCHUSERASYNC_FAIL);
	}

	@Override
	public void onSuccess(AbstractUser result) {
			app.setUser(result);		
	}
	
}

class EditAsync implements AsyncCallback<AbstractUser>{
	
	private TheRebooter app;

	public EditAsync(TheRebooter theRebooter){
		app=theRebooter;
	}

	@Override
	public void onFailure(Throwable caught) {
		ClientUtils.Alert(ClientConstants.EDITASYNC_FAIL);
	}

	@Override
	public void onSuccess(AbstractUser result) {
		app.setUser(result);
		
	}
	
}

class AddAddressAsync implements AsyncCallback<AbstractUser>{
	
	private TheRebooter app;

	public AddAddressAsync(TheRebooter theRebooter){
		app=theRebooter;
	}

	@Override
	public void onFailure(Throwable caught) {
		ClientUtils.Alert(ClientConstants.ADDADDRESSASYNC_FAIL);
		app.mapPanel.removeAll();
	}

	@Override
	public void onSuccess(AbstractUser result) {
		app.setUser(result);
	}
	
}

class RemoveAdressAsync implements AsyncCallback<AbstractUser>{
	
	private TheRebooter app;

	public RemoveAdressAsync(TheRebooter theRebooter){
		app=theRebooter;
	}

	@Override
	public void onFailure(Throwable caught) {
		ClientUtils.Alert(ClientConstants.REMOVEADDRESSASYNC_FAIL);
		app.mapPanel.removeAll();

		
	}

	@Override
	public void onSuccess(AbstractUser result) {
		app.setUser(result);
		
	}
	
}

class GetBusinessesAsync implements AsyncCallback<BusinessServiceResponse>{
	
	private TheRebooter app;

	public GetBusinessesAsync(TheRebooter theRebooter){
		app=theRebooter;
	}

	@Override
	public void onFailure(Throwable caught) {
		ClientUtils.Alert(ClientConstants.GETBUSINESSESASYNCFAIL);
	}

	@Override
	public void onSuccess(BusinessServiceResponse result) {
		if(result==null||result.getBusinesses()==null)
			app.mapPanel.getAdresses().get(result.getRequestAddress()).fetchUnseccessful();
		else
			app.mapPanel.setBusinessesAndPlot(result);
	}
	
}
