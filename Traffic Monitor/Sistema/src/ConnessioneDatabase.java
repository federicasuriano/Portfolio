import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnessioneDatabase{

	//Esegue comandi di update o create sul database e restituisce un int che indica quante row 
	//della table del database sono state modificate

	public static int eseguiUpdateCreateSQL(String comando) throws SQLException, InstantiationException, IllegalAccessException {

		String dbURL = "jdbc:mysql://localhost:3306/mydb";
		String dbUserID = "root";
		String dbPassword = "eattherude";

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (ClassNotFoundException e) {
			System.out.println("Can't load JDBC driver");
		}

		Connection con=null;
		Statement stmt=null;
		int rs=0;
		try {
			con = DriverManager.getConnection(dbURL, dbUserID, dbPassword);
			stmt=con.createStatement();
			rs=stmt.executeUpdate(comando);

			if (con!=null) {System.out.println("Connessione riuscita");} 
			else {System.out.println("Connessione non riuscita");}

			System.out.println("L'ho fatto");
		}
		catch( SQLException se )
		{
			System.out.println( "SQL Exception: "+ se.getMessage() );
			se.printStackTrace();
		}
		finally
		{
			if( stmt!=null ) stmt.close();
			if( con!=null ) con.close();
		}
		return rs;
	}

	//Esegue il comando di select sul database e restituisce i dati in un ResultSet
	public static ResultSet eseguiSQL(String comando) throws SQLException, InstantiationException, IllegalAccessException
	{

		String dbURL = "jdbc:mysql://localhost:3306/mydb";
		String dbUserID = "root";
		String dbPassword = "eattherude";

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (ClassNotFoundException e) {
			System.out.println("Can't load JDBC driver");
		}

		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		try {
			con = DriverManager.getConnection(dbURL, dbUserID, dbPassword);
			stmt=con.createStatement();
			rs=stmt.executeQuery(comando);

			if (con!=null) {System.out.println("Connessione riuscita");} 
			else {System.out.println("Connessione non riuscita");}

			System.out.println("L'ho fatto"); 

		}
		catch( SQLException se )
		{
			System.out.println( "SQL Exception: "+ se.getMessage() );
			se.printStackTrace();
		}
		return rs;

		/*finally
			{
			if( rs!=null ) rs.close();
			if( stmt!=null ) stmt.close();
			if( con!=null ) con.close();
			}*/
	}
}