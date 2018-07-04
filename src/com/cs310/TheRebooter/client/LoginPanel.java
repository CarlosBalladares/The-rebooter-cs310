package com.cs310.TheRebooter.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginPanel extends Composite {
	
	private Label title;
	private Button facebookLogin;
	private Button googleLogin;
	
	private TheRebooter app;
	
	public LoginPanel(TheRebooter theRebooter){
		app= theRebooter;
		
		setWidgets();
		setClickHandlers();
		
		VerticalPanel loginButtonsPanel = 	new VerticalPanel();
		VerticalPanel titlePanel= 			new VerticalPanel();
		VerticalPanel panel = 				new VerticalPanel();
		
		titlePanel.setStyleName			(ClientConstants.LOGIN_PANEL_TITLE_ID);
		loginButtonsPanel.setStyleName	(ClientConstants.LOGIN_BUTTONS_PANNEL_ID);
		
		loginButtonsPanel.add(facebookLogin);
		loginButtonsPanel.add(googleLogin);
		titlePanel.add(title);
		
		panel.add(titlePanel);
		panel.add(loginButtonsPanel);		
		
		initWidget(panel);
		setStyleName(ClientConstants.USERPANEL_ID);
		setVisible(false);
	}
	
	public void refresh(){
		setEnabled(!app.loggedIn());
	}
	
	
	private void setWidgets(){
		title= 			new Label	(ClientConstants.LOGIN_PANEL_TITLE);
		facebookLogin= 	new Button	(ClientConstants.LOGIN_PANEL_FACEBOOK_LOGIN);
		googleLogin=	new Button	(ClientConstants.LOGIN_PANEL_GOOGLE_LOGIN);
	}
	
	private void setClickHandlers(){
		facebookLogin.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				app.login("facebook");
				
			}
		});
		
		googleLogin.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				app.login("google");
				
			}
		});
	}
	
	public void setEnabled(boolean enabled){
		this.setVisible(enabled);
		facebookLogin.setEnabled(enabled);
		googleLogin.setEnabled(enabled);
		
	}
	
	
}
