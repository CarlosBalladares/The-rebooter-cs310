package com.cs310.TheRebooter.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cs310.TheRebooter.shared.Address;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.maps.gwt.client.Geocoder;
import com.google.maps.gwt.client.GeocoderRequest;
import com.google.maps.gwt.client.GeocoderResult;
import com.google.maps.gwt.client.GeocoderStatus;
import com.google.maps.gwt.client.LatLng;

public class AddressPanel  extends Composite{
	
	private List<Address>	addressList;
	
	private FlexTable		mainTable;
	private TextBox 		addressSearch;
	private Button			searchButton;
	private Image			icon;
	
	private TheRebooter 	app;
		
	public AddressPanel(TheRebooter theRebooter){
		
		app=theRebooter;
		
		initWidgets();
		setHandlers();
		
		initWidget(mainTable);
		setStyleName(ClientConstants.ADDRESS_PANEL_ID);		
	}
	
	private void initWidgets(){
		addressList=	new ArrayList<Address>();
		mainTable=		new FlexTable();
		addressSearch=	new TextBox();
		icon= new Image(ClientConstants.ADDRESS_MARKER_URL);
		icon.setSize("20px", "20px");
		searchButton=	new Button	(ClientConstants.ADDRESS_PANEL_SEARCHBTN);
		addressSearch.setText		(ClientConstants.ADDRESS_PANEL_SEARCHBOX);
		mainTable.setWidget(0, 0, addressSearch);
		mainTable.setWidget(0, 1, searchButton);
	}
	
	private void setHandlers(){
		addressSearch.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				emptySearchBox();
			}
		});
		
		addressSearch.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				char pressed = event.getCharCode();
				if(pressed==KeyCodes.KEY_ENTER)
					searchButton.click();
				
			}
		});
		
		searchButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String input = addressSearch.getText().trim();
				if(!ClientUtils.validAddressString(input)){
					ClientUtils.Alert(ClientConstants.ADDRESS_PANEL_INVALID_INPUT);
					return;
				}
				else{
					Address addressObject= new Address(app.getUser(), ClientUtils.escapeHTML(input));
					if(hasAddress(addressObject)){
						ClientUtils.Alert(ClientConstants.ADDRESS_PANEL_ADDRESS_EXISTS);
						emptySearchBox();
						return;
					}
					getLatLonAndPlot(addressObject);
				}
				searchButton.setEnabled(false);
				emptySearchBox();
					
			}
		});
		
		
	}
	
	public void addRow(Address address){
		if(!app.loggedIn())
			addressList.add(address);			
		
			int i= mainTable.getRowCount();
			mainTable.setText(i, 0, ClientUtils.escapeHTML(address.getFullAddress()));
			mainTable.setWidget(i, 1, generateDeleteButton(address));
			Image add=new Image(ClientConstants.ADDRESS_MARKER_FOCUSED_URL);
			add.setSize("20px", "20px");
			mainTable.setWidget(i, 2, generateFocusButton(address));

		
	}
	private void focusOnAdd(){}
	
	
	public void removeRow(Address address){
		if(!app.loggedIn()){
			mainTable.removeRow(findIndexInList(address)+1);
			addressList.remove(address);	
			app.mapPanel.remove(address);
		}
		else
			ClientUtils.reload();

	}
	
	public void refresh(){
		clearAddresses();
		setEnabled(true);
		if(app.loggedIn()){
			setRows();
		}
			
	}
	
	public void setEnabled(boolean enabled){
		this.setVisible(enabled);
		this.addressSearch.setEnabled(enabled);
		this.searchButton.setEnabled(enabled);
	}
	
	public void clearAddresses(){
		mainTable.removeAllRows();
		mainTable.setWidget(0, 0, addressSearch);
		mainTable.setWidget(0, 1, searchButton);
	}
	
	
	private void setRows(){
		
		for(Address address:app.getUser().getAddresses()){
			addRow(address);
		}
		for(Address address:app.getUser().getAddresses()){
			drawOnMap(address);
		}
		
		
	}
	
	private void emptySearchBox(){
		addressSearch.setText("");
	}
	
	public int findIndexInList(Address a){
		List<Address> list=null;
		int i =-2;
		if(app.loggedIn())
			list=app.getUser().getAddresses();
		else
			list=this.addressList;
		for(Address anAddress: list)
			if (a.equals(anAddress))
				i= list.indexOf(anAddress);
		return i;
		
	}
	
	private boolean hasAddress(Address a){
		List<Address> list;
		if(app.loggedIn())
			list=app.getUser().getAddresses();
		else
			list=addressList;
		for(Address anAddress: list){
			if(anAddress.equals(a))
				return true;
		}
		return false;
		
	}
		
	
	private Button generateDeleteButton(final Address address){
		Button button = new Button();
		button.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				//Address addressObject =new Address(app.getUser(),address.);
				if(app.loggedIn())
					app.removeAddress(address);
				else
					removeRow(address);
				
			}
		});
		button.setText("X");
		return button;
	}
	
	private Image generateFocusButton(final Address address){
		final Image image = new Image(ClientConstants.ADDRESS_MARKER_URL);
		image.setSize("20px", "20px");
		image.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				//image.setUrl(ClientConstants.ADDRESS_MARKER_FOCUSED_URL);
				AddressMarker marker=app.mapPanel.getAdresses().get(address);
				LatLng location =marker.getLatlon();
				app.mapPanel.getMap().setCenter(location);
			}
		});
		
		return image;
	}
	
	public void getLatLonAndPlot(final Address s){
		
		GeocoderRequest request = GeocoderRequest.create();
		request.setAddress(s.getFullAddress());
		
		Geocoder geocode = Geocoder.create();
		geocode.geocode(request, new Geocoder.Callback() {
			
			@Override
			public void handle(JsArray<GeocoderResult> a, GeocoderStatus b) {
				
				if (b != GeocoderStatus.OK) {
					Window.alert("The address was not found");
					searchButton.setEnabled(true);
					return;
				}else{
				
				if(app.loggedIn()){
					app.addAddress(s);
				}
				else{
					addRow(s);
					searchButton.setEnabled(true);
				}
				}
				LatLng pos = a.get(0).getGeometry().getLocation();
				app.mapPanel.plot(new AddressMarker(s, pos,app.mapPanel.getMap()));
				
			}

			
		});
		
	}
	
	public void drawOnMap(final Address s){
		
		GeocoderRequest request = GeocoderRequest.create();
		request.setAddress(s.getFullAddress());
		
		Geocoder geocode = Geocoder.create();
		geocode.geocode(request, new Geocoder.Callback() {
			
			@Override
			public void handle(JsArray<GeocoderResult> a, GeocoderStatus b) {
				
				if (b != GeocoderStatus.OK) {
					if(app.loggedIn()){
						
					}else{
					
					Window.alert("not found");
					}
				}else{
				
				LatLng pos = a.get(0).getGeometry().getLocation();
				app.mapPanel.plot(new AddressMarker(s, pos,app.mapPanel.getMap()));
				}
				
			}

			
		});
		
	}
	
	public Image getFocusImage(int i){
		return (Image) mainTable.getWidget(i, 2);
	}
	
}
