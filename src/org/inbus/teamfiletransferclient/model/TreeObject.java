package org.inbus.teamfiletransferclient.model;

import org.eclipse.core.runtime.IAdaptable;

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