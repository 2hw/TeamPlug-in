package org.inbus.teamfiletransferclient.model;

/**
* File Transfer Model
* 접속한 서버의 파일 업로드 및 다운로드의 정보를 담는 Model
* 
* @author lhw
* @version 1.0
* @since 2019.08.06
*/

public class FileTransfer {
	private String remotePath;		//업로드할 곳의 경로
	private String remoteFileName;	//다운받을 파일명
	private String localPath;		//다운받을 곳의 경로
	private String localFileName;	//업로드할 파일명
	private String commonActionFlag;//공통액션 테이블구분(local | remote)
	private String dirName;	//서버 폴더명 (create or delete)
	
	public String getRemotePath() {
		return remotePath;
	}
	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}
	public String getRemoteFileName() {
		return remoteFileName;
	}
	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public String getLocalFileName() {
		return localFileName;
	}
	public void setLocalFileName(String localFileName) {
		this.localFileName = localFileName;
	}
	public String getCommonActionFlag() {
		return commonActionFlag;
	}
	public void setCommonActionFlag(String commonActionFlag) {
		this.commonActionFlag = commonActionFlag;
	}
	public String getDirName() {
		return dirName;
	}
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	
}
