
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class LoginApp {

	protected Shell txtEmail;
	private Text txtExamplemailcom;
	private Text txtPsw;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LoginApp window = new LoginApp();
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
		txtEmail.open();
		txtEmail.layout();
		while (!txtEmail.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		txtEmail = new Shell();
		txtEmail.setSize(424, 632);
		txtEmail.setText("Login");
		txtEmail.setLayout(null);
		
		Label lblEmail = new Label(txtEmail, SWT.CENTER);
		lblEmail.setBounds(164, 119, 83, 24);
		lblEmail.setText("Email");
		
		Label lblPassword = new Label(txtEmail, SWT.CENTER);
		lblPassword.setBounds(164, 230, 83, 24);
		lblPassword.setText("Password");
		
		txtExamplemailcom = new Text(txtEmail, SWT.BORDER | SWT.CENTER);
		txtExamplemailcom.setText("example@mail.com");
		txtExamplemailcom.setBounds(92, 149, 226, 32);
		
		txtPsw = new Text(txtEmail, SWT.BORDER | SWT.CENTER);
		txtPsw.setText("*********");
		txtPsw.setBounds(92, 279, 216, 32);
		
		Button btnLogin = new Button(txtEmail, SWT.NONE);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String email=txtEmail.getText();
				String psw=txtPsw.getText();
				try {
					//chiamo il client dell'app per connettermi all'amministratore del database
					GestoreApp.login(email, psw);
				} catch (MalformedURLException | RemoteException | InstantiationException | IllegalAccessException
						| SQLException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(164, 360, 100, 29);
		btnLogin.setText("Login");
		
		Button btnRegistrati = new Button(txtEmail, SWT.NONE);
		btnRegistrati.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//chiamo la schermata per la registrazione
				RegistrazioneApp.main(null);
			
			}
		});
		btnRegistrati.setBounds(164, 462, 100, 29);
		btnRegistrati.setText("Registrati!");
		
		Label lblSeiUnNuovo = new Label(txtEmail, SWT.CENTER);
		lblSeiUnNuovo.setBounds(97, 416, 216, 32);
		lblSeiUnNuovo.setText("Sei un nuovo utente?");

	}
}
