package com.cs310.TheRebooter.client;

import java.util.HashMap;

import com.cs310.TheRebooter.shared.Address;
import com.cs310.TheRebooter.shared.BusinessServiceResponse;
import com.cs310.TheRebooter.shared.Coordinate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;

public class MapPanel extends Composite {

	private GoogleMap map;
	
	
	
	private HashMap<Address, AddressMarker> addresses;
	
	private TheRebooter app;
	private static final LatLng vancouver =LatLng.create(49.2250, -123.1083);
	
	public MapPanel(TheRebooter theRebooter){

		app=theRebooter;
		VerticalPanel panel = new VerticalPanel();
		initWidget(panel);
		setStyleName(ClientConstants.MAPPANEL_ID);
		this.setSize("inherit","750px");

		RootPanel.get(ClientConstants.MAPPANEL_CONTAINER).add(this);

		addresses= new HashMap<Address, AddressMarker>();
		MapOptions myOptions = MapOptions.create();
		myOptions.setZoom(12.0);
		myOptions.setCenter(vancouver);
		myOptions.setMapTypeId(MapTypeId.ROADMAP);
		myOptions.setDisableDefaultUi(true);
		myOptions.setZoomControl(true);
		map = GoogleMap.create(this.getElement(), myOptions);
		initMapPanel();

	}
	
	public GoogleMap getMap(){
		return this.map;
	}
	
	public void plot(AddressMarker marker){
		if(addresses.containsKey(marker.getAddress()))
			return;
		
		addresses.put(marker.getAddress(), marker);
		marker.addToMap();
		map.setCenter(marker.getLatlon());
		fetchBusinesses(marker);
	}
	
	public void remove(Address address){
		if(!addresses.containsKey(address))
			return;
		
		addresses.remove(address).removeFromMap();

	}
	
	public void removeAll(){
		for(Address a : addresses.keySet())
			remove(a);
	}
	
	public void refresh(){
		if(!app.loggedIn())
			removeAll();
		else{
			for(Address map:addresses.keySet())
				if(!app.getUser().getAddresses().contains(map))
					remove(map);
					
		}
			
	}
	
	public void centerMap(){
		map.setCenter(vancouver);
	}
	
	private void initMapPanel(){
		
	}
	public HashMap<Address, AddressMarker> getAdresses(){
		return this.addresses;
	}
	
	public void setBusinessesAndPlot(BusinessServiceResponse b){
		if(b.getRequestAddress()==null||!addresses.containsKey(b.getRequestAddress()))
			return;
		AddressMarker marker = addresses.get(b.getRequestAddress());
		marker.setBusinesses(b.getBusinesses());
		marker.displayInfo();
		map.setCenter(LatLng.create(b.getCoord().getLatitude(), b.getCoord().getLongitude()));
	}
	
	private void fetchBusinesses(AddressMarker a){
		Coordinate location = new Coordinate(a.getLatlon().lat(), a.getLatlon().lng());
		
		BusinessServiceResponse r = new BusinessServiceResponse(a.getAddress(), location, 0.005);
		app.getBusineeness(r);
	}
	
	
	
}
