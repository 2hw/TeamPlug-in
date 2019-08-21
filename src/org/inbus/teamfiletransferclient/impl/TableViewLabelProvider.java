package org.inbus.teamfiletransferclient.impl;

import java.net.URL;

import javax.swing.text.View;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.dialogs.ViewLabelProvider;
import org.inbus.teamfiletransferclient.model.DirectoryModel;
import org.inbus.teamfiletransferclient.views.FileTransferView;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;


public class TableViewLabelProvider implements ITableLabelProvider{

	private IWorkbench workbench;
	private final ImageDescriptor ZIP = getImageDescriptor("zip.png");
	private ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());
	
	
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
			}else{
				switch (treeFileModel.getExt()) {
				case "zip": case "7z" :
					return resourceManager.createImage(ZIP);
			default:
				imageKey = ISharedImages.IMG_OBJ_FILE;
//				break;
				}
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
	
	private static ImageDescriptor getImageDescriptor(String file) {
	    Bundle bundle = FrameworkUtil.getBundle(FileTransferView.class);
	    URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
	    return ImageDescriptor.createFromURL(url);
	}
	
}
