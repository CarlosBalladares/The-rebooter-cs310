package com.cs310.TheRebooter.client;

import com.cs310.TheRebooter.shared.Business;
import com.google.gwt.core.client.GWT;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.SplitResult;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

public class ClientUtils {

	public static void redirect(String url){
		Window.Location.replace(url);
	}
	
	public static void reload(){
		Window.Location.reload();
	}
	
	public static void Alert(String alertText){
		Window.alert(alertText);
	}
	
	public static String getBaseURL(){
		return GWT.getHostPageBaseURL();
	}
	
	public static String getJsessionID(){
		return Cookies.getCookie("JSESSIONID");
	}
	
	public static String escapeHTML(String html){
		return SafeHtmlUtils.htmlEscape(html);
	}
	
	public static native void console(String text)
	/*-{
	    console.log(text);
	}-*/;
	
	//from stackoverflow due to the lack of a string formatter in gwt client side
	public static String format(final String format, final Object... args) {
		  final RegExp regex = RegExp.compile("%[a-z]");
		  final SplitResult split = regex.split(format);
		  final StringBuffer msg = new StringBuffer();
		  for (int pos = 0; pos < split.length() - 1; ++pos) {
		    msg.append(split.get(pos));
		    msg.append(args[pos].toString());
		  }
		  msg.append(split.get(split.length() - 1));
		  return msg.toString();
	}

	public static boolean isAlphaNumeric(String s){
	    String pattern= "^[\\p{L} .'-]+$";
	        if(s.matches(pattern)){
	            return true;
	        }
	        return false;   
	}
	public static boolean isAlphabetic(String s){
	    String pattern= "^[A-Za-z ]+$";
	        if(s.matches(pattern)){
	            return true;
	        }
	        return false;   
	}
	public static boolean validAddressFormat(String s){
	    String pattern= "^[a-zA-Z0-9,!? ]*$";
	        if(s.matches(pattern)){
	            return true;
	        }
	        return false;   
	}
	
	public static boolean validAddressString(String s){
		return s!=null&&!s.equals("")&&validAddressFormat(s)&&s.length()<60;
	}
	
	public static boolean validBusiness(Business b){
		return !isNull(b)&&!isNull(b.getAddress())&&!isNull(b.getType());
		
	}
	
	public static boolean isNull(Object o){
		return o==null;
	}
}
