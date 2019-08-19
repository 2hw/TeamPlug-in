package org.inbus.teamfiletransferclient.impl;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.inbus.teamfiletransferclient.model.DirectoryModel;


public class TableViewLabelProvider implements ITableLabelProvider{

	private IWorkbench workbench;
	
	public TableViewLabelProvider(IWorkbench workbench) {
		this.workbench = workbench;
	}
	
	@Override
	public void addListener(ILabelProviderListener arg0) {}

	@Override
	public void dispose() {}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {}

	@Override
	public Image getColumnImage(Object element, int idx) {
		
		DirectoryModel treeFileModel = (DirectoryModel) element;
		String imageKey ="";
		
		switch (idx) {
		case 0:
			if(treeFileModel.isFolder()) {
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			}else {
				imageKey = ISharedImages.IMG_OBJ_FILE;
			}
		}
		
		return workbench.getSharedImages().getImage(imageKey);
	}

	@Override
	public String getColumnText(Object element, int idx) {
		DirectoryModel treeFileModel = (DirectoryModel) element;
		switch (idx) {
		case 0:
				return treeFileModel.getName();
		case 1:
				return String.valueOf(treeFileModel.getSize());
		case 2:
				return treeFileModel.getModified_date();
		case 3:
				return treeFileModel.getPermission();
		case 4:
				return treeFileModel.getUser_group();
		}
		return "";
	}
	
	
}
