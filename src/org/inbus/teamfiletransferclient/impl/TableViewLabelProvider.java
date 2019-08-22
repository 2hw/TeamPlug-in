package org.inbus.teamfiletransferclient.impl;

import java.io.File;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.inbus.teamfiletransferclient.model.DirectoryModel;

/**
 * Table에 표시될 데이터를 제어하는 클래스
 * 화면에 보여지게 되는 내용을 getColumnText에서 리턴하여 준다.
 *
 * @author lhw
 * @since 2019.08.06
 */

public class TableViewLabelProvider implements ITableLabelProvider{

	private IWorkbench workbench;
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd E요일 a HH:mm:ss"); // 날짜 포맷을 지정
	
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
		String imageKey ="";
		switch (idx) {
		case 0:
			Display display = Display.getCurrent();
			
			if(element instanceof File) {
				
				File file = (File) element;
				return IconImageUtil.getImage(display, file);
				
			}else if(element instanceof DirectoryModel) {
				
				DirectoryModel remoteFile = (DirectoryModel) element;
				
				if(remoteFile.isFolder()) {
					imageKey = ISharedImages.IMG_OBJ_FOLDER;
				}else {
					imageKey = ISharedImages.IMG_OBJ_FILE;
					if(!StringUtils.isBlank(remoteFile.getExt())) {
						String fileEnding = remoteFile.getExt();
						ImageData iconData = Program.findProgram(fileEnding).getImageData();
						Image icon = new Image(display, iconData);
						return icon;
					}
				}
			}
		}
		return workbench.getSharedImages().getImage(imageKey);
	}

	@Override
	public String getColumnText(Object element, int idx) {
		
		if(element instanceof File) {
			
			File file = (File) element;
			
			switch(idx) {
			case 0:
				return file.getName();
			case 1:
				return String.valueOf(file.length()); 
			case 2:
				return String.valueOf(sf.format(file.lastModified()));
			}
			
		}else if(element instanceof DirectoryModel) {
			
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
		}
		return "";
	}
}
