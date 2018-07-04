package com.cs310.TheRebooter.client;

import java.util.ArrayList;

import com.cs310.TheRebooter.shared.Address;
import com.cs310.TheRebooter.shared.AbstractUser;

import com.google.appengine.api.socket.SocketServicePb.AddressPort;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddressesWidget extends VerticalPanel {

	//private AbstractUser user=null;
	private ArrayList<Address> addresses = new ArrayList<Address>();
	private FlexTable addressTable = new FlexTable();
	private HorizontalPanel interactionBar = new HorizontalPanel();
	private Button addButton = new Button("add");
	private TextBox addressText = new TextBox();
	private TheRebooter father;
	
	public AddressesWidget(TheRebooter f){
		//TODO set action when button is clicked
		//setUser(u);
//		addButton.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				String s = addressText.getText().trim();
//				//Address address=new Address(user,s);
//				addAddress(address);
//				//i//f(user!=null)
//					//user.getAddresses().add(address);
//				
//				//addAdressToDatabase(new Address(user,s));
//				
//			}
//		});
		this.father=f;
		addressText.setText("SEARCH (YOU NEED TO LOGIN)");
		interactionBar.add(addressText);
		interactionBar.add(addButton);
		this.add(interactionBar);
		this.add(addressTable);
	}
	
	private boolean addAddress(Address a){
		if(a==null)
			return false;
		addresses.add(a);
		
		int i = addressTable.getRowCount();
		final AddressRow arow =  new AddressRow(a);
		arow.setAction(new ClickHandler() {
			//int row = i;
			@Override
			public void onClick(ClickEvent event) {
				//removeAddress(arow.getAddress());
				//father.removeAddress(arow.getAddress());
			}
		});
		console(a.getFullAddress());
		console("added row");
		addressTable.setWidget(i, 0, arow.getAddressName());
		addressTable.setWidget(i, 1, arow.getDeleteAddressButton());

		
		return true;
	}
	
	public boolean addAddressList(ArrayList<Address> address){
		if (address==null)
			return false;
		addresses= new ArrayList<Address>();
		//this.addresses=address;
		addressTable.removeAllRows();
		for(Address a:address){
			addAddress(a);
		}
		//this.addresses=address;
		return true;
		
	}
	
	public boolean removeAddress(Address a){
		if(a==null||!this.addresses.contains(a))
			return false;
		
		addresses.remove(a);
		addAddressList(this.addresses);
			
		return true;
	}
	
//	public void setUser(AbstractUser u){
//		
//		if(u==null){
//			console("null user");
//			return;
//		}
//		console(u.getEmail());
//		//this.user=u;
////		for(Address a : u.getAddresses()){
////			console(a.getFullAddress());
////		}
//		//addresses= user.getAddresses();
//		addAddressList(u.getAddresses());
//	}
	
	public String getText(){
		String s =addressText.getText().trim();
		addressText.setText("");
		return s;
	}
	
	public void setAction(ClickHandler action){
		this.addButton.addClickHandler(action);
	}
	

	
	public static native void console(String text)
	/*-{
	    console.log(text);
	}-*/;
	
}

class AddressRow extends HorizontalPanel{
	private Address a;
	private Label addressName;
	private Button deleteAddressButton;
	
	public AddressRow(Address a){
		this.a=a;
		addressName=new Label(this.a.getFullAddress());
		deleteAddressButton= new Button("X");
		this.add(addressName);
		this.add(deleteAddressButton);
	}
	public void setAction(ClickHandler action){
		deleteAddressButton.addClickHandler(action);
	}
	public Address getAddress(){
		return this.a;
	}
	public Label getAddressName() {
		return addressName;
	}
	public void setAddressName(Label addressName) {
		this.addressName = addressName;
	}
	public Button getDeleteAddressButton() {
		return deleteAddressButton;
	}
	public void setDeleteAddressButton(Button deleteAddressButton) {
		this.deleteAddressButton = deleteAddressButton;
	}
	
	
	
}

class MyHandler implements ClickHandler{
	
	int i;
	
	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}
	
} 
