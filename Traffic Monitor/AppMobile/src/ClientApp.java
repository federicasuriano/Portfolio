
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.sql.SQLException;

public class ClientApp {


	public static void inviaSegnalazione (String GPS, Date data, LivelloDiTraffico livello, String email) throws MalformedURLException, RemoteException, NotBoundException, SQLException, InstantiationException, IllegalAccessException{
		InterfacciaInvioApp interfaccia = (InterfacciaInvioApp) Naming.lookup("rmi://localhost:5099/invioapp");
		System.out.println("---" + interfaccia.invia(GPS, data, livello, email) + "   " + interfaccia.getClass().getName());
	}
	public String aggiornaGPS(String gps, String email) throws MalformedURLException, RemoteException, NotBoundException, InstantiationException, IllegalAccessException, SQLException
	{
		InterfacciaInvioApp interfaccia = (InterfacciaInvioApp) Naming.lookup("rmi://localhost:5099/invioapp");
		return interfaccia.aggiornaGPS(gps, email);
	}

	public static boolean login(String email, String psw) throws MalformedURLException, RemoteException, InstantiationException, IllegalAccessException, SQLException, MalformedURLException, NotBoundException {
		InterfacciaInvioApp interfaccia = (InterfacciaInvioApp) Naming.lookup("rmi://localhost:5099/invioapp");
		return interfaccia.loginApp(email, psw);};

		public static boolean registrati(String email, String psw) throws MalformedURLException, RemoteException, NotBoundException, InstantiationException, IllegalAccessException, SQLException {
			InterfacciaInvioApp interfaccia = (InterfacciaInvioApp) Naming.lookup("rmi://localhost:5099/invioapp");
			return interfaccia.registraUtente(email, psw);};

}
