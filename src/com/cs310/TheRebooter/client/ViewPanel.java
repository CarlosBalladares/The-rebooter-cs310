package com.cs310.TheRebooter.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ViewPanel extends Composite{
	
	private Label 	welcome;
	private Button 	editButton;
	private Button logoutButton;
	
	private TheRebooter app;
	
	
	public ViewPanel(TheRebooter theRebooter){
		
		app=theRebooter;
		setWidgets();
		setHandlers();
		
		VerticalPanel panel = new VerticalPanel();

		panel.add(welcome);
		panel.add(editButton);
		panel.add(logoutButton);		
		
		
		initWidget(panel);
		setStyleName(ClientConstants.VIEWPANEL_ID);
		
	}
	
	private void setWidgets(){
		welcome= new Label		(ClientConstants.VIEWPANEL_MESSAGE);
		editButton= new Button	(ClientConstants.VIEWPANEL_EDITBTN);
		logoutButton= new Button(ClientConstants.VIEWPANEL_LOGOUTBTN);
	}
	
	private void setHandlers(){
		editButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(app.loggedIn()){
					EditPanel ePanel = app.userPanel.getLoggedInPanel().getEditPanel();
					//ePanel.setEditBoxText(app.getUser().getName());
					ePanel.setVisible(true);
					ePanel.setEnabled(true);
					setEnabled(false);
				}
				//TODO put name on textbox and set editpanel visible and also disable buttons
				
			}
		});
		logoutButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				app.logout();
			}
		});
	}
	
	public void refresh(){
		setEnabled(app.loggedIn());
		if(app.loggedIn()){
			setWelcomeMessage();
		}
	}
	
	public void setEnabled(boolean enabled){
		this.setVisible(enabled);
		editButton.setEnabled(enabled);
		logoutButton.setEnabled(enabled);
	}
	
	private void setWelcomeMessage(){
		welcome.setText(ClientUtils.escapeHTML(ClientConstants.VIEWPANEL_MESSAGE+app.getUser().getName()));
	}
	
}
