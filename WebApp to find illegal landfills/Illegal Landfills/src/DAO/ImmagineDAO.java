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

import beans.Campagna;
import beans.Immagine;
import enm.Risoluzione;

public class ImmagineDAO {

	private Connection con;

	public ImmagineDAO(Connection con) {
		this.con=con;
	}

	public List<Immagine> getListaImmagini(int id) throws SQLException
	{	List<Immagine> lista= new ArrayList();


	String query= "Select * from immagine where Localita=?;";
	ResultSet result=null;
	PreparedStatement pStatement= null;

	try {
		pStatement=con.prepareStatement(query);
		pStatement.setInt(1, id);
		result=pStatement.executeQuery();

		while(result.next()) {
			Immagine immagine= new Immagine();
			immagine.setImmagineID(result.getInt("ImmagineID"));
			immagine.setEstensione(result.getString("Estensione"));
			immagine.setData(result.getDate("Data")); 
			immagine.setLocalita(result.getInt("Localita"));
			immagine.setConflitto(result.getBoolean("Conflitto"));
			immagine.setValidita(result.getBoolean("Validita"));
			int ris=result.getInt("Risoluzione");
			immagine.setRisoluzione(Risoluzione.fromInteger(ris));
			immagine.setProvenienza(result.getString("Provenienza"));
			lista.add(immagine);
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

	public int salvaImmagine(String provenienza, String dataDiRecupero, String ris, int localita, String estensione) throws SQLException
	{  int immagineID=0;

	int risoluzione;
	//trasformo risoluzione
	if (ris.equals("2")) {risoluzione=2;}
	else if (ris.equals("1")) {risoluzione=1;}
	else {risoluzione=0;}

	//trasformo data
	java.util.Date utilDate;
	java.sql.Date sqlDate= new Date(0);
	try {
		utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dataDiRecupero); //"yyyy MMM dd"
		sqlDate = new java.sql.Date(utilDate.getTime());
	} catch (ParseException e1) {
		e1.printStackTrace();
	}

	String quer= "INSERT INTO immagine(Data, Risoluzione, Provenienza, Localita, Estensione) VALUES (?, ?, ?, ?, ?);";
	PreparedStatement pStat= null;

	int result;
	ResultSet rs=null;
	try {
		pStat=con.prepareStatement(quer, Statement.RETURN_GENERATED_KEYS);
		pStat.setDate(1, sqlDate);
		pStat.setInt(2, risoluzione);
		pStat.setString(3, provenienza);
		pStat.setInt(4, localita);
		pStat.setString(5, estensione);


		result=pStat.executeUpdate();
		rs=pStat.getGeneratedKeys();
		while (rs.next()) {
			immagineID=rs.getInt(1);
		}
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


	return immagineID;
	}
	
	public boolean aggiornaConflitto(int id, boolean val) throws SQLException
	{

		String query;
			query= "Update immagine set Conflitto=?, Validita=? where ImmagineID=?";
		
int result;
		PreparedStatement pStat= null;

		try {
			pStat=con.prepareStatement(query);
			pStat.setBoolean(1, false);
			pStat.setBoolean(2, val);
			pStat.setInt(3, id);
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
		
		if (result>0)  {return true;}
		return false;
	}
	
		
}
