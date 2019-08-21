package org.inbus.teamfiletransferclient.model;

import org.eclipse.core.runtime.IAdaptable;

/**
* TreeObject Model
* Tree에 넣는 Model
* 
* @author lhw
* @version 1.0
* @since 2019.08.06
*/

public class TreeObject implements IAdaptable {
	private String name;
	private TreeParent parent;
	private String path;
	
	public TreeObject(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TreeParent getParent() {
		return parent;
	}

	public void setParent(TreeParent parent) {
		this.parent = parent;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public <T> T getAdapter(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}