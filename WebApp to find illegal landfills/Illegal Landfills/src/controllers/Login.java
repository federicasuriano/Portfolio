package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CampagnaDAO;
import DAO.UtenteDAO;
import beans.Campagna;
import beans.Lavoratore;
import beans.Manager;

@WebServlet("/login")
@MultipartConfig
public class Login extends HttpServlet {
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
		String username =req.getParameter("nome_utente");
		String password =req.getParameter("password");
		boolean ruolo= true;
		String r= req.getParameter("ruolo");
		if (r.equals("lavoratore")) {ruolo=false;}

		if  (username==null || password == null) {res.sendError(505, "Input errati");}

		String redirect;

		UtenteDAO utenteDAO= new UtenteDAO(connection);

		try {
			if (ruolo) {
					redirect="/getCampagneManager";
					String path= getServletContext().getContextPath() + redirect;
					Manager utente= utenteDAO.loginManager(username, password, ruolo);
					if (utente!=null) {
						HttpSession session=req.getSession();
						session.setAttribute("utenteCorrente", utente);
					    res.sendRedirect(path);}
					else {
						
path= "./ErroreLogin.html";	
res.sendRedirect(path);
}
					}
				
			
			
			
			
			else {
				redirect="/getCampagneLavoratore";
				String path= getServletContext().getContextPath() + redirect;
				Lavoratore utente= utenteDAO.loginLavoratore(username, password, ruolo);
				if (utente!=null) {
					HttpSession session=req.getSession();
					session.setAttribute("utenteCorrente", utente);
				    res.sendRedirect(path);}
				else {
					path= "./ErroreLogin.html";	
					res.sendRedirect(path); 
				}}
			

		}
		catch(SQLException e) {
			res.sendError(500, "Errore database");}
	}
	
	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {}
	}	
}


