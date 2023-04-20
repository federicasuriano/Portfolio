package controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

import DAO.ImmagineDAO;
import DAO.UtenteDAO;
import beans.Campagna;
import beans.Manager;
import enm.Risoluzione;

/**
 * Servlet implementation class salvaImmagine
 */
@MultipartConfig 
@WebServlet("/salvaImmagine") 
public class SalvaImmagine extends HttpServlet {

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
	 int immagineID=0;

String dataDiRecupero= req.getParameter("dataDiRecupero");
String provenienza= req.getParameter("provenienza");
String risoluzione=req.getParameter("risoluzione");


System.out.println(dataDiRecupero + provenienza + risoluzione);
String loc=req.getParameter("localita");
int localita=Integer.parseInt(loc);
System.out.println(localita);
	
	String tomcatBase=System.getProperty("catalina.base");
	String path="/webapps/Immagini/";
	File saveDir=new File(tomcatBase);
	Part part=null;
	try {
		part=req.getPart("img");
	}
	catch (Exception e)  {
		e.printStackTrace();
		res.sendRedirect("/index.html");
	}
	
	String estensione=Utility.getSubmittedFileName(part).substring(Utility.getSubmittedFileName(part).lastIndexOf("."));
	ImmagineDAO immagineDAO= new ImmagineDAO(connection);
	try {
		immagineID = immagineDAO.salvaImmagine(provenienza, dataDiRecupero,  risoluzione, localita, estensione);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (immagineID!=0) {
		File immagine=new File(tomcatBase + path + Integer.toString(immagineID));
		if(!saveDir.exists()) {
			saveDir.mkdirs();
		}
		part.write(tomcatBase + path + immagineID + estensione);
		
	}
	
	HttpSession session= req.getSession();
	Campagna campagna=(Campagna) session.getAttribute("campagna");
	int id=campagna.getCampagnaID();
	
	res.sendRedirect("/aggiornaMappaManager?id="+id);

}

public void destroy() {
	try {
		if (connection != null) {
			connection.close();
		}
	} catch (SQLException sqle) {}
}	
}


