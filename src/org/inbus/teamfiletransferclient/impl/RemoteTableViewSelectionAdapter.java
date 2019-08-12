package org.inbus.teamfiletransferclient.impl;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TreeItem;

public class RemoteTableViewSelectionAdapter extends SelectionAdapter{

	@Override
	public void widgetSelected(SelectionEvent e) {
		TreeItem item = (TreeItem) e.item;
		if(item.getItemCount() > 0) {
			item.getText();
		}
	}
	
}
