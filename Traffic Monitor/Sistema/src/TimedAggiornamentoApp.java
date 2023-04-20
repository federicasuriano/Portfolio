import java.sql.SQLException;
import java.util.TimerTask;

public class TimedAggiornamentoApp extends TimerTask {

	@Override
	public void run() {
		//metto lo stato di tutte le app a 0
		
		String comando="Update utente set stato=0";
		try {
			ConnessioneDatabase.eseguiUpdateCreateSQL(comando);
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}

	}

}
