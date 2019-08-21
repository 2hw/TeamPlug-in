package org.inbus.teamfiletransferclient.core;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.inbus.teamfiletransferclient.exceptions.InvalidServerInformationException;
import org.inbus.teamfiletransferclient.exceptions.InvalidUtilInformationException;
import org.inbus.teamfiletransferclient.impl.SFTPUtil;
import org.inbus.teamfiletransferclient.model.ConnectionInfoModel;
import org.inbus.teamfiletransferclient.model.DirectoryModel;
import org.inbus.teamfiletransferclient.model.FileTransferModel;
import org.inbus.teamfiletransferclient.model.TreeParent;

import com.jcraft.jsch.ChannelSftp;


public class FileTransfer {
	
	private ConnectionInfoModel connectionInfoModel = new ConnectionInfoModel();
	private SFTPUtil util = new SFTPUtil();
	private List<DirectoryModel> tfList;
	private String remoteHome = "/home/testuser";
	
	private String pattern = "(.+)\\s+(\\d+)\\s+(\\S+\\s+\\S+)\\s+(\\d+)\\s+(.+\\s+\\d+\\s+[\\d:]+)\\s+(.*)";
	private Pattern ptrn = Pattern.compile(pattern);
	Matcher matcher;
	
	
	public TreeParent remoteConnect(String host, String userName, String password, String port) {
		tfList = new ArrayList<DirectoryModel>();
		
		try {
			checkConnectionInfo(host);
			checkConnectionInfo(userName);
			checkConnectionInfo(password);
			checkConnectionInfo(port);
			
			this.connectionInfoModel.setHost(host);
			this.connectionInfoModel.setUserName(userName);
			this.connectionInfoModel.setPassword(password);
			this.connectionInfoModel.setPort(Integer.parseInt(port));
			
			System.out.println("=> Connecting to " + host);
			
			util.init(connectionInfoModel.getHost(), connectionInfoModel.getUserName(), connectionInfoModel.getPassword(), connectionInfoModel.getPort());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("=> Connected to " + host);
		TreeParent rtTP = getTreeDirectory(remoteHome);
		
//		util.disconnection();

		return rtTP;
		
	}
	
	public TreeParent getTreeDirectory(String path) {
		String name = path.split("/")[path.split("/").length - 1];
		
		// 1. 파일 객체 생성 (path 정보를 가지는 파일 만듬)
		// 현재 경로를 파일 객체로 생성
		
		TreeParent retTP = new TreeParent(name, path);
		
		List<DirectoryModel> TFMList = getFileSystem(path);
		
		for(DirectoryModel tfm : TFMList) {
			
			if(tfm.isFolder())
				retTP.addChild(getTreeDirectory(path + "/" + tfm.getName()));
		}
        
		return retTP;
        
	}
	
	/**
	 * 경로에 대한 디렉토리 정보 가져오기
	 * 
	 * @param path 디렉토리 정보를 가져올 경로
	 * @return TreeFileModel 리스트
	 */
	
	public List<DirectoryModel> getFileSystem(String path) {
		
		List<DirectoryModel> folders = new ArrayList<DirectoryModel>();	// 폴더 리스트
		List<DirectoryModel> files = new ArrayList<DirectoryModel>();		// 파일 리스트
		
		Vector<ChannelSftp.LsEntry> list = util.getFileList(path);
		
		// 1-1. directory 안의 내용을 탐색한다.

		// 2. directory 객체의 내용이 폴더인지 파일인지 구분한다.

		for(ChannelSftp.LsEntry oListItem : list) {
			matcher = ptrn.matcher(oListItem.toString());
			while(matcher.find()) {
			
				if(matcher.group(6).equals(".") || matcher.group(6).equals(".."))
					continue;
				
				DirectoryModel tfModel = new DirectoryModel(
						matcher.group(6),		// name
        				Integer.parseInt(matcher.group(4)),	// size
        				matcher.group(5),	// modified date
        				matcher.group(1), 	// permission
        				matcher.group(3),	// user/group
        				path		// path
					);
				if(matcher.group(6).contains(".")) {
					if(matcher.group(6).indexOf(".") != 0) {
						String ext = getExtension(matcher.group(6));
						if(ext != null)
							tfModel.setExt(ext);
					}
				}
				
				tfList.add(tfModel);
				
				if (oListItem.getAttrs().isDir()) {
					folders.add(tfModel);
				} else {
					files.add(tfModel);
				}
			}
        }
		folders.addAll(files);
		return folders;
	}
	
	public List<DirectoryModel> getFileModel() {
		return tfList;
	}
	
	private boolean checkConnectionInfo(String param) throws Exception{
		boolean valid = true;
		
		if(param == null || param.isEmpty()) {
			throw new InvalidServerInformationException(String.valueOf(param));
		}
		
		return valid;
	}
	
	public List<File> getFileDirectory(String path) {
		
		List<File> subDirectory = new ArrayList<File>();

		File[] fileList =  new File(path + "/").listFiles();
		
		// 하위 디렉토리 
        for (File info : fileList) {
            subDirectory.add(info);
        }
		return subDirectory;
	}
	
	public void utilfunction(String selectAction, FileTransferModel fileTransferModel) {
		try {
			if(checkBlank(fileTransferModel.getRemotePath(), fileTransferModel.getLocalPath())) {
				
				switch (selectAction) {
				case "upload":
					File uploadFile = new File(fileTransferModel.getLocalPath() + "/" + fileTransferModel.getLocalFileName());
					util.upload(fileTransferModel.getRemotePath(), uploadFile);
					System.out.println("=> File uploading success the " + fileTransferModel.getLocalFileName());
					break;

				case "download":
					util.download(fileTransferModel.getRemotePath(), fileTransferModel.getRemoteFileName(), fileTransferModel.getLocalPath());
					System.out.println("=> File downloading success the " + fileTransferModel.getRemoteFileName());
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getExtension(String fileName) {
		int pos = fileName.lastIndexOf( "." );
		String ext = fileName.substring( pos + 1 );
		return ext;
	}
	
	public boolean checkBlank(String remotePath, String localPath)  throws Exception {
		if(StringUtils.isBlank(remotePath) || StringUtils.isBlank(localPath)) {
			throw new InvalidUtilInformationException(String.valueOf("serverPath : " + remotePath + " filePath : " + localPath));
		}
		return true;
	}
	
}
