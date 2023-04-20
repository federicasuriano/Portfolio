import java.sql.ResultSet;
import java.sql.SQLException;

public class GestoreUtenti {

	//registra i nuovi utenti nel database
	public static boolean RegistrazioneApp(String email, String psw) throws InstantiationException, IllegalAccessException, SQLException
	{

		String comando="Insert into Utente values ('"+email+"','"+psw+"',null, 1)";
		int x=ConnessioneDatabase.eseguiUpdateCreateSQL(comando);
		if (x>0) {
			aggiornaStatoUtente(email);
			return true;}
		return false;
	}

	//login dell'utente tramite consultazione del database
	public static boolean loginApp(String email, String psw) throws InstantiationException, IllegalAccessException, SQLException
	{
		String comando="select psw from Utente where email='"+email+"'";
		ResultSet rs=ConnessioneDatabase.eseguiSQL(comando);
		while(rs.next()) {if(rs.getString("psw").equals(psw)) {
			aggiornaStatoUtente(email);
			return true;}}
		return false;
	}

	//aggiorna la posizione dell'utente sul database
	public static boolean aggiornaGPS(String gps, String email) throws InstantiationException, IllegalAccessException, SQLException {
		String comando="Update Utente set coordinateGPS='"+ gps+"' where email='"+email+"'";
		int x=ConnessioneDatabase.eseguiUpdateCreateSQL(comando);
		if (x>0) {
			aggiornaStatoUtente(email);
			return true;}
		return false;
	}

	//notifica utente
	public static int notificaUtente(String email) throws InstantiationException, IllegalAccessException, SQLException {
		String comando="SELECT Utente.email, Utente.coordinateGPS, datotraffico.traffico\r\n" + 
				"FROM Utente JOIN DatoTraffico on Utente.coordinateGPS=DatoTraffico.coordinateGPS \r\n" + 
				"AND\r\n" + 
				"DatoTraffico.data=(SELECT max(data)\r\n" + 
				"                                FROM DatoTraffico\r\n" + 
				"                                where Utente.coordinateGPS=DatoTraffico.coordinateGPS)\r\n" + 
				"AND\r\n" + 
				"DatoTraffico.rangeDiTempo=(SELECT max(rangeDiTempo)\r\n" + 
				"                              FROM DatoTraffico\r\n" + 
				"                              where Utente.coordinateGPS=DatoTraffico.coordinateGPS)";

		ResultSet rs=ConnessioneDatabase.eseguiSQL(comando); int x=0;
		while(rs.next())
		{if (rs.getString("email").equals(email)) {x=rs.getInt("traffico");}}
		return x;

	}

	public static void aggiornaStatoUtente(String email) throws InstantiationException, IllegalAccessException, SQLException
	{
		String comando="Update Utente set stato=1 where email='"+email+"'";
		int x=ConnessioneDatabase.eseguiUpdateCreateSQL(comando);
	}
}
