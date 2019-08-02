package org.inbus.teamfiletransferclient.model;

import java.util.ArrayList;
import java.util.List;

public enum ModelProvider {
	INSTANCE;
	
	private List<FileModel> fileModel;
	
	private ModelProvider() {
		fileModel = new ArrayList<FileModel>();
		
		fileModel.add(new FileModel("�����̸��̴�..", "����", "��¥�ΰ�"));
		
	}
	
	public List<FileModel> getFileModel() {
		return fileModel;
	}

}
