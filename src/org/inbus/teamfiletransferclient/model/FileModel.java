package org.inbus.teamfiletransferclient.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class FileModel {
	
	public FileModel parent;
	public List<FileModel> child = new ArrayList<>();
	public int counter;
	
	private String fileName;
	private String size;
	private String lastEdit;
	private PropertyChangeSupport propertyChangeSupport= new PropertyChangeSupport(this);
	
	public FileModel() {
	}

	public FileModel(int counter, FileModel parent) {
		this.parent = parent;
		this.counter = counter;
	}
	
	public FileModel(String fileName, String size, String lastEdit) {
		super();
		this.fileName = fileName;
		this.size = size;
		this.lastEdit = lastEdit;
	}
	
	private FileModel createModel(int parentCount) {
		FileModel root = new FileModel(0,null);
		root.counter = 0;
		
		FileModel tmp;
		for(int i=0; i<parentCount; i++) {
			tmp = new FileModel(i, root);
			root.child.add(tmp);
			for(int j=1; j<i; j++) {
				tmp.child.add(new FileModel(j,tmp));
			}
		}
		return root;
	}
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getLastEdit() {
		return lastEdit;
	}

	public void setLastEdit(String lastEdit) {
		this.lastEdit = lastEdit;
	}
	
}
