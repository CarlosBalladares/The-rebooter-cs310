package com.cs310.TheRebooter.client;

import com.cs310.TheRebooter.shared.Business;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.InfoWindowOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;

public class BusinessMarker {

	private Business 		business;
	private Marker	 		marker;
	private InfoWindow 		dialog;
	private GoogleMap		map;
	
	public BusinessMarker(Business b, GoogleMap map){
		setBusiness(b);
		setMap(map);
		initMarker();
		initDialog();
	}
	
	public void displayInfo(){
		dialog.open(map,marker);
	}
	
	private void initMarker(){
		LatLng businessLocation= LatLng.create(business.getLatitude(), business.getLongitude());
		MarkerOptions businessMarkerOptions = MarkerOptions.create();
		determineTypeIcon(businessMarkerOptions);
		businessMarkerOptions.setIcon(MarkerImage.create(ClientConstants.BUSINESS_MARKER_URL));
		businessMarkerOptions.setVisible(false);
		marker=Marker.create(businessMarkerOptions);
		marker.addClickListener(new Marker.ClickHandler() {
			
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				displayInfo();
			}
		});
		addToMap();
	}
	
	private void initDialog(){
		InfoWindowOptions dialogOptions= InfoWindowOptions.create();
		dialogOptions.setContent("Name: "+business.getBusinessName()+"\nType:"+business.getType());
		dialogOptions.setMaxWidth(100);
		dialog= InfoWindow.create(dialogOptions);
	}
	
	private void addToMap(){
		marker.setMap(map);
	}
	
	public void removeFromMap(){
		map=null;
		marker.setMap(map);
	}
	
	public void showMarker(){
		marker.setVisible(true);
	}
	
	public void hideMarker(){
		dialog.close();
		marker.setVisible(false);
	}
	
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public Marker getMarker() {
		return marker;
	}
	public void setMarker(Marker marker) {
		this.marker = marker;
	}
	public InfoWindow getDialog() {
		return dialog;
	}
	public void setDialog(InfoWindow dialog) {
		this.dialog = dialog;
	}
	public GoogleMap getMap() {
		return map;
	}
	public void setMap(GoogleMap map) {
		this.map = map;
	}
	
	
	public void determineTypeIcon(MarkerOptions s){
		String type= business.getType();
			if(type.contains("restaurant")||type.contains("food"))
					s.setIcon(ClientConstants.RESTAURANTICON);
			else if(type.contains("hospital")||type.contains("medical")||type.contains("health"))
					s.setIcon(ClientConstants.HOSPITALICON);
			else if(type.contains("financial")||type.contains("money")||type.contains("bank"))
					s.setIcon(ClientConstants.FINANCIALICON);
			else if(type.contains("retail")||type.contains("supermarket"))
					s.setIcon(ClientConstants.RETAILICON);
			else 
				s.setIcon(ClientConstants.BUSINESS_MARKER_URL);
	}
	
	
	
}
