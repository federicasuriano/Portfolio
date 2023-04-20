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

@WebServlet("/registrazione")
@MultipartConfig
public class Registrazione extends HttpServlet {
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
		String email =req.getParameter("email");
		String password =req.getParameter("password");
		boolean ruolo= true;

		if (req.getParameter("ruolo").equals("lavoratore")) {ruolo=false;}

		if  (username==null || email==null || password == null) {res.sendError(505, "Input errati");}

		String redirect;

		UtenteDAO utenteDAO= new UtenteDAO(connection);

		try {
			if (ruolo) {
				boolean registrato=utenteDAO.registraManager(username, email, password, ruolo);
				redirect="/HomeManager.jsp";
					String path= getServletContext().getContextPath() + redirect;

				if (registrato)	{
					Manager utente= utenteDAO.loginManager(username, password, ruolo);
					redirect="/HomeManager.jsp";
					if (utente!=null) {
						HttpSession session=req.getSession();
						session.setAttribute("utenteCorrente", utente);
					    res.sendRedirect(path);}
					else {
						res.sendRedirect("./ErroreRegistrazione.html");
					}
					}
				
				else {
					res.sendRedirect("./ErroreRegistrazione.html");
				}
			
			}
			
			
			else {
			

				boolean registrato=utenteDAO.registraLavoratore(username, email, password, ruolo);
				redirect="/getCampagneLavoratore";
					String path= getServletContext().getContextPath() + redirect;

				if (registrato)	{
					Lavoratore utente= utenteDAO.loginLavoratore(username, password, ruolo);
					
					if (utente!=null) {
						HttpSession session=req.getSession();
						session.setAttribute("utenteCorrente", utente);
					    res.sendRedirect(path);}
					else {
						res.sendRedirect("./ErroreRegistrazione.html");
					}}
				
				else {
					res.sendRedirect("./ErroreRegistrazione.html");
				}
		
			}

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


