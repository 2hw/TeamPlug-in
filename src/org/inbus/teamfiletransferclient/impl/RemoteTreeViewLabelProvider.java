package org.inbus.teamfiletransferclient.impl;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.inbus.teamfiletransferclient.model.TreeParent;


public class RemoteTreeViewLabelProvider extends LabelProvider {
	
	private IWorkbench workbench;
	
	public RemoteTreeViewLabelProvider(IWorkbench workbench) {
		this.workbench = workbench;
	}

	public String getText(Object obj) {
		if (obj.toString().length() == 3)
			return ((TreeParent) obj).getName();
		else 
			return ((TreeParent) obj).getName().split("\\\\")[obj.toString().split("\\\\").length - 1];
	}
	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		if (obj instanceof TreeParent)
		   imageKey = ISharedImages.IMG_OBJ_FOLDER;
		return workbench.getSharedImages().getImage(imageKey);
	}
}
