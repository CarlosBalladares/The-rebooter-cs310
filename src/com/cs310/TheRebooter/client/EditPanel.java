package com.cs310.TheRebooter.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class EditPanel extends Composite {

	private FlexTable 	formTable;
	private Label		nameField;
	private TextBox		nameText;
	private Button		sendButton;
	
	private TheRebooter app;
	
	public EditPanel(TheRebooter theRebooter){
		
		app=theRebooter;
		
		setWidgets();
		setHandlers();
		
		formTable.setWidget(0, 0, nameField);
		formTable.setWidget(0, 1, nameText);
		formTable.setWidget(1, 1, sendButton);
		
		initWidget(formTable);
		setStyleName(ClientConstants.EDIT_PANEL_ID);
		setVisible(false);
		
	}
	
	public void setEditBoxText(String value){
		nameText.setText(ClientUtils.escapeHTML(value));
	}
	
	private void setHandlers() {
		// TODO set click and action handlers
		sendButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String s = nameText.getText().trim();
				if(!ClientUtils.isAlphabetic(s)){
					ClientUtils.Alert(ClientConstants.EDIT_PANEL_INVALID_INPUT);
					return ;
				}
				
				app.getUser().setName(s);
				
				ViewPanel viewPanel = app.userPanel.getLoggedInPanel().getViewPanel();
				if(app.loggedIn())
					app.editUser();
				setVisible(false);
				setEnabled(false);
				viewPanel.setEnabled(true);
			}
		});
		nameText.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				nameText.setText("");
			}
		});
		
		nameText.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				char pressed = event.getCharCode();
				if (pressed == KeyCodes.KEY_ENTER){
					sendButton.click();
				}
			}
		});
		
	}

	private void setWidgets(){
		formTable= new FlexTable();
		nameField= new Label(ClientConstants.EDIT_PANEL_NAME_LABEL);
		nameText=  new TextBox();
		sendButton=new Button(ClientConstants.EDIT_PANEL_EDITBTN);
		
		sendButton.getElement().getStyle().setFontSize(80, Unit.PCT);
	}
	
	public void refresh(){
		setEnabled(app.loggedIn());
		if(app.loggedIn())
			nameText.setText(ClientUtils.escapeHTML(app.getUser().getName()));
	}
	
	public void setEnabled(boolean enabled){
		//this.setVisible(enabled);
		sendButton.setEnabled(enabled);
		nameText.setEnabled(enabled);
	}
	
}
