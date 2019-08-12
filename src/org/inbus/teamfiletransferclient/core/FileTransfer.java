package org.inbus.teamfiletransferclient.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.inbus.teamfiletransferclient.exceptions.InvalidServerInformationException;
import org.inbus.teamfiletransferclient.impl.SFTPUtil;
import org.inbus.teamfiletransferclient.model.ConnectionInfoModel;
import org.inbus.teamfiletransferclient.model.TreeFileModel;
import org.inbus.teamfiletransferclient.model.TreeParent;

import com.jcraft.jsch.ChannelSftp;

public class FileTransfer {
	
	private ConnectionInfoModel connectionInfoModel = new ConnectionInfoModel();
	private SFTPUtil util = new SFTPUtil();
	private List<TreeFileModel> tfList = new ArrayList<>();
	private String remoteHome = "/home/testuser";
	
	private String pattern = "(.+)\\s+(\\d+)\\s+(\\S+\\s+\\S+)\\s+(\\d+)\\s+(.+\\s+\\d+\\s+[\\d:]+)\\s+(.*)";
	private Pattern ptrn = Pattern.compile(pattern);
	Matcher matcher;
	
	
	public TreeParent remoteConnect(String host, String userName, String password, String port) {
		
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
		
		return getTreeDirectory(remoteHome);
		
	}
	
	
	public TreeParent getTreeDirectory(String path) {

//		System.out.println("=> Connected to 3322");
        
		String name = path.split("/")[path.split("/").length - 1];
		
		// 1. 파일 객체 생성 (path 정보를 가지는 파일 만듬)
		// 현재 경로를 파일 객체로 생성
		
		TreeParent retTP = new TreeParent(name, path);
		
		List<TreeFileModel> TFMList = getFileSystem(path);
		
		for(TreeFileModel tfm : TFMList) {
			
			if(tfm.isFolder())
				retTP.addChild(getTreeDirectory(path + "/" + tfm.getName()));
		}
//        util.disconnection();
        
		return retTP;
        
	}
	
	/**
	 * 경로에 대한 디렉토리 정보 가져오기
	 * 
	 * @param path 디렉토리 정보를 가져올 경로
	 * @return {@linkplain DirectoryVO} 리스트
	 */
	
	public List<TreeFileModel> getFileSystem(String path) {
		
		List<TreeFileModel> folders = new ArrayList<TreeFileModel>();	// 폴더 리스트
		List<TreeFileModel> files = new ArrayList<TreeFileModel>();		// 파일 리스트
		
		Vector<ChannelSftp.LsEntry> list = util.getFileList(path);
		
		// 1-1. directory 안의 내용을 탐색한다.

		// 2. directory 객체의 내용이 폴더인지 파일인지 구분한다.

		for(ChannelSftp.LsEntry oListItem : list) {
			
			matcher = ptrn.matcher(oListItem.toString());
			while(matcher.find()) {
			
				if(matcher.group(6).equals(".") || matcher.group(6).equals(".."))
					break;
				
				TreeFileModel tfModel = new TreeFileModel(
						matcher.group(6),		// name
        				Integer.parseInt(matcher.group(4)),	// size
        				matcher.group(5),	// modified date
        				matcher.group(1), 	// permission
        				matcher.group(3),	// user/group
        				path		// path
					);
				
				tfList.add(tfModel);
				
				if (!oListItem.getAttrs().isDir()) {
					files.add(tfModel);
					
//					System.out.println(matcher.group(6) + "	파일입니다.");
	
				} else {
					folders.add(tfModel);
					
//						System.out.println(oListItem.toString() + "	디렉토리입니다. " + "폴더명 : " + matcher.group(6));
//						System.out.print("폴더명 : " + matcher.group(6));
//						System.out.println("	재귀함수 파라미터 : " + path + "/" + matcher.group(6));
				}
				
			}
        }
		folders.addAll(files);
		return folders;
	}
	
	public List<TreeFileModel> getFileModel() {
		return tfList;
	}
	
	private boolean checkConnectionInfo(String param) throws Exception{
		boolean valid = true;
		
		if(param == null || param.isEmpty()) {
			throw new InvalidServerInformationException(String.valueOf(param));
		}
		
		return valid;
	}
	
}
