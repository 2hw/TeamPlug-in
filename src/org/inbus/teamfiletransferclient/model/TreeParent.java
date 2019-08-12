package org.inbus.teamfiletransferclient.model;

import java.util.ArrayList;

public class TreeParent extends TreeObject {
	
	private ArrayList<Object> children;
	
	public TreeParent(String name, String path) {
		super(name, path);
		children = new ArrayList<Object>();
	}
	public void addChild(TreeObject child) {
		children.add(child);
		child.setParent(this);
	}
	public void removeChild(TreeObject child) {
		children.remove(child);
		child.setParent(null);
	}
	public TreeObject [] getChildren() {
		return (TreeObject [])children.toArray(new TreeObject[children.size()]);
	}
	public boolean hasChildren() {
		return children.size()>0;
	}
}
