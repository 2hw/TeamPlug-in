package org.inbus.teamfiletransferclient.impl;

import java.io.File;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.inbus.teamfiletransferclient.model.TreeParent;

public class TreeViewLabelProvider extends LabelProvider {
	private ResourceManager resourceManager;

	private IWorkbench workbench;
	
	public TreeViewLabelProvider(IWorkbench workbench) {
		this.workbench = workbench;
	}
	
	@Override
	public String getText(Object element) {
		if(element instanceof File) {
			if (element.toString().length() == 3)
				return element.toString();
			else 
				return element.toString().split("\\\\")[element.toString().split("\\\\").length - 1];
		} else if(element instanceof TreeParent) {
			if (element.toString().length() == 3)
				return ((TreeParent) element).getName();
			else 
				return ((TreeParent) element).getName().split("\\\\")[element.toString().split("\\\\").length - 1];
		}
		return (String) element;
	}
	@Override
	public Image getImage(Object element) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		if (element instanceof File)
		   imageKey = ISharedImages.IMG_OBJ_FOLDER;
		if (element instanceof TreeParent)
			   imageKey = ISharedImages.IMG_OBJ_FOLDER;
		return workbench.getSharedImages().getImage(imageKey);
	}

	@Override
	public void dispose() {
		// garbage collection system resources
		if (resourceManager != null) {
			resourceManager.dispose();
			resourceManager = null;
		}
	}

	protected ResourceManager getResourceManager() {
		if (resourceManager == null) {
			resourceManager = new LocalResourceManager(JFaceResources.getResources());
		}
		return resourceManager;
	}

	private String getFileName(File file) {
		String name = file.getName();
		return name.isEmpty() ? file.getPath() : name;
	}

}
