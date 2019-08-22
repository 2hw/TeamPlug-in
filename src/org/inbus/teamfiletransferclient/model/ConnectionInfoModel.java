package org.inbus.teamfiletransferclient.model;

/**
* FTP 접속 정보 Model
* 접속하려는 서버의 정보를 담는 Model
* 
* @author lhw
* @version 1.0
* @since 2019.08.06
*/

public class ConnectionInfoModel {
	
	private String host;		//호스트
	private String userName;	//사용자명
	private String password;	//비밀번호
	private int port;			//포트번호
	
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
