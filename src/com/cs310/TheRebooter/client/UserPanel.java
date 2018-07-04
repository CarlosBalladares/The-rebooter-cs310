package com.cs310.TheRebooter.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserPanel extends Composite {
	
	private LoginPanel 		loginPanel;
	private LoggedInPanel	loggedInPanel;

	private TheRebooter		app;
	
	public UserPanel(TheRebooter theRebooter){
		
		app=			theRebooter;
		loginPanel= 	new LoginPanel	 (app);
		loggedInPanel=	new LoggedInPanel(app);
		
		
		VerticalPanel panel = new VerticalPanel();
		panel.add(loginPanel);
		panel.add(loggedInPanel);
		
		
		initWidget(panel);
	}
	
	public LoggedInPanel getLoggedInPanel(){
		return loggedInPanel;
	}
	
	public void refresh(){
		loginPanel.refresh();
		loggedInPanel.refresh();
	}
	
}
