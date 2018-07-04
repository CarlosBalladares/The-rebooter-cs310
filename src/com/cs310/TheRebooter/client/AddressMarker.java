package com.cs310.TheRebooter.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cs310.TheRebooter.shared.Address;
import com.cs310.TheRebooter.shared.Business;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.InfoWindowOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;

public class AddressMarker {
	
	private Address			address;
	private LatLng 			latlon;
	private Marker			marker;
	private GoogleMap 		map;
	private InfoWindow 		dialog;
	private Map<Business, BusinessMarker> businesses;	
	
	private boolean 	dialogOpen;
	
	private boolean fetchedBusinesses;
	
	public AddressMarker(Address a, LatLng l,GoogleMap mp) {
		this.address=a;
		businesses = new HashMap<Business, BusinessMarker>();
		fetchedBusinesses=false;
		setLatlon(l);
		setMap(mp);
		initMarker();
		initDialog();
	}
	

	public Address getAddress(){
		return this.address;
	}
	
	public void displayInfo(){
		if(!fetchedBusinesses)
			dialog.setContent("looking for addresses ...");
		else if(businesses.keySet().size()==0)
			dialog.setContent("Sorry no businesses were found around this address");
		else
			showBusinesses();
		dialog.open(map, marker);
		dialogOpen=true;
	}
	
	public void hideInfo(){
		dialog.close();
		hideBusinesses();
		dialogOpen=false;
	}
	
	public void showBusinesses(){
		if(businesses.keySet().size()==0&&fetchedBusinesses)
			ClientUtils.Alert("no businesses to show");
		else
			for(Business b: businesses.keySet())
				businesses.get(b).showMarker();
	}
	
	
	public void hideBusinesses(){
		if(businesses==null||businesses.keySet().size()==0)
			return;
		else
			for(Business b: businesses.keySet())
				businesses.get(b).hideMarker();
	}
	
	public void addToMap(){
		marker.setMap(map);
		dialog.open(map, marker);
		dialogOpen=true;
	}
	
	public void removeFromMap(){
		for(Business b:businesses.keySet())
			businesses.get(b).removeFromMap();
		map=null;
		marker.setMap(map);
	}
	
	public void hideMarker(){
		marker.setVisible(false);
	}
	
	public void showMarker(){
		marker.setVisible(true);
	}
	
	public void setPosition(LatLng l){
		marker.setPosition(l);
		map.setCenter(l);
	}
	
	private void initDialog() {
		
		InfoWindowOptions dialogOptions= InfoWindowOptions.create();
		dialogOptions.setContent("Looking for adresses around: "+address.getFullAddress()+" ...  ");
		dialogOptions.setMaxWidth(300);
		dialog= InfoWindow.create(dialogOptions);
		dialog.addCloseClickListener(new InfoWindow.CloseClickHandler(){
			@Override
			public void handle() {
				dialogOpen=false;
				hideBusinesses();
			}
		});
		dialogOpen=false;
		
	}
	
	private void initMarker(){
		
		MarkerOptions myOptions = MarkerOptions.create();
		myOptions.setPosition(latlon);
		marker=Marker.create(myOptions);
		marker.addClickListener(new Marker.ClickHandler() {
			
			@Override
			public void handle(MouseEvent event) {
				if(!dialogOpen)
					displayInfo();
				else
					hideInfo();
			}
		});
		
	}

	public LatLng getLatlon() {
		return latlon;
	}
	public void setLatlon(LatLng latlon) {
		this.latlon = latlon;
	}
	public Marker getMarker() {
		return marker;
	}
	public void setMarker(Marker marker) {
		this.marker = marker;
	}
	public GoogleMap getMap() {
		return map;
	}
	public void setMap(GoogleMap map) {
		this.map = map;
	}
	public Collection<Business> getBusinesses(){
		return businesses.keySet();
	}
	public void setBusinesses(List<Business> businesses){ 
		dialog.close();
		dialogOpen=false;
		if(businesses!=null)
			for (Business b: businesses) {
				if(ClientUtils.validBusiness(b))
					this.businesses.put(b, new BusinessMarker(b, map));
			}
		String description= getAddressSummary();
		String newtext=address.getFullAddress()+"\n"+description;
		dialog.setContent(newtext);
		fetchedBusinesses=true;
	}
	
	private String getAddressSummary(){
		int restaurants=0;
		int health=0;
		int financial=0;
		int retail=0;
		int other=0;
		
		if(businesses.size()==0)
			return "There no registered businesses around";
		
		for (Business b:businesses.keySet()){
			String type = b.getType().toLowerCase();
			if(type.contains("restaurant")||type.contains("food"))
					restaurants++;
			else if(type.contains("hospital")||type.contains("medical")||type.contains("health"))
					health++;
			else if(type.contains("financial")||type.contains("money")||type.contains("bank"))
					financial++;
			else if(type.contains("retail")||type.contains("supermarket"))
					retail++;
			else 
				other++;
		}
		
		StringBuffer buff = new StringBuffer();
		buff.append("There are:\n");
		
		if(restaurants>0)
			buff.append(restaurants+" Restaurants\n");
		if(health>0)
			buff.append(health+		" Health Services\n");
		if(financial>0)
			buff.append(financial+	" Financial Services\n");
		if(retail>0)
			buff.append(retail+		" Retail Stores\n");
		if(other>0)
			buff.append("and "+other+"others around");
		
		
		
		String response = buff.toString();
			
		return response;
	}
	
	public void fetchUnseccessful(){
		dialog.close();
		dialogOpen=false;
		this.fetchedBusinesses=true;
	}
	
	
	
}
