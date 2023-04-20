import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;


public class RicevutoLivello {

	protected Shell shell;
	private static int livello;
	private static Utente utente;

	/**
	 * Launch the application.
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public static void main(int x, Utente u) {
		livello=x;
		utente=u;
		try {
			RicevutoLivello window = new RicevutoLivello(); 
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
		shell.open();
		shell.layout(); 
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.close();
		return;

	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");

		Label lblRicevutoLivello = new Label(shell, SWT.CENTER);
		lblRicevutoLivello.setBounds(152, 96, 133, 40);
		lblRicevutoLivello.setText("Livello");

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(249, 96, 83, 24); 
		lblNewLabel.setText(" "+getLivello());

		Button btnInviaSegnalazione = new Button(shell, SWT.CENTER);
		btnInviaSegnalazione.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					utente.inviaSegnalazione(livello);
				} catch (MalformedURLException | RemoteException | InstantiationException | IllegalAccessException
						| NotBoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				SegnalazioneApp.main(null);
			}
		});
		btnInviaSegnalazione.setBounds(238, 142, 166, 29);
		btnInviaSegnalazione.setText("Invia segnalazione");

		Button btnTornaIndietro = new Button(shell, SWT.NONE);
		btnTornaIndietro.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SegnalazioneApp.main(null);
				shell.dispose();
			}
		});
		btnTornaIndietro.setBounds(59, 142, 117, 29);
		btnTornaIndietro.setText("Torna indietro");



		btnTornaIndietro.setBounds(74, 142, 133, 29);
		btnTornaIndietro.setText("Torna indietro");

	}

	public int getLivello() {
		return livello;
	}
}