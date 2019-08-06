package org.inbus.teamfiletransferclient.impl;

import java.io.FileInputStream;
import java.util.Vector;

import org.inbus.teamfiletransferclient.model.ConnectionInfoModel;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPConnect {
	
	private int treeCount;
	
	public boolean connectService(ConnectionInfoModel connectionInfoModel) {
		
		
		
		Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
 
        FileInputStream in = null;
        
        JSch jsch = new JSch();
		
		try {

            SFTPUtil util = new SFTPUtil();
            util.init(connectionInfoModel.getHost(), connectionInfoModel.getUserName(), connectionInfoModel.getPassword(), connectionInfoModel.getPort());
            
            String path = "/home/testuser";
            
            Vector<ChannelSftp.LsEntry> list = util.getFileList(path);
            
            treeCount = 0;
            
            for(ChannelSftp.LsEntry oListItem : list) {
            	if (!oListItem.getAttrs().isDir()) {
    				System.out.println(oListItem.getFilename() + "파일입니다.");

//    				String fileName = null;
//    				fileName = oListItem.getFilename();
//    				String saveDir = rootPath + oListItem.getFilename();
//    				util.download(dir, fileName, saveDir);

    			} else {
    				System.out.println(oListItem.toString() + " 디렉토리입니다.");
    				treeCount++;
    			}
            }
            
            System.out.println("=> Connected to " + connectionInfoModel.getHost());
            
            util.disconnection();
		}catch (Exception e) {
			return false;
		}	
		return true;
	}

	public int getTreeCount() {
		return treeCount;
	}

}
