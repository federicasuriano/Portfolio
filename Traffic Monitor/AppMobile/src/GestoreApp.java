import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class GestoreApp {

	public static void login(String email, String psw) throws MalformedURLException, RemoteException, InstantiationException, IllegalAccessException, SQLException, NotBoundException
	{
		boolean entrato=ClientApp.login(email, psw);
		if (entrato) {
			Utente utente=new Utente(email, psw, "");
			apriApp(utente);
		}
	}
	public static void registrati(String email, String psw) throws MalformedURLException, RemoteException, InstantiationException, IllegalAccessException, NotBoundException, SQLException
	{
		boolean registrato=ClientApp.registrati(email, psw);
		if (registrato) {Utente utente=new Utente(email, psw, "");
		apriApp(utente);}
	}
	
	public static void apriApp(Utente utente) {
		
	//thread 1: attiva il timer
		Runnable task=new MyRunnable();
		((MyRunnable) task).setUtente(utente);
		Thread myThread= new Thread(task);
		myThread.start();
		//apre la schermata di segnalazione
		SegnalazioneApp.main(utente);
	}
}
