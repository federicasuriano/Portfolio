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

import DAO.UtenteDAO;
import beans.Manager;

@WebServlet("/modificaProfilo")
@MultipartConfig
public class ModificaProfilo extends HttpServlet {
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

	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		boolean modificato=false;
		HttpSession session=req.getSession();
		Manager utente=(Manager) session.getAttribute("utenteCorrente");
		int id= utente.getManagerID();
		String email=utente.getEmail();
		String username =req.getParameter("nome_utente");
		String password =req.getParameter("password");
		String redirect="/getCampagneManager";
		String path= getServletContext().getContextPath() + redirect;
	UtenteDAO utenteDAO = new UtenteDAO(connection);
	try {
		modificato=utenteDAO.modificaProfiloManager(username, email, password, id);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if (modificato) {
		try {
			utente=utenteDAO.loginManager(username, password, true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute("utenteCorrente", utente);
		
		res.sendRedirect(path);}
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


