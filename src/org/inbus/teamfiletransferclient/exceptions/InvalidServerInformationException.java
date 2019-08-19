package org.inbus.teamfiletransferclient.exceptions;

public class InvalidServerInformationException extends Exception{

	public InvalidServerInformationException() {
		super("Server Connection Information is not valid.");
	}
	
	public InvalidServerInformationException(String message) {
		super("Server Connection Information " + message + "  is not valid.");
	}

}
