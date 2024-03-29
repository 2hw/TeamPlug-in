package org.inbus.teamfiletransferclient.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.inbus.teamfiletransferclient.model.TreeObject;
import org.inbus.teamfiletransferclient.model.TreeParent;

/**
 * Tree에 보여질 노드를 리턴하는 클래스
 * 부모노드와 자식노드를 리턴한다.
 *
 * @author lhw
 * @since 2019.08.06
 */

public class TreeViewContentProvider implements ITreeContentProvider {

	public Object[] getElements(Object parent) {
		if(parent instanceof File) {
			return (File[]) parent;
		}else if(parent instanceof TreeParent) {
			return getChildren(parent);
		}
		return (Object[]) parent;
	}
	
	public Object getParent(Object child) {
		if (child instanceof TreeObject) {
			return ((TreeObject)child).getParent();
		} else if(child instanceof File) {
			File file = (File) child;
	        return file.getParentFile();
		}
		return null;
	}
	
	public Object [] getChildren(Object parent) {
		if (parent instanceof TreeParent) {
			return ((TreeParent)parent).getChildren();
        }else if (parent instanceof File) {
            List<File> childs = new ArrayList<File>();
            
            File[] files = ((File)parent).listFiles();
               
               if(files != null) {
                   for(File fItem : files) {
                       if(fItem.isDirectory())
                           childs.add(fItem);
                   }
               }
               return childs.toArray() == null ? new Object[0] : childs.toArray();
       }
       return new Object[0];
   }
	
	public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParent) {
			return ((TreeParent)parent).hasChildren();
		}else if(parent instanceof File) {
			 File file = (File) parent;
	          if (file.isDirectory()) {
	              return true;
	          }
		}
		
		return false;
	}
}
