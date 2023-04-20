
package controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.servlet.http.Part;

import DAO.CampagnaDAO;
import DAO.ImmagineDAO;
import DAO.LocalitaDAO;
import DAO.UtenteDAO;
import beans.Campagna;
import beans.Lavoratore;
import beans.Manager;

@WebServlet("/aggiungiImmagineConLocalita")
@MultipartConfig
public class AggiungiImmagineConLocalita extends HttpServlet {
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
		int localitaID=0;

		String dataDiRecupero= req.getParameter("dataDiRecupero");
		String provenienza= req.getParameter("provenienza");
		String risoluzione=req.getParameter("risoluzione");

		String lat= req.getParameter("lat");
		String longi= req.getParameter("long");
		String nome= req.getParameter("nome");
		String comune= req.getParameter("comune");
		String regione= req.getParameter("regione");

		HttpSession session=req.getSession();

		Campagna campagna=(Campagna) session.getAttribute("campagna");
		int campagnaID=campagna.getCampagnaID();
		LocalitaDAO localitaDAO=new LocalitaDAO(connection);
		try {
			localitaID=localitaDAO.creaLocalita(lat, longi, nome, comune, regione, campagnaID);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println(localitaID);
		if (localitaID!=0) {

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
				immagineID = immagineDAO.salvaImmagine(provenienza, dataDiRecupero,  risoluzione, localitaID, estensione);
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


			res.sendRedirect("/aggiornaMappaManager?id="+campagnaID);
		}

	}
	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {}
	}	
}


