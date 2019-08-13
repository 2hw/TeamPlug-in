package org.inbus.teamfiletransferclient.model;

public class TreeFileModel {
	
	private String name;
	private int size;
	private boolean isFolder;
	private String modified_date;
	private String permission;
	private String user_group;
	private String path;
	
	public static String[] COLUMN_HEADER = new String[] {"파일명", "크기", "최종수정" ,"권한", "소유자/그룹"};
	
	public TreeFileModel(String name, int size, String modified_date, String permission,
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
	
	public TreeFileModel(String name, int size, String modified_date, String path) {
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
	
}
