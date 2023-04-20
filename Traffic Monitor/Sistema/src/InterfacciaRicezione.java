import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class InterfacciaRicezione extends UnicastRemoteObject implements InterfacciaInvio{

	protected InterfacciaRicezione() throws RemoteException {
		super();

	}

	public String invia(String GPS, java.util.Date data, LivelloDiTraffico livello)
			throws RemoteException, SQLException, InstantiationException, IllegalAccessException {
		Amministratore amministratore= new Amministratore();
		boolean vero=amministratore.controllaDato(GPS, data, livello);
		if (vero) {
			return "Ho ricevuto "+ GPS + " " + livello + " ";
		}
		else return "Non ho ricevuto.";
	}
}
