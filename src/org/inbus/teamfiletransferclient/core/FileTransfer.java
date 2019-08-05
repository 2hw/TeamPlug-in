package org.inbus.teamfiletransferclient.core;

import java.io.FileInputStream;

import org.inbus.teamfiletransferclient.exceptions.InvalidServerInformationException;
import org.inbus.teamfiletransferclient.impl.FTPConnect;
import org.inbus.teamfiletransferclient.model.ConnectionInfoModel;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FileTransfer {
	
	private ConnectionInfoModel connectInfo = new ConnectionInfoModel();
	private FTPConnect ftpConnect = new FTPConnect();
	
	public boolean remoteConnect(String host, String userName, String password, String port) throws Exception {
		
		checkConnectionInfo(host);
		checkConnectionInfo(userName);
		checkConnectionInfo(password);
		checkConnectionInfo(port);
		
		this.connectInfo.setHost(host);
		this.connectInfo.setUserName(userName);
		this.connectInfo.setPassword(password);
		this.connectInfo.setPort(Integer.parseInt(port));
		
		System.out.println("=> Connecting to " + host);
		
		return ftpConnect.connectService(connectInfo);
	}
	
	private boolean checkConnectionInfo(String param) throws Exception{
		boolean valid = true;
		
		if(param == null || param.isEmpty()) {
			throw new InvalidServerInformationException(String.valueOf(param));
		}
		
		return valid;
	}
	
}
