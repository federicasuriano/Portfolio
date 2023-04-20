package controllers;
import java.io.File;
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
import javax.servlet.http.Part;

import DAO.ImmagineDAO;
import DAO.UtenteDAO;
import beans.Lavoratore;
import beans.Manager;

@WebServlet("/modificaProfiloLavoratore")
@MultipartConfig
public class ModificaProfiloLavoratore extends HttpServlet {
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
		boolean fatto=false;
		HttpSession session=req.getSession();
		Lavoratore utente=(Lavoratore) session.getAttribute("utenteCorrente");
		int id= utente.getLavoratoreID();
		String est=utente.getEstensione();
		String username =req.getParameter("nome_utente");
		String password =req.getParameter("password");
		String cambioImg= req.getParameter("cambioImg");
		String redirect=getServletContext().getContextPath() + "/getCampagneLavoratore";
		String estensione=" ";

		UtenteDAO utenteDAO = new UtenteDAO(connection);
	if (cambioImg.equals("true")) {
	
		
		
		String tomcatBase=System.getProperty("catalina.base");
	String path="/webapps/ImmaginiProfilo/";
	File saveDir=new File(tomcatBase);
	Part part=null;
	try {
		part=req.getPart("imgProfilo");
	}
	catch (Exception e)  {
		e.printStackTrace();
		res.sendRedirect("./ModificaProfiloLavoratore.jsp");
	}
	
	File fold=new File(tomcatBase + path + id + est);
    fold.delete();
	
	estensione=Utility.getSubmittedFileName(part).substring(Utility.getSubmittedFileName(part).lastIndexOf("."));
	
   	File immagine=new File(tomcatBase + path + Integer.toString(id));
		if(!saveDir.exists()) {
			saveDir.mkdirs();
		}
		part.write(tomcatBase + path + id + estensione);
   
	}
	
	try {
		 fatto = utenteDAO.modificaProfiloLavoratore(id, username, password, estensione);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if (!fatto) {
		redirect="./ModificaProfiloLavoratore.jsp";}
	else {try {
		utente=utenteDAO.loginLavoratore(username, password, false);
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	session.setAttribute("utenteCorrente", utente);}
	res.sendRedirect(redirect);
	}	
}


