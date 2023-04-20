import java.rmi.RemoteException;
import java.util.Date;
import java.sql.SQLException;

public interface InterfacciaInvioApp extends InterfacciaInvio {
	public String invia(String GPS, Date data, LivelloDiTraffico livello, String email) throws RemoteException, SQLException, InstantiationException, IllegalAccessException;
	public String aggiornaGPS(String gps, String email)throws RemoteException, SQLException, InstantiationException, IllegalAccessException;
	public boolean loginApp(String email, String psw)throws RemoteException, SQLException, InstantiationException, IllegalAccessException;
	public boolean registraUtente(String email, String password)throws RemoteException, SQLException, InstantiationException, IllegalAccessException;
}
