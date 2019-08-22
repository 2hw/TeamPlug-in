package org.inbus.teamfiletransferclient.views;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;
import org.inbus.teamfiletransferclient.core.FileTransfer;
import org.inbus.teamfiletransferclient.impl.TableViewLabelProvider;
import org.inbus.teamfiletransferclient.impl.TreeViewContentProvider;
import org.inbus.teamfiletransferclient.impl.TreeViewLabelProvider;
import org.inbus.teamfiletransferclient.model.DirectoryModel;
import org.inbus.teamfiletransferclient.model.FileTransferModel;
import org.inbus.teamfiletransferclient.model.TreeParent;


/**
* FTP Plug-in view
* 서버 정보를 이용하여 파일을 다운로드 및 업로드
* 
* @author lhw
* @version 1.0
* @since 2019.08.06
*/

public class FileTransferView extends ViewPart {
	public FileTransferView() {
	}

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "naspluginproject.views.SampleView";

	private TreeViewer localTRViewer;
	private TableViewer localTBViewer;
	private TreeViewer remoteTRViewer;
	private TableViewer remoteTBViewer;
	
	private FileTransfer fileTF = new FileTransfer();
	private DirectoryModel trFileMD;
	private List<DirectoryModel> remot_allDirectoryList;
	private List<File> local_allDirectoryList;
	private String absolutePath = "";
	private FileTransferModel fileTransferModel = new FileTransferModel();
	
	@Inject IWorkbench workbench;
	private Text txt_host;
	private Text txt_userName;
	private Text txt_pwd;
	private Text txt_port;
	private Table table_local;
	private Table table_remote;
	private Table table_file;
	
	private Action localTableAction;
	private Action remoteTableAction;
	 

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
		
		Combo combo_localPath = new Combo(group_local, SWT.NONE);
		combo_localPath.setBounds(0, 30, 530, 30);
		
		//Local TreeViewer
		localTRViewer = new TreeViewer(group_local, SWT.BORDER);
		Tree tree_local = localTRViewer.getTree();
		tree_local.setBounds(0, 64, 530, 205);
		
		//Local TreeViewer Provider
        localTRViewer.setContentProvider(new TreeViewContentProvider());
        localTRViewer.setLabelProvider(new TreeViewLabelProvider(workbench));
        
        //Set data in Local TreeViewer
	    localTRViewer.setInput(File.listRoots());
	    localTRViewer.refresh();
		
	    //Local TableViewer
	    localTBViewer = new TableViewer(group_local, SWT.BORDER | SWT.FULL_SELECTION);
		table_local = localTBViewer.getTable();
		table_local.setHeaderVisible(true);
		table_local.setBounds(0, 280, 530, 260);
		
		//Create Local Table Column Header
		TableViewerColumn tableViewerColumn2 = new TableViewerColumn(localTBViewer, SWT.NONE);
		TableColumn tblclmnNewColumn2 = tableViewerColumn2.getColumn();
		tblclmnNewColumn2.setWidth(100);
		tblclmnNewColumn2.setText(trFileMD.COLUMN_HEADER[0]);
		
		TableViewerColumn tableViewerColumn2_1 = new TableViewerColumn(localTBViewer, SWT.NONE);
		TableColumn tblclmnNewColumn2_1 = tableViewerColumn2_1.getColumn();
		tblclmnNewColumn2_1.setWidth(100);
		tblclmnNewColumn2_1.setText(trFileMD.COLUMN_HEADER[1]);
		
		TableViewerColumn tableViewerColumn2_2 = new TableViewerColumn(localTBViewer, SWT.NONE);
		TableColumn tblclmnNewColumn2_2 = tableViewerColumn2_2.getColumn();
		tblclmnNewColumn2_2.setWidth(100);
		tblclmnNewColumn2_2.setText(trFileMD.COLUMN_HEADER[2]);
		
		Label label_localResult = new Label(group_local, SWT.NONE);
		label_localResult.setBounds(0, 550, 530, 30);
		
		Group group_remote = new Group(composite_main, SWT.NONE);
		group_remote.setText("리모트 사이트");
		
		Combo combo_remotePath = new Combo(group_remote, SWT.NONE);
		combo_remotePath.setBounds(0, 30, 530, 30);
		
		//Remote TreeViewer
		remoteTRViewer = new TreeViewer(group_remote, SWT.BORDER);
		Tree tree_remote = remoteTRViewer.getTree();
		tree_remote.setLocation(0, 64);
		tree_remote.setSize(530, 205);
		
		//Remote TableViewer
		remoteTBViewer = new TableViewer(group_remote, SWT.BORDER | SWT.FULL_SELECTION);
		table_remote = remoteTBViewer.getTable();
		table_remote.setHeaderVisible(true);
		table_remote.setBounds(0, 280, 530, 260);
		
		//Create Remote Table Column Header
		TableViewerColumn tableViewerColumn = new TableViewerColumn(remoteTBViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText(trFileMD.COLUMN_HEADER[0]);
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(remoteTBViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText(trFileMD.COLUMN_HEADER[1]);
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(remoteTBViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText(trFileMD.COLUMN_HEADER[2]);
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(remoteTBViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_3.setWidth(100);
		tblclmnNewColumn_3.setText(trFileMD.COLUMN_HEADER[3]);
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(remoteTBViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_4 = tableViewerColumn_4.getColumn();
		tblclmnNewColumn_4.setWidth(100);
		tblclmnNewColumn_4.setText(trFileMD.COLUMN_HEADER[4]);
		
		Label label_remoteResult = new Label(group_remote, SWT.NONE);
		label_remoteResult.setBounds(0, 550, 530, 30);
		
		Group group_fileTable = new Group(composite_main, SWT.NONE);
		group_fileTable.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		
		TableViewer tableViewer_1 = new TableViewer(group_fileTable, SWT.BORDER | SWT.FULL_SELECTION);
		table_file = tableViewer_1.getTable();
		table_file.setBounds(0, 0, 1070, 90);

		/*	Create User Event Function	*/
		
		//Connection button Event
		//서버에 접속 후 TreeViewer에 디렉토리를 구조를 표시
		btn_search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				remot_allDirectoryList = new ArrayList<>();
				TreeParent root = fileTF.remoteConnect(txt_host.getText(), txt_userName.getText(), txt_pwd.getText(), txt_port.getText());
				TreeParent invisibleRoot = new TreeParent("", "");
				invisibleRoot.addChild(root);
				
				remoteTRViewer.setContentProvider(new TreeViewContentProvider());
				remoteTRViewer.setLabelProvider(new TreeViewLabelProvider(workbench));
				remoteTRViewer.setInput(invisibleRoot);
				remoteTRViewer.refresh();
				
				remoteTBViewer.setContentProvider(new ArrayContentProvider());
				remoteTBViewer.setLabelProvider(new TableViewLabelProvider(workbench));
				
				remot_allDirectoryList.addAll(fileTF.getFileModel());
				remoteTBViewer.refresh();
				remoteTBViewer.getControl().setFocus();
			}
		});
		
		//Tree Selection Event to local repository
		//선택한 Tree의 item 하위경로의 디렉토리 구조를 localTBViewer에 표시
		tree_local.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				local_allDirectoryList = new ArrayList<>();
				absolutePath = "";
				
				TreeItem item = (TreeItem) e.item;
				absoluteDirectory(item);
				absolutePath = absolutePath.replace("\\", "");
				
				// ComboBox Local Path
				combo_localPath.setText(absolutePath);
				fileTransferModel.setLocalPath(absolutePath);
				
				// Path Log
//				int num = 0;
//				combo_localPath.add(absolutePath, num++);
//				
//				if(combo_localPath.getItem(num).equals(absolutePath)) {
//					combo_localPath.remove(absolutePath);
//				}
				
				
				local_allDirectoryList.addAll(fileTF.getFileDirectory(absolutePath));
				localTBViewer.setContentProvider(new ArrayContentProvider());
				localTBViewer.setLabelProvider(new TableViewLabelProvider(workbench));
				
				//Print Directory List
				localTBViewer.setInput(local_allDirectoryList);
				localTBViewer.refresh();
				
			}
			
		});
		
		//Table Selection Event to local repository
		//Table에 item을 선택 시 경로와 파일 이름을 저장하여 업로드 & 다운로드 시 사용
		table_local.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem tableItem = (TableItem) e.item;
				File fileModel = (File) tableItem.getData();
				fileTransferModel.setLocalFileName(fileModel.getName());
				fileTransferModel.setLocalPath(fileModel.getParentFile().toString());
				localhookContextMenu();
				
			}
			
		});
		
		//Tree Selection Event to remote repository
		//선택한 Tree의 item 하위경로의 디렉토리 구조를 remoteTBViewer에 표시
		tree_remote.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem item = (TreeItem) e.item;
				
				// ComboBox Remote Path
				String treePath = ((TreeParent) e.item.getData()).getPath();
				combo_remotePath.setText(treePath);

				// Path Log
//				int num = 0;
//				combo_remotePath.add(treePath, num++);
//				
//				if(combo_remotePath.getItem(num).equals(treePath)) {
//					combo_remotePath.remove(num);
//				}
				
				List<DirectoryModel> directoryList = new ArrayList<DirectoryModel>();
				String path;
					
					for(DirectoryModel tfItem : remot_allDirectoryList) {
						//Setting Path for display the table
						path = tfItem.getPath().split("/")[tfItem.getPath().split("/").length - 1];
						//SubDirectories of selected the folder
						if(item.getText().equals(path)) {
							directoryList.add(tfItem);
						}
					}
					fileTransferModel.setRemotePath(treePath);
					remoteTBViewer.setInput(directoryList);
					remoteTBViewer.refresh();
			}
			
		});
		
		//Table Selection Event to remote repository
		//Table에 item을 선택 시 경로와 파일 이름을 저장하여 업로드 & 다운로드 시 사용
		table_remote.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem tableItem = (TableItem) e.item;
				DirectoryModel fileModel = (DirectoryModel) tableItem.getData();
				fileTransferModel.setRemoteFileName(fileModel.getName());
				remotehookContextMenu();
			}
		});
		
		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(localTBViewer.getControl(), "FileTransferView.localTBViewer");
		getSite().setSelectionProvider(localTBViewer);
		workbench.getHelpSystem().setHelp(remoteTBViewer.getControl(), "FileTransferView.remoteTBViewer");
		getSite().setSelectionProvider(remoteTBViewer);
		makeActions();
		localhookContextMenu();
		remotehookContextMenu();
	}
	
	private void localhookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				FileTransferView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(localTBViewer.getControl());
		localTBViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, localTBViewer);
	}
	
	private void fillContextMenu(IMenuManager manager) {
		manager.add(localTableAction);
		if(fileTF.checkBlank(fileTransferModel.getRemotePath(), fileTransferModel.getLocalPath())) {
			// Other plug-ins can contribute there actions here
			manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		}else {
			localTableAction.setEnabled(false);
		}
	}
	
	private void remotehookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				FileTransferView.this.remotefillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(remoteTBViewer.getControl());
		remoteTBViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, remoteTBViewer);
	}
	
	private void remotefillContextMenu(IMenuManager manager) {
		manager.add(remoteTableAction);
		if(fileTF.checkBlank(fileTransferModel.getRemotePath(), fileTransferModel.getLocalPath())) {
			// Other plug-ins can contribute there actions here
			manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		}else {
			remoteTableAction.setEnabled(false);
		}
	}
	
	private void makeActions() {
		localTableAction = new Action() {
			public void run() {
				//File upload
				fileTF.utilfunction("upload", fileTransferModel);
			}
		};
		localTableAction.setText("파일 업로드");
		
		remoteTableAction = new Action() {
			public void run() {
				//File download
				fileTF.utilfunction("download", fileTransferModel);
			}
		};
		remoteTableAction.setText("파일 다운로드");
	}
	
	private void absoluteDirectory(TreeItem item) {
		
		TreeItem parentItem = item.getParentItem();
		String path = item.getText();
		
		if(parentItem == null || parentItem.equals(null)) {
			absolutePath += path;
		}else {
			absoluteDirectory(parentItem);
			absolutePath += "/" + item.getText();
		}
	}

	@Override
	public void setFocus() {
		
	}
}
