package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/creaCampagna")
@MultipartConfig
public class CreaCampagna extends HttpServlet {
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
		String redirect="";
		HttpSession session=req.getSession();
		Manager utente=(Manager) session.getAttribute("utenteCorrente");
		int managerID= utente.getManagerID();
		String nome_campagna=req.getParameter("nome_campagna");
		String committente=req.getParameter("committente");
		CampagnaDAO campagnaDAO=new CampagnaDAO(connection);
		int id=0;
	try {
	 id=campagnaDAO.registraCampagna(nome_campagna, committente, managerID);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (id!=0) {
	 redirect="/dettagliaCampagna?id="+id+"&nome="+ nome_campagna+"&committente="+ committente+"&stato=creata";
	}
	else {redirect="/getCampagneManager"; 
}		
	String path= getServletContext().getContextPath() + redirect;
res.sendRedirect(path);
	}
	
	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {}
	}	
}


