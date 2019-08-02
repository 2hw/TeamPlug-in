package org.inbus.teamfiletransferclient.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class FileModel {

	
	private String fileName;
	private String size;
	private String lastEdit;
	private PropertyChangeSupport propertyChangeSupport= new PropertyChangeSupport(this);
	
	public FileModel() {
	}

	public FileModel(String fileName, String size, String lastEdit) {
		super();
		this.fileName = fileName;
		this.size = size;
		this.lastEdit = lastEdit;
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
