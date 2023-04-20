package controllers;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ImmagineDAO;
import DAO.UtenteDAO;
import beans.Campagna;
import beans.Manager;
import enm.Stato;

@WebServlet("/validaImmagine")
@MultipartConfig
public class ValidaImmagine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public void init() throws ServletException {
		String dbURL = "jdbc:mysql://localhost:3306/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
		String dbUserID = "root";
		String dbPassword = "eattherude";

try {
	DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	Class.forName("com.mysql.jdbc.Driver").newInstance();
}
catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
	System.out.println("Can't load JDBC driver");
}


try {
	connection = DriverManager.getConnection(dbURL, dbUserID, dbPassword);

	if (connection!=null) {System.out.println("Connessione riuscita");} 
	else {System.out.println("Connessione non riuscita");}

	System.out.println("L'ho fatto");
}

catch( SQLException se )
{
	System.out.println( "SQL Exception: "+ se.getMessage() );
	se.printStackTrace();
}

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		boolean modificato=false;
		
		//informazioni campagna per redirect
		HttpSession session=req.getSession();
		Campagna campagna=(Campagna) session.getAttribute("campagna");
		int idCampagna= campagna.getCampagnaID();
		String committenteCampagna =campagna.getCommittente();
		String nomeCampagna=campagna.getNome();
		Stato statoCampagna=campagna.getStato();
		String redirect="./dettagliaCampagna?id="+idCampagna+"&nome="+nomeCampagna+ "&committente="+committenteCampagna + "&stato="+statoCampagna;
		String validita =req.getParameter("valida");
		String immagine =req.getParameter("immagine");
		String path= redirect;
		int id=Integer.parseInt(immagine);
		boolean val=false;
		if (validita.equals("1")) {val=true;}
		
ImmagineDAO immagineDAO = new ImmagineDAO(connection);

try {
		modificato=immagineDAO.aggiornaConflitto(id, val);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if (modificato) {res.sendRedirect(path);}
	else {res.sendError(500);}
	}
	
	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {}
	}	
}


