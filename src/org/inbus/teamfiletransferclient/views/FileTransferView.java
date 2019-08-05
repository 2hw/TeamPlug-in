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
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;


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
	public static final String ID = "org.inbus.teamfiletransferclient.views.FileTransferView";

	private TreeViewer TRViewer;
	private TableViewer TBViewer;
	

	private static final DateFormat dateFormat = DateFormat.getDateInstance();
	
	@Inject IWorkbench workbench;
	private Text txtHost;
	private Text txtUserName;
	private Text txtPassword;
	private Text txtPort;
	private Text text_4;
	

	private FileTransfer ft = new FileTransfer();
	
	private boolean connectResult = true;
	
	@Override
	public void createPartControl(Composite parent) {
		//grpFTP
		Group grpFTP = new Group(parent, SWT.NONE);
		grpFTP.setLayout(new FormLayout());
		
		//grpConnect
		Group grpConnect = new Group(grpFTP, SWT.NONE);
		grpConnect.setLayoutData(new FormData());
		grpConnect.setLayout(new GridLayout(10, false));
		
		Label lblNewLabel = new Label(grpConnect, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_lblNewLabel.widthHint = -45;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("\uD638\uC2A4\uD2B8(H):");
		
		txtHost = new Text(grpConnect, SWT.BORDER);
		txtHost.setText("210.103.215.160");
		txtHost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(grpConnect, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("\uC0AC\uC6A9\uC790\uBA85(U):");
		
		txtUserName = new Text(grpConnect, SWT.BORDER);
		txtUserName.setText("testuser");
		txtUserName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_2 = new Label(grpConnect, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("\uBE44\uBC00\uBC88\uD638(W):");
		
		txtPassword = new Text(grpConnect, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setText("xptmxmdbwj1q2w3e4r!@#$");
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_3 = new Label(grpConnect, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("\uD3EC\uD2B8(P):");
		
		txtPort = new Text(grpConnect, SWT.BORDER);
		txtPort.setText("3322");
		txtPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(grpConnect, SWT.NONE);
		
		Button btnNewButton = new Button(grpConnect, SWT.NONE);
		btnNewButton.setText("\uC5F0\uACB0");
		
		Group grpConect_Confirm = new Group(grpConnect, SWT.NONE);
		grpConect_Confirm.setLayout(new GridLayout(1, false));
		GridData gd_grpConect_Confirm = new GridData(SWT.FILL, SWT.FILL, true, false, 10, 1);
		gd_grpConect_Confirm.widthHint = 878;
		grpConect_Confirm.setLayoutData(gd_grpConect_Confirm);
		
		text_4 = new Text(grpConect_Confirm, SWT.BORDER);
		GridData gd_text_4 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text_4.heightHint = 48;
		gd_text_4.widthHint = 858;
		text_4.setLayoutData(gd_text_4);
		text_4.setBounds(0, 0, 73, 21);
		
		//grpLocal
		Group grpview = new Group(grpFTP, SWT.NONE);
		grpview.setLayout(new RowLayout(SWT.HORIZONTAL));
		FormData fd_grpview = new FormData();
		fd_grpview.top = new FormAttachment(0, 146);
		fd_grpview.left = new FormAttachment(0);
		fd_grpview.right = new FormAttachment(100, -6);
		grpview.setLayoutData(fd_grpview);
		
		
		//grpConfirm
		Group grpConfirm = new Group(grpFTP, SWT.NONE);
		fd_grpview.bottom = new FormAttachment(grpConfirm, -6);
		
		Group grpView_Local = new Group(grpview, SWT.NONE);
		grpView_Local.setLayoutData(new RowData(SWT.DEFAULT, 291));
        GridLayout gl_grpView_Local = new GridLayout(1, false);
        gl_grpView_Local.marginLeft = 50;
        grpView_Local.setLayout(gl_grpView_Local);
                
        //TreeView 컴포넌트 생성
        TRViewer = new TreeViewer(grpView_Local, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        Tree tree = TRViewer.getTree();
        GridData gd_tree = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
        gd_tree.heightHint = 137;
        tree.setLayoutData(gd_tree);
        
        //TreeView에 표시를 위한 Provider
        TRViewer.setContentProvider(new ViewContentProvider());
        TRViewer.getTree().setHeaderVisible(true);
        
        TreeViewerColumn mainColumn = new TreeViewerColumn(TRViewer, SWT.NONE);
        
        TreeViewerColumn modifiedColumn = new TreeViewerColumn(TRViewer, SWT.NONE);
        
        TreeViewerColumn fileSizeColumn = new TreeViewerColumn(TRViewer, SWT.NONE);
        mainColumn.getColumn().setText("Name");
        mainColumn.getColumn().setWidth(140);
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
	
	    //표시할 리스트 목록 설정
	    TRViewer.setInput(File.listRoots());
	    
	    //현재 선택된 TreeView의 디렉토리
	    
	    TRViewer.refresh();
	
	    
		//TableViewer 컴포넌트 생성
	    TBViewer = new TableViewer(grpView_Local, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
	    createColumns(grpView_Local, TBViewer);
        final Table table = TBViewer.getTable();
        GridData gd_table = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
        gd_table.heightHint = 101;
        table.setLayoutData(gd_table);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        TBViewer.setContentProvider(new ArrayContentProvider());
        
        TBViewer.setInput(ModelProvider.INSTANCE.getFileModel());
        
        
		FormData fd_grpConfirm = new FormData();
		fd_grpConfirm.left = new FormAttachment(0);
		fd_grpConfirm.right = new FormAttachment(100, -6);
		fd_grpConfirm.top = new FormAttachment(0, 482);
		grpConfirm.setLayoutData(fd_grpConfirm);
        
		/*
		 * 사용자 이벤트 정의
		 */
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				
				try {
					connectResult = ft.remoteConnect(txtHost.getText(), txtUserName.getText(), txtPassword.getText(), txtPort.getText());
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
		TRViewer.getControl().setFocus();
		TBViewer.getControl().setFocus();
	}
}
