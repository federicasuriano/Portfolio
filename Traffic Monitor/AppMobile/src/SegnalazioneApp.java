import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseAdapter;

public class SegnalazioneApp {
	public static Utente utente;
	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(Utente u) {
		try {
			utente=u;
			SegnalazioneApp window = new SegnalazioneApp();
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
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(424, 632);
		shell.setText("SWT Application");
		shell.setLayout(null);

		Scale scale = new Scale(shell, SWT.NONE);
		scale.setBounds(37, 291, 331, 49);
		scale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int x=scale.getSelection();
				if (x>0) {
try {
	utente.inviaSegnalazione(x);
} catch (MalformedURLException | RemoteException | InstantiationException | IllegalAccessException | NotBoundException
		| SQLException | ParseException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
				}
			}
		});





		scale.setMaximum(5);

		Label lblCTrafficoFaccelo = new Label(shell, SWT.CENTER);
		lblCTrafficoFaccelo.setBounds(63, 161, 279, 66);
		lblCTrafficoFaccelo.setText("C'è traffico? Faccelo sapere");

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(108, 262, 469, 49);
		lblNewLabel.setText("1          2           3          4         5");

	}
}