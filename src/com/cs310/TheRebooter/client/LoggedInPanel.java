package com.cs310.TheRebooter.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoggedInPanel extends Composite {
	
	private ViewPanel viewPanel;
	private EditPanel editPanel;
	
	private TheRebooter app;
	
	public LoggedInPanel(TheRebooter theRebooter){
		app= theRebooter;
		
		viewPanel= new ViewPanel(app);
		editPanel= new EditPanel(app);
		
		VerticalPanel panel= new VerticalPanel();
		
		panel.add(viewPanel);
		panel.add(editPanel);
		
		initWidget(panel);
		setStyleName(ClientConstants.LOGGGEDINPANEL_ID);
		setVisible(false);

	}
	public ViewPanel getViewPanel(){
		return viewPanel;
	}
	public EditPanel getEditPanel(){
		return editPanel;
	}
	public void refresh(){
		setEnabled(app.loggedIn());
		viewPanel.refresh();
		editPanel.refresh();
	}
	
	public void setEnabled(boolean enabled){
		this.setVisible(enabled);
	}
	
}
