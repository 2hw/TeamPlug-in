package org.inbus.teamfiletransferclient.impl;

import java.util.ArrayList;
import java.util.List;

import org.inbus.teamfiletransferclient.model.FileModel;

public enum ModelProvider {
	INSTANCE;
	
	private List<FileModel> fileModel;
	
	private ModelProvider() {
		fileModel = new ArrayList<FileModel>();
		
		fileModel.add(new FileModel("파일이름이다..", "오쥐", "날짜인가"));
		
	}
	
	public List<FileModel> getFileModel() {
		return fileModel;
	}

}
