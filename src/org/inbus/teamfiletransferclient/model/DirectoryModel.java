package org.inbus.teamfiletransferclient.model;

/**
* Remote Directory Model
* 접속한 서버의 디렉토리를 담는 Model (Folder or File)
* 
* @author lhw
* @version 1.0
* @since 2019.08.06
*/

public class DirectoryModel {
	
	private String name;			//이름 
	private int size;				//크기 
	private boolean isFolder;		//폴더 : true (구분용도)
	private String modified_date;	//최종 수정 시간
	private String permission;		//권한
	private String user_group;		//유저그룹
	private String path;			//경로
	private String ext;				//확장자
	
	public static String[] COLUMN_HEADER = new String[] {"파일명", "크기", "최종수정" ,"권한", "소유자/그룹"};
	
	public DirectoryModel(String name, int size, String modified_date, String permission,
			String user_group, String path) {
		super();
		this.name = name;
		this.size = size;

		if (permission.charAt(0) == 'd')
			this.isFolder = true;
		else
			this.isFolder = false;
		
		this.modified_date = modified_date;
		this.permission = permission;
		this.user_group = user_group;
		this.path = path;
	}
	
	public DirectoryModel(String name, int size, String modified_date, String path) {
		super();
		this.name = name;
		this.size = size;
		this.modified_date = modified_date;
		this.path = path;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public boolean isFolder() {
		return isFolder;
	}
	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getUser_group() {
		return user_group;
	}
	public void setUser_group(String user_group) {
		this.user_group = user_group;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	
}
