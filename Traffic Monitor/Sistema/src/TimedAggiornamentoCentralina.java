import java.sql.SQLException;
import java.util.TimerTask;

public class TimedAggiornamentoCentralina extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String comando="Update centralina set stato=0";
		try {
			ConnessioneDatabase.eseguiUpdateCreateSQL(comando);
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}

	}

}
