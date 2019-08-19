package org.inbus.teamfiletransferclient.model;

public class FileTransferModel {
	private String remotePath;
	private String remoteFileName;
	private String localPath;
	private String localFileName;
	
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
	
}
