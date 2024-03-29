package org.inbus.teamfiletransferclient.views;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewDirectoryDialog extends TitleAreaDialog{

	private Text txtDirectoryName;
	
	private String directoryName;

	public NewDirectoryDialog(Shell parentShell) {
		super(parentShell);
	}
	
	
	@Override
	public void create() {
		super.create();
		setTitle("New Directory");
		setMessage("please enter the new directory name", IMessageProvider.INFORMATION);
	}


	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);
        
        createNewDirectoryName(container);
        
		return area;
	}

	private void createNewDirectoryName(Composite container) {
		Label lbtFirstName = new Label(container, SWT.NONE);
        lbtFirstName.setText("First Name");

        GridData dataFirstName = new GridData();
        dataFirstName.grabExcessHorizontalSpace = true;
        dataFirstName.horizontalAlignment = GridData.FILL;

        txtDirectoryName = new Text(container, SWT.BORDER);
        txtDirectoryName.setLayoutData(dataFirstName);
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}
	
    // save content of the Text fields because they get disposed
    // as soon as the Dialog closes
    private void saveInput() {
        directoryName = txtDirectoryName.getText();
    }

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}


	public String getDirectoryName() {
		return directoryName;
	}


	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}
	
}
