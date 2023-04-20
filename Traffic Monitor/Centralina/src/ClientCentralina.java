import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.sql.SQLException;

public class ClientCentralina {


	public void inviaInformazione (String GPS, Date data, LivelloDiTraffico livello) throws MalformedURLException, RemoteException, NotBoundException, SQLException, InstantiationException, IllegalAccessException{
		InterfacciaInvio interfaccia = (InterfacciaInvio) Naming.lookup("rmi://localhost:5099/inviocentralina");
		System.out.println("---" + interfaccia.invia(GPS, data, livello) + "   " + interfaccia.getClass().getName());
	}
}
