import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.sql.SQLException;

public class InterfacciaRicezioneApp extends UnicastRemoteObject implements InterfacciaInvioApp{

	protected InterfacciaRicezioneApp() throws RemoteException {
		super();
	}

	public String invia(String GPS, Date data, LivelloDiTraffico livello, String email)
			throws RemoteException, SQLException, InstantiationException, IllegalAccessException {


		boolean vero=AmministratoreApp.controllaDato(GPS, data, livello, email);
		if (vero) {
			return "Ho ricevuto "+ GPS + " " + livello + " ";
		}
		else return "Qualcosa non ha funzionato.";
	}

	public String aggiornaGPS(String gps, String email) throws InstantiationException, IllegalAccessException, SQLException {


		boolean vero=GestoreUtenti.aggiornaGPS(gps, email);
		if (vero) {
			int livello=0;
			livello=GestoreUtenti.notificaUtente(email);
			return "Nella tua zona c'è del traffico di livello "+ livello;
		}
		else return " ";
	}

	public boolean loginApp(String email, String psw)throws RemoteException, SQLException, InstantiationException, IllegalAccessException
	{
		return GestoreUtenti.loginApp(email, psw);	 }

	public boolean registraUtente(String email, String psw)throws RemoteException, SQLException, InstantiationException, IllegalAccessException
	{
		return GestoreUtenti.RegistrazioneApp(email, psw);
	}

	@Override
	public String invia(String GPS, Date data, LivelloDiTraffico livello)
			throws RemoteException, SQLException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}