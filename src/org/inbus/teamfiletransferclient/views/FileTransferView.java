package org.inbus.teamfiletransferclient.views;


import java.io.File;
import java.net.URL;
import java.text.DateFormat;

import javax.inject.Inject;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;
import org.inbus.teamfiletransferclient.core.FileTransfer;
import org.inbus.teamfiletransferclient.impl.FileModifiedLabelProvider;
import org.inbus.teamfiletransferclient.impl.FileSizeLabelProvider;
import org.inbus.teamfiletransferclient.impl.ModelProvider;
import org.inbus.teamfiletransferclient.impl.ViewContentProvider;
import org.inbus.teamfiletransferclient.impl.ViewLabelProvider;
import org.inbus.teamfiletransferclient.model.FileModel;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class FileTransferView extends ViewPart {
	public FileTransferView() {
	}

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "naspluginproject.views.SampleView";

	private TreeViewer TRViewer;
	private TableViewer TBViewer;
	
	private static final DateFormat dateFormat = DateFormat.getDateInstance();
	
	@Inject IWorkbench workbench;
	private Text txt_host;
	private Text txt_userName;
	private Text txt_pwd;
	private Text txt_port;
	private Table table_local;
	private Table table_remote;
	private Table table_file;
	 
	private FileTransfer ft = new FileTransfer();
	
	private boolean connectResult;
	

	@Override
	public void createPartControl(Composite parent) {
		
		Composite composite_main = new Composite(parent, SWT.BORDER);
		composite_main.setLayout(new GridLayout(2, false));
		
		Group group_connect = new Group(composite_main, SWT.NONE);
		GridData gd_group_connect = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_group_connect.widthHint = 367;
		group_connect.setLayoutData(gd_group_connect);
		group_connect.setText("연결 정보");
		group_connect.setLayout(new GridLayout(5, false));
		
		Label label_host = new Label(group_connect, SWT.NONE);
		label_host.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_host.setText("호스트");
		
		txt_host = new Text(group_connect, SWT.BORDER);
		txt_host.setText("210.103.215.160");
		txt_host.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label label_pwd = new Label(group_connect, SWT.NONE);
		label_pwd.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_pwd.setText("비밀번호");
		label_pwd.setBounds(0, 0, 60, 20);
		
		txt_pwd = new Text(group_connect, SWT.BORDER | SWT.PASSWORD);
		txt_pwd.setText("xptmxmdbwj1q2w3e4r!@#$");
		txt_pwd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btn_search = new Button(group_connect, SWT.NONE);
		
		btn_search.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));
		btn_search.setText("조회");
		
		Label label_userName = new Label(group_connect, SWT.NONE);
		label_userName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_userName.setAlignment(SWT.CENTER);
		label_userName.setSize(71, 20);
		label_userName.setText("사용자명");
		
		txt_userName = new Text(group_connect, SWT.BORDER);
		txt_userName.setText("testuser");
		txt_userName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label label_port = new Label(group_connect, SWT.NONE);
		label_port.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_port.setText("포트");
		
		txt_port = new Text(group_connect, SWT.BORDER);
		txt_port.setText("3322");
		txt_port.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(group_connect, SWT.NONE);
		
		Label label_connectResult = new Label(group_connect, SWT.NONE);
		label_connectResult.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 2));
		new Label(group_connect, SWT.NONE);
		new Label(group_connect, SWT.NONE);
		new Label(group_connect, SWT.NONE);
		
		Group group_local = new Group(composite_main, SWT.NONE);
		group_local.setText("로컬 사이트");
		
		Combo combo_local = new Combo(group_local, SWT.NONE);
		combo_local.setBounds(0, 30, 530, 30);
		
		//TreeView
		TRViewer = new TreeViewer(group_local, SWT.BORDER);
		Tree tree_local = TRViewer.getTree();
		tree_local.setBounds(0, 64, 530, 205);
		
		//TreeView Provider
        TRViewer.setContentProvider(new ViewContentProvider());
        TRViewer.getTree().setHeaderVisible(true);
        
        TreeViewerColumn mainColumn = new TreeViewerColumn(TRViewer, SWT.NONE);
        
        TreeViewerColumn modifiedColumn = new TreeViewerColumn(TRViewer, SWT.NONE);
        
        TreeViewerColumn fileSizeColumn = new TreeViewerColumn(TRViewer, SWT.NONE);
        mainColumn.getColumn().setText("Name");
        mainColumn.getColumn().setWidth(180);
        mainColumn.setLabelProvider(
                new DelegatingStyledCellLabelProvider(
                        new ViewLabelProvider(createImageDescriptor())
                        ));
        modifiedColumn.getColumn().setText("Last Modified");
        modifiedColumn.getColumn().setWidth(100);
        modifiedColumn.getColumn().setAlignment(SWT.RIGHT);
        modifiedColumn
                .setLabelProvider(new DelegatingStyledCellLabelProvider(
                        new FileModifiedLabelProvider(dateFormat)));
        fileSizeColumn.getColumn().setText("Size");
        fileSizeColumn.getColumn().setWidth(89);
        fileSizeColumn.getColumn().setAlignment(SWT.RIGHT);
        fileSizeColumn.setLabelProvider(new DelegatingStyledCellLabelProvider(
	    new FileSizeLabelProvider()));
        
        //TreeViewer 데이터 설정
	    TRViewer.setInput(File.listRoots());
	    TRViewer.refresh();
		
	    //TableViewer
	    TBViewer = new TableViewer(group_local, SWT.BORDER | SWT.FULL_SELECTION);
	    createColumns(group_local, TBViewer);
		table_local = TBViewer.getTable();
		table_local.setHeaderVisible(true);
		table_local.setBounds(0, 280, 530, 260);
		
		//TableView Provider
		TBViewer.setContentProvider(new ArrayContentProvider());
        TBViewer.setInput(ModelProvider.INSTANCE.getFileModel());
		
		Label label_localResult = new Label(group_local, SWT.NONE);
		label_localResult.setBounds(0, 550, 530, 30);
		
		Group group_remote = new Group(composite_main, SWT.NONE);
		group_remote.setText("리모트 사이트");
		
		Combo combo_remote = new Combo(group_remote, SWT.NONE);
		combo_remote.setBounds(0, 30, 530, 30);
		
		TreeViewer treeViewer_2 = new TreeViewer(group_remote, SWT.BORDER);
		Tree tree_remote = treeViewer_2.getTree();
		tree_remote.setLocation(0, 64);
		tree_remote.setSize(530, 205);
		
		TableViewer tableViewer_1 = new TableViewer(group_remote, SWT.BORDER | SWT.FULL_SELECTION);
		table_remote = tableViewer_1.getTable();
		table_remote.setBounds(0, 280, 530, 260);
		
		Label label_remoteResult = new Label(group_remote, SWT.NONE);
		label_remoteResult.setBounds(0, 550, 530, 30);
		
		Group group_fileTable = new Group(composite_main, SWT.NONE);
		group_fileTable.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		
		TableViewer tableViewer_2 = new TableViewer(group_fileTable, SWT.BORDER | SWT.FULL_SELECTION);
		table_file = tableViewer_2.getTable();
		table_file.setBounds(0, 0, 1070, 90);

		//사용자 정의 함수
		btn_search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					connectResult = ft.remoteConnect(txt_host.getText(), txt_userName.getText(), txt_pwd.getText(), txt_port.getText());
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				
				if(connectResult == false) {
					System.out.println("Connecting fail~~~~~!!!");
				}else {
					System.out.println("Connecting sucess~~~~~!!!");
				}
			}
		});
	}
		private ImageDescriptor createImageDescriptor() {
			Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
			URL url = FileLocator.find(bundle, new Path("icons/folder.png"),null);
			
			return ImageDescriptor.createFromURL(url);
		}
		
		private void createColumns(final Composite parent, final TableViewer viewer) {
	        String[] titles = { "File name", "Size", "Last Modified" };
	        int[] bounds = { 100, 100, 100};

	        // First column is for the first name
	        TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	        col.setLabelProvider(new ColumnLabelProvider() {
	            @Override
	            public String getText(Object element) {
	                FileModel p = (FileModel) element;
	                return p.getFileName();
	            }
	        });

	        // Second column is for the last name
	        col = createTableViewerColumn(titles[1], bounds[1], 1);
	        col.setLabelProvider(new ColumnLabelProvider() {
	            @Override
	            public String getText(Object element) {
	                FileModel p = (FileModel) element;
	                return p.getSize();
	            }
	        });

	        // now the gender
	        col = createTableViewerColumn(titles[2], bounds[2], 2);
	        col.setLabelProvider(new ColumnLabelProvider() {
	            @Override
	            public String getText(Object element) {
	                FileModel p = (FileModel) element;
	                return p.getLastEdit();
	            }
	        });

			/*
			 * // now the status married col = createTableViewerColumn(titles[3], bounds[3],
			 * 3); col.setLabelProvider(new ColumnLabelProvider() {
			 * 
			 * @Override public String getText(Object element) { return null; }
			 * 
			 * @Override public Image getImage(Object element) { if (((Person)
			 * element).isMarried()) { return CHECKED; } else { return UNCHECKED; } } });
			 */

	    }
		
		private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
	        final TableViewerColumn viewerColumn = new TableViewerColumn(TBViewer,
	                SWT.NONE);
	        final TableColumn column = viewerColumn.getColumn();
	        column.setText(title);
	        column.setWidth(bound);
	        column.setResizable(true);
	        column.setMoveable(true);
	        return viewerColumn;

	    }

	@Override
	public void setFocus() {
		
	}
}
