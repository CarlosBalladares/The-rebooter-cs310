package com.cs310.TheRebooter.client;

public class NotLoggedInException extends Exception
{
	public NotLoggedInException()
	{
		super();
	}
	
	public NotLoggedInException(String message)
	{
		super(message);
	}
}
