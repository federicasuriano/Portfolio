import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class VisioneDatabase {

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
}
