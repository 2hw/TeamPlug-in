package org.inbus.teamfiletransferclient.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.inbus.teamfiletransferclient.exceptions.InvalidServerInformationException;
import org.inbus.teamfiletransferclient.impl.SFTPUtil;
import org.inbus.teamfiletransferclient.model.ConnectionInfo;
import org.inbus.teamfiletransferclient.model.Directory;
import org.inbus.teamfiletransferclient.model.FileTransfer;
import org.inbus.teamfiletransferclient.model.TreeParent;

import com.jcraft.jsch.ChannelSftp;

/**
 * 비즈니스 로직을 제어하는 클래스
 * 화면부터 전반적인 기능을 제어하는 Controller
 *
 * @author lhw
 * @since 2019.08.06
 */

public class FileTransferCore {
	
	private ConnectionInfo connectionInfoModel = new ConnectionInfo();
	private SFTPUtil util = new SFTPUtil();
	private List<Directory> treeFileList;
	private String remoteHome = "/test";
	private boolean rootFlag = true;
	
	private String pattern = "(.+)\\s+(\\d+)\\s+(\\S+\\s+\\S+)\\s+(\\d+)\\s+(.+\\s+\\d+\\s+[\\d:]+)\\s+(.*)";
	private Pattern ptrn = Pattern.compile(pattern);
	Matcher matcher;
	
	/**
	 * logger - java.util.logging.Logger
	 */
//	private final Logger log = LoggerFactory.getLogger(FileTransfer.class);
	
    /**
     * 서버에 연결 후 디렉토리 반환
     *
     * @param host 서버 주소
     * @param userName 접속에 사용될 아이디
     * @param password 비밀번호
     * @param port  포트번호
     * @return 서버 home 경로의 디렉토리 목록을 Tree구조로 반환
     * @exception 
     */
	public TreeParent remoteConnect(String host, String userName, String password, String port) {
		
		initTreeFileList();
		
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
		
		return rtTP;
	}
	
    /**
     * 경로에 해당하는 폴더를 찾아 Tree 구조로 반환 (재귀함수)
     * 해당 경로에 폴더가 존재할시 자식노드로 추가하고 하위 폴더가 없을때까지 탐색
     *
     * @param 탐색할 경로
     * @return 경로에 하위 폴더들을 Tree구조로 TreeParent 반환
     * @exception 
     */
	public TreeParent getTreeDirectory(String path) {
		String name = "";
//		if(rootFlag) {
//			name = path;
//			rootFlag = false;
//		} else {
			name = path.split("/")[path.split("/").length - 1];
//		}
		
		// 1. 파일 객체 생성 (path 정보를 가지는 파일 만듬)
		// 현재 경로를 파일 객체로 생성
		
		TreeParent retTP = new TreeParent(name, path);
		
		List<Directory> TFMList = getFileSystem(path);
		
		for(Directory tfm : TFMList) {
			
			if(tfm.isFolder())
				retTP.addChild(getTreeDirectory(path + "/" + tfm.getName()));
		}
        
		return retTP;
        
	}
	
	/**
	 * Remote경로에 대한 디렉토리 정보 가져오기
	 * 
	 * @param path 디렉토리 정보를 가져올 경로
	 * @return TreeFileModel 리스트 반환
	 */
	
	public List<Directory> getFileSystem(String path) {
		
		
		List<Directory> folders = new ArrayList<Directory>();		// 폴더 리스트
		List<Directory> files = new ArrayList<Directory>();		// 파일 리스트
		
		Vector<ChannelSftp.LsEntry> list = util.getFileList(path);
		
		// 1-1. directory 안의 내용을 탐색한다.

		// 2. directory 객체의 내용이 폴더인지 파일인지 구분한다.

		for(ChannelSftp.LsEntry oListItem : list) {
			matcher = ptrn.matcher(oListItem.toString());
			while(matcher.find()) {
			
				if(matcher.group(6).equals(".") || matcher.group(6).equals(".."))
					continue;
				
				Directory tfModel = new Directory(
						matcher.group(6),					// name
        				Integer.parseInt(matcher.group(4)),	// size
        				matcher.group(5),					// modified date
        				matcher.group(1), 					// permission
        				matcher.group(3),					// user/group
        				path								// path
					);
				//확장자가 존재할 시
				if(matcher.group(6).contains(".")) {
					if(matcher.group(6).indexOf(".") != 0) {
						String ext = getExtension(matcher.group(6));
						if(ext != null)
							tfModel.setExt(ext);
					}
				}
				
				treeFileList.add(tfModel);
				
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
	
    /**
     * 접속했을때의 경로의 폴더 및 파일 리스트를 리턴한다.
     * 
     * @return 경로안에 디렉토리 구조를 DirectoryModel 리스트 반환
     * @exception 
     */
	public List<Directory> getFileModel() {
		return treeFileList;
	}
	
	private boolean checkConnectionInfo(String param) throws Exception{
		boolean valid = true;
		
		if(param == null || param.isEmpty()) {
			throw new InvalidServerInformationException(String.valueOf(param));
		}
		
		return valid;
	}
	
    /**
     * local 경로의 폴더 및 파일 리스트를 리턴한다.
     * 
     * @param path
     * @return 경로안에 디렉토리 구조를 File 리스트로 반환
     * @exception 
     */
	public List<File> getFileDirectory(String path) {
		
		List<File> subDirectory = new ArrayList<File>();

		File[] fileList =  new File(path + "/").listFiles();
		
		// 하위 디렉토리 
        for (File info : fileList) {
            subDirectory.add(info);
        }
		return subDirectory;
	}
	
    /**
     * 마우스 우클릭 이벤트 로직
     * 
     * @param selectAction 이벤트 구분(업로드, 다운로드, 새폴더 만들기)
     * @param fileTransferModel	(local & remote의 경로,파일명을 담는 Model)
     * @return void
     * @exception 
     */
	public void utilfunction(String selectAction, FileTransfer fileTransferModel) {
		try {
//			if(checkBlank(fileTransferModel.getRemotePath(), fileTransferModel.getLocalPath())) {
				
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
					
				case "newDirectory":
					//commonAction(local or remote)
					//create new directory
					if(fileTransferModel.getCommonActionFlag().equals("local")) {
						
						File file = new File(fileTransferModel.getLocalPath()+ "/" +fileTransferModel.getDirName());
						file.mkdir();
					}
					else {
						util.createDir(fileTransferModel.getDirName(), fileTransferModel.getRemotePath());
					}
					System.out.println("=> Create directory success the " + fileTransferModel.getDirName());
					break;
				case "delDirectory":
					//commonAction(local or remote)
					//delete directory
					if(fileTransferModel.getCommonActionFlag().equals("local")) {
						
						File file = new File(fileTransferModel.getLocalPath()+ "/" +fileTransferModel.getDirName());
						file.delete();
					}
					else {
						util.deleteDir(fileTransferModel.getDirName(), fileTransferModel.getRemotePath());
					}
					System.out.println("=> Delete directory success the " + fileTransferModel.getDirName());
					break;
				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * 확장자를 찾아 리턴한다.
     * 
     * @param 파일명 (ex: test.txt)
     * @return 확장자만 문자열로 반환
     * @exception 
     */
	private String getExtension(String fileName) {
		int pos = fileName.lastIndexOf( "." );
		String ext = fileName.substring( pos + 1 );
		return ext;
	}
	
    /**
     * local & remote 경로가 있는지 확인
     * 
     * @param remotePath 서버쪽 경로
     * @param localPath	local경로
     * @return 경로가 잘못되어 있을 경우 false 반환 (default : true)
     * @exception 
     */
	public boolean checkBlank(String remotePath, String localPath) {
		if(StringUtils.isBlank(remotePath) || StringUtils.isBlank(localPath)) {
			return false;
		}
		return true;
	}

	public void initTreeFileList() {
		treeFileList = new ArrayList<Directory>();
	}
	
	public String getRemoteHome() {
		return remoteHome;
	}

}
