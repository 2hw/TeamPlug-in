package org.inbus.teamfiletransferclient.impl;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.inbus.teamfiletransferclient.model.TreeObject;
import org.inbus.teamfiletransferclient.model.TreeParent;


public class RomoteTreeViewContentProvider implements ITreeContentProvider {

	public Object[] getElements(Object parent) {
		return getChildren(parent);
	}
	public Object getParent(Object child) {
		if (child instanceof TreeObject) {
			return ((TreeObject)child).getParent();
		}
		return null;
	}
	public Object [] getChildren(Object parent) {
		if (parent instanceof TreeParent) {
			return ((TreeParent)parent).getChildren();
		}
		return new Object[0];
	}
	public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParent)
			return ((TreeParent)parent).hasChildren();
		return false;
	}
}
