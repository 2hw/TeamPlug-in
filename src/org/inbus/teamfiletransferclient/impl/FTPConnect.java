package org.inbus.teamfiletransferclient.impl;

import java.io.FileInputStream;

import org.inbus.teamfiletransferclient.model.ConnectionInfoModel;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FTPConnect {
	
	public boolean connectService(ConnectionInfoModel connectionInfoModel) {
		
		Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
 
        FileInputStream in = null;
        
        JSch jsch = new JSch();
		
		try {
            session = jsch.getSession(connectionInfoModel.getUserName(), connectionInfoModel.getHost(), connectionInfoModel.getPort());
            session.setPassword(connectionInfoModel.getPassword());
         
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no"); // 인증서 검사를 하지 않음
            session.setConfig(config);
            
            session.connect();
 
            channel = session.openChannel("sftp");
            channel.connect();
 
            channelSftp = (ChannelSftp)channel;
            System.out.println("=> Connected to " + connectionInfoModel.getHost());
            
        
		}catch (Exception e) {
			return false;
		}	
		return true;
	}
}
