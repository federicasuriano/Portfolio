package DAO;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beans.Annotazione;
import beans.Campagna;
import beans.Immagine;
import enm.Fiducia;
import enm.Risoluzione;

public class AnnotazioneDAO {

	private Connection con;

	public AnnotazioneDAO(Connection con) {
		this.con=con;
	}

	public List<Annotazione> getListaAnnotazioni(int id) throws SQLException
	{	List<Annotazione> lista= new ArrayList();


	String query= "Select * from annotazione where Immagine=?;";
	ResultSet result=null;
	PreparedStatement pStatement= null;

	try {
		pStatement=con.prepareStatement(query);
		pStatement.setInt(1, id);
		result=pStatement.executeQuery();

		while(result.next()) {
			Annotazione annotazione= new Annotazione();
			annotazione.setAnnotazioneID(result.getInt("AnnotazioneID"));
			annotazione.setNote(result.getString("Note"));
			annotazione.setData(result.getDate("Data")); 
			annotazione.setImmagine(result.getInt("Immagine"));
			annotazione.setLavoratore(result.getInt("Lavoratore"));
			int ris=result.getInt("Fiducia");
			annotazione.setFiducia(Fiducia.fromInteger(ris));
			annotazione.setValidita(result.getBoolean("Validita"));
			lista.add(annotazione);
		}
	}

	catch (SQLException e) {
		e.printStackTrace();
		throw new SQLException(e);
	}

	finally {
		try {
			result.close();
		}catch(Exception e1) {throw new SQLException(e1);}

		try {
			pStatement.close();
		}catch(Exception e2) {throw new SQLException(e2);}
	}


	return lista;}

	public boolean aggiungiAnnotazione(int lavoratoreID, int immagineID,String fiduciaAnnotazione, String validitaAnnotazione, String note) throws SQLException {
	int fiducia=Integer.parseInt(fiduciaAnnotazione);
	int val=Integer.parseInt(validitaAnnotazione);
	boolean validita=false;
	if(val==0)
	{validita=false;}
	else if (val==1) {validita=true;}
	
	//controlla se esiste già un'annotazione
	boolean esiste=true;
	String y;

		y= "Select * from annotazione where Immagine=? and Lavoratore=?";
			
		ResultSet r=null;
	PreparedStatement S= null;

	try {
		S=con.prepareStatement(y);
		S.setInt(1, immagineID);
		S.setInt(2, lavoratoreID);
		r=S.executeQuery();
		esiste=r.next(); //falso se NON ci sono annotazioni che hanno quel lavoratore per quell'immagine.
	}

	catch (SQLException e) {
		e.printStackTrace();
		throw new SQLException(e);
	}

	finally {
		try {
			r.close();
		}catch(Exception e1) {throw new SQLException(e1);}

		try {
			S.close();
		}catch(Exception e2) {throw new SQLException(e2);}
	}
	
	
	if(!esiste) {
	
	//controlla se si crea un conflitto
	boolean test=true;
	String query;

		query= "Select * from annotazione where Immagine=? and Validita<>?";
			
		ResultSet res=null;
	PreparedStatement pStatement= null;

	try {
		pStatement=con.prepareStatement(query);
		pStatement.setInt(1, immagineID);
		pStatement.setBoolean(2, validita);
		res=pStatement.executeQuery();
		test=res.next(); //falso se NON ci sono annotazioni che creano conflitti.
	}

	catch (SQLException e) {
		e.printStackTrace();
		throw new SQLException(e);
	}

	finally {
		try {
			res.close();
		}catch(Exception e1) {throw new SQLException(e1);}

		try {
			pStatement.close();
		}catch(Exception e2) {throw new SQLException(e2);}
	}

	int result=0;

	
	
	//registra annotazione e validità in immagine
	


	
		String quer= "INSERT INTO annotazione(Data, Validita, Fiducia, Note, Immagine, Lavoratore) VALUES (curdate(), ?, ?, ?, ?, ?);";
		PreparedStatement pStat= null;

		try {
			pStat=con.prepareStatement(quer);
			pStat.setBoolean(1, validita);
			pStat.setInt(2, fiducia);
		    pStat.setString(3, note);
		    pStat.setInt(4, immagineID);
		    pStat.setInt(5, lavoratoreID);

			result=pStat.executeUpdate();

		}

		catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}

		finally {

			try {
				pStat.close();
			}catch(Exception e2) {throw new SQLException(e2);}
		}

int rst=0;
		String t= "UPDATE immagine SET Validita=? where ImmagineID=?";
		PreparedStatement pst= null;

		try {
			pst=con.prepareStatement(t);
			pst.setBoolean(1, validita);
			
		    pst.setInt(2, immagineID);
		    

			rst=pst.executeUpdate();

		}

		catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}

		finally {

			try {
				pst.close();
			}catch(Exception e2) {throw new SQLException(e2);}
		}
		
if (test)	{
	
	//aggiorna conflitto
	
	int aggiornaConflitto=0;
	String q;

		q= "Update immagine set Conflitto=1 where ImmagineID=?;";
			
	PreparedStatement p= null;

	try {
		
		p=con.prepareStatement(q);
	
		p.setInt(1, immagineID);
		aggiornaConflitto=p.executeUpdate();
	}

	catch (SQLException e) {
		e.printStackTrace();
		throw new SQLException(e);
	}

	finally {
	
		try {
			p.close();
		}catch(Exception e2) {throw new SQLException(e2);}

}
}	
	
	

if (result!=0 && rst!=0) {return true;}
}
return false;
	

}
}
