package org.inbus.teamfiletransferclient.model;

public class ConnectionInfoModel {
	
	private String host;
	private String userName;
	private String password;
	private int port;
	
	public ConnectionInfoModel() {
	}

	public ConnectionInfoModel(String host, String userName, String password, int port) {
		super();
		this.host = host;
		this.userName = userName;
		this.password = password;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
