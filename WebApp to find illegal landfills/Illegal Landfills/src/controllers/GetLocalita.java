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

import DAO.AnnotazioneDAO;
import DAO.CampagnaDAO;
import DAO.ImmagineDAO;
import DAO.LocalitaDAO;
import DAO.UtenteDAO;
import beans.Annotazione;
import beans.Campagna;
import beans.Immagine;
import beans.Lavoratore;
import beans.Localita;
import beans.Manager;
import enm.Stato;

@WebServlet("/dettagliaCampagna")
@MultipartConfig
public class GetLocalita extends HttpServlet {
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

	public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


		int id = Integer.parseInt(req.getParameter("id"));
		LocalitaDAO localitaDAO=new LocalitaDAO(connection);
		String path="/DettaglioCampagna.jsp";
		List<Localita> localita=new ArrayList();

		//trova la lista delle localita
		try {
			localita=localitaDAO.getListaLocalita(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(localita!=null) {

			//trovo immagini

			ImmagineDAO immagineDAO=new ImmagineDAO(connection);
			
			for (int i=0; i<localita.size(); i++) 
			{	List<Immagine> immaginiPerLocalita = new ArrayList();
			
			try {
				immaginiPerLocalita= immagineDAO.getListaImmagini(localita.get(i).getLocalitaID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (immaginiPerLocalita!=null)
			{

				//faccio il for su immaginiPerLocalita per le annotazioni
				AnnotazioneDAO annotazioneDAO = new AnnotazioneDAO(connection);
				for (int a=0; a<immaginiPerLocalita.size(); a++) 
				{
					List<Annotazione> annotazioniPerImmagine = new ArrayList();
					try 
					{
						annotazioniPerImmagine= annotazioneDAO.getListaAnnotazioni(immaginiPerLocalita.get(a).getImmagineID());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (annotazioniPerImmagine!=null) {
						immaginiPerLocalita.get(a).setAnnotazioni(annotazioniPerImmagine);

					}
				}
				localita.get(i).setImmagini(immaginiPerLocalita);

			}


			}
		}

		//salva la campagna nella session
		Campagna campagna= new Campagna();
		campagna.setCampagnaID(id);
		campagna.setCommittente(req.getParameter("committente"));

		campagna.setStato(Stato.fromString(req.getParameter("stato")));
		campagna.setNome(req.getParameter("nome"));

		HttpSession session=req.getSession();
		session.setAttribute("campagna", campagna);

		//salva le localita + immagini nella request
		if (localita!=null)
		{	req.setAttribute("localita", localita); }

		//forward
		RequestDispatcher dispatcher = req.getRequestDispatcher(path);
		dispatcher.forward(req, res);
	}

	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {}
	}	
}


