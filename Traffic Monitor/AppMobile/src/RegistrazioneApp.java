import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class RegistrazioneApp {

	protected Shell shlRegistrati;
	private Text txtEmailRegistrazione;
	private Text txtPswRegistrazione;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RegistrazioneApp window = new RegistrazioneApp();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlRegistrati.open();
		shlRegistrati.layout();
		while (!shlRegistrati.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlRegistrati = new Shell();
		shlRegistrati.setSize(424, 632);
		shlRegistrati.setText("Registrati!");
		
		Label lblEmail = new Label(shlRegistrati, SWT.CENTER);
		lblEmail.setBounds(161, 117, 83, 24);
		lblEmail.setText("Email");
		
		Label lblPassword = new Label(shlRegistrati, SWT.CENTER);
		lblPassword.setBounds(161, 250, 83, 24);
		lblPassword.setText("Password");
		
		txtEmailRegistrazione = new Text(shlRegistrati, SWT.BORDER);
		txtEmailRegistrazione.setBounds(112, 172, 183, 32);
		
		txtPswRegistrazione = new Text(shlRegistrati, SWT.BORDER);
		txtPswRegistrazione.setBounds(111, 300, 183, 32);
		
		Button btnRegistrati = new Button(shlRegistrati, SWT.NONE);
		btnRegistrati.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				String nuovaEmail=txtEmailRegistrazione.getText();
				String nuovaPsw=txtPswRegistrazione.getText();
				try {
					GestoreApp.registrati(nuovaEmail, nuovaPsw);
				} catch (MalformedURLException | RemoteException | InstantiationException | IllegalAccessException
						| NotBoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRegistrati.setBounds(153, 385, 100, 29);
		btnRegistrati.setText("Registrati!");

	}

}
