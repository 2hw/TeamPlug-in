package org.inbus.teamfiletransferclient.exceptions;

public class InvalidUtilInformationException extends Exception{
	public InvalidUtilInformationException() {
		super("FileTransfer Information is not valid.");
	}
	
	public InvalidUtilInformationException(String message) {
		super("FileTransfer Information " + message + "  is not valid.");
	}
}
