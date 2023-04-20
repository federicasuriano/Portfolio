import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.TimerTask;

public class TimedAggiornamento extends TimerTask {
	public Utente u;

	public TimedAggiornamento(Utente u)
	{
		this.u=u;
	}
	public void run() {
		System.out.println(u.getEmail());
		try {
			u.aggiornaGPS();

		} catch (MalformedURLException | RemoteException | InstantiationException | IllegalAccessException
				| NotBoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}