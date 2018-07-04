package com.cs310.TheRebooter.client;

import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.Marker;

public class MarkerPosition {
	
	private static Marker marker = Marker.create();

	
	public static void createInstance(){
		
		//marker.setMap(MapPanel.map);
	}
	
	public static void newPosition(LatLng latlng){
		
	
		marker.setPosition(latlng);
		//MapPanel.map.setCenter(latlng);
	}

}
