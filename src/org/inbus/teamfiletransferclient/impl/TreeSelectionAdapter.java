package org.inbus.teamfiletransferclient.impl;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TreeItem;

public class TreeSelectionAdapter extends SelectionAdapter{

	@Override
	public void widgetSelected(SelectionEvent e) {
		super.widgetSelected(e);
		TreeItem item = (TreeItem) e.item;
		if(item.getItemCount() > 0) {
			
			//set the 
		}
	}
	
}
