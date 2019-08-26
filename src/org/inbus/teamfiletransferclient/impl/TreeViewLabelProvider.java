package org.inbus.teamfiletransferclient.impl;

import java.io.File;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.inbus.teamfiletransferclient.model.TreeParent;

/**
 * Tree에 표시될 노드를 제어하는 클래스
 * 화면에 보여지게 되는 노드 명칭과 image를 제어한다.
 *
 * @author lhw
 * @since 2019.08.06
 */

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
		Display display = Display.getCurrent();
		if (element instanceof File) {
			File file = (File) element;
			return IconImageUtil.getImage(display, file);
		}else if (element instanceof TreeParent)
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

}
