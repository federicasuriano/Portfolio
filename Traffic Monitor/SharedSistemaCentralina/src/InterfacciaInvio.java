import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.sql.SQLException;

public interface InterfacciaInvio extends Remote {
	//	public String invia(InformazioneCentralina informazione) throws RemoteException;
	public String invia(String GPS, Date data, LivelloDiTraffico livello) throws RemoteException, SQLException, InstantiationException, IllegalAccessException;
}
