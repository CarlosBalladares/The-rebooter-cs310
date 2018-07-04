package com.cs310.TheRebooter.client;

import java.util.ArrayList;

import com.cs310.TheRebooter.shared.Business;

public class SampleListofBusinesses {
	private ArrayList<Business> lofb = new ArrayList<Business>();
	
	public ArrayList<Business> construct(){
	Business b1 = new Business("restaurant","Keg","dummyAddress",49.2270, -123.1093);
	Business b2 = new Business("gas station","Shell","dummyAddress",49.2280, -123.1143);
    Business b3 = new Business("hospital","ChildrenHospital","dummyAddress",49.2230, -123.1013);
    Business b4 = new Business("restaurant","Sushi","dummyAddress",49.2280, -123.1149);
	Business b5 = new Business("shop","name","dummyAddress",49.2354, -123.1143);
	Business b6 = new Business("mall","name","dummyAddress",49.2340, -123.1043);
    Business b7 = new Business("school","name","dummyAddress",49.2340, -123.1143);
    Business b8 = new Business("park","name","dummyAddress",49.2150, -123.1002);
	Business b9 = new Business("club","name","dummyAddress",49.2330, -123.1153);
	Business b10 = new Business("repair","Shell","dummyAddress",49.22220, -123.1343);
    Business b11 = new Business("college","ChildrenHospital","dummyAddress",49.2200, -123.1053);
    Business b12 = new Business("lumber yard","Sushi","dummyAddress",49.2250, -123.1099);
     lofb.add(b1);
     lofb.add(b2);
     lofb.add(b3);
     lofb.add(b4);
     lofb.add(b5);
     lofb.add(b6);
     lofb.add(b7);
     lofb.add(b8);
     lofb.add(b9);
     lofb.add(b10);
     lofb.add(b11);
     lofb.add(b12);
	return lofb;
	}

}
