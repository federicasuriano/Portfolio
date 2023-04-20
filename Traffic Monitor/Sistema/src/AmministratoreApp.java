import java.sql.SQLException;
import java.util.Date;
import java.util.Timer;

public class AmministratoreApp extends Amministratore {

	public static boolean controllaDato(String GPS, Date dataInfo, LivelloDiTraffico livello, String email) throws InstantiationException, IllegalAccessException, SQLException {

		long range= ((dataInfo.getTime())/300000);


		int rangeInt=(int) range; 

		//controllo il database
		boolean selezione= checkDati(GPS, rangeInt);
		String comando;
		if(!selezione) {
			System.out.println("Devi creare il dato app");
			boolean creato=creaDato(rangeInt, livello.getInt(), GPS);
			if(creato) {
				System.out.println("Ho creato il dato app");
				return true;}}
		else {
			System.out.println("Devi aggiornare il dato app");
			comando="Update datotraffico "+ "set traffico=traffico*0.7+" +livello.getInt() + 
					"*0.3 Where rangeDiTempo= " +range+" AND data='"+ data + "'"+ " AND coordinateGPS='"
					+GPS+"'";
			int datoAggiornato=ConnessioneDatabase.eseguiUpdateCreateSQL(comando);
			if (datoAggiornato>0) {System.out.println("Ho aggiornato il dato app");
			return true;}


		}

		return false;
	}
	
	public static void aggiornaStatoApp() {
		Timer timer = new Timer();
		timer.schedule(new TimedAggiornamentoApp(), 0, 600000);	
	}
}
