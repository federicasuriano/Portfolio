
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Amministratore implements AdminSistema{
	protected static String data;

	public void setData() {
		Date data1=new Date(System.currentTimeMillis());
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
		data=sdr.format(data1).toString();
	}
	//crea un nuovo datotraffico
	public static boolean creaDato(int range, int livello, String GPS) throws InstantiationException, IllegalAccessException, SQLException
	{
		String comando="INSERT INTO datotraffico " + 
			"VALUES ('"+ data +"', " +range+", "+livello+", '"
			+GPS+"')";
	int datoCreato=ConnessioneDatabase.eseguiUpdateCreateSQL(comando);
	if (datoCreato>0) {System.out.println("Ho creato il dato"); return true;}

	return false;
	}

	//controlla se certi dati esistono già
	public static boolean checkDati(String GPS, int range) throws InstantiationException, IllegalAccessException, SQLException
	{
		String comando="Select *" +" from datotraffico " + "where coordinateGPS='"
			+GPS+"' AND rangeditempo="
			+range+" AND data='"+data+"'";
	ResultSet selezione=ConnessioneDatabase.eseguiSQL(comando);
	if (!selezione.next()) {return false;}
	return true;
	}

	//organizza i dati mandati dalla centralina
	public static boolean controllaDato(String coordinateGPS, Date dataInfo, LivelloDiTraffico livelloDiTraffico) throws SQLException, InstantiationException, IllegalAccessException {
		aggiornaStatoCentralina(coordinateGPS);
		
		//controllo il range di tempo dividendo i millisecondi per 300000 (range da 288 fasce da 5 min dalle 00:00 alle 23:55)
		long range= ((dataInfo.getTime())/300000);
		
		int rangeInt=(int) range; 

		//controllo il database
		boolean selezione= checkDati(coordinateGPS, rangeInt);
		String comando;

		if (!selezione) {System.out.println("Devi creare il dato");
		boolean creato=creaDato(rangeInt, livelloDiTraffico.getInt(), coordinateGPS);
		if(creato) {System.out.println("Ho creato il dato"); return true;}}
		else {System.out.println("Devi aggiornare il dato");
		comando="Update datotraffico "+ "set traffico=" +livelloDiTraffico.getInt() + 
				" Where rangeDiTempo= " +range+" AND data='"+ data + "'"+ " AND coordinateGPS='"
				+coordinateGPS+"'";
		int datoAggiornato=ConnessioneDatabase.eseguiUpdateCreateSQL(comando);
		if (datoAggiornato>0) {System.out.println("Ho aggiornato il dato"); return true;}
		}
		System.out.println("Siamo fuori");

		return false;
	}


	//seleziona dati traffico (e li stampa)
	public static void mostraDatiTraffico() throws InstantiationException, IllegalAccessException, SQLException {
		
		String comando="SELECT * from datotraffico";
		ResultSet mostra=ConnessioneDatabase.eseguiSQL(comando);
		while( mostra.next() )
		{
			System.out.printf( "%-8s\t", mostra.getString( "data" ) );
			System.out.printf( "%-8s\t", mostra.getInt( "rangeDiTempo" ) );
			System.out.printf( "%-8s\t", mostra.getFloat( "traffico" ) );
			System.out.printf( "%-8s\t", mostra.getString( "coordinateGPS" ) );
			System.out.println();
		}
		if (!mostra.next()) {System.out.println("Non ci sono dati disponibili");}
	}

	//seleziona dati utente (e li stampa)
	public static void mostraDatiUtenti() throws InstantiationException, IllegalAccessException, SQLException {
		
		String comando="SELECT * from Utente";
		ResultSet mostra=ConnessioneDatabase.eseguiSQL(comando);
		while( mostra.next() ) {
			System.out.printf( "%-8s\t", mostra.getString( "email" ) );
			System.out.printf( "%-8s\t", mostra.getString( "psw" ) );
			System.out.printf( "%-8s\t", mostra.getString( "coordinateGPS" ));
			System.out.printf( "%-8s\t", mostra.getString( "stato" ));
			System.out.println();
		}
		if (!mostra.next()) {System.out.println("Non ci sono più dati disponibili");}
	}
	
	public static DefaultTableModel mostraStatoApp() throws InstantiationException, IllegalAccessException, SQLException {
		String comando="SELECT * from Utente where stato=1";
		ResultSet rs= ConnessioneDatabase.eseguiSQL(comando);
	    java.sql.ResultSetMetaData metaData = rs.getMetaData();
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }
	    
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}
	public static DefaultTableModel mostraStatoCentraline() throws InstantiationException, IllegalAccessException, SQLException {
		String comando="SELECT * from centralina where stato=1";
		ResultSet rs= ConnessioneDatabase.eseguiSQL(comando);
	    java.sql.ResultSetMetaData metaData = rs.getMetaData();
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }
	    
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}
	
	public static void aggiornaStatoCentralina(String s) throws InstantiationException, IllegalAccessException, SQLException {
		String comando="Update centralina set stato=1 where coordinateGPS='"+s+"'";
		ConnessioneDatabase.eseguiUpdateCreateSQL(comando);
	}
	
	public static void resettaStatoCentralina() { //ogni 30 min
		Timer timer = new Timer();
		timer.schedule(new TimedAggiornamentoCentralina(), 0, 1800000);	
	}
}