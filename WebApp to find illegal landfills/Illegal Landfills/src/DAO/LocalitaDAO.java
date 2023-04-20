package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Campagna;
import beans.Immagine;
import beans.Localita;

public class LocalitaDAO {

	private Connection con;

	public LocalitaDAO(Connection con) {
		this.con=con;
	}
	public int getTotale(int campagnaID) throws SQLException {
		int numero=0;
		String query= "Select count(*) as tot from localita where Campagna=?;";
		PreparedStatement pStatement=null;
		ResultSet result=null;
		try {
			pStatement=con.prepareStatement(query);
			pStatement.setInt(1, campagnaID);
			result=pStatement.executeQuery();
			while(result.next()) {
				numero=result.getInt("tot");
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

		return numero;

	}

	public List<Localita> getListaLocalita(int campagnaID) throws SQLException {
		List<Localita> lista= new ArrayList();
		String query= "Select * from localita where Campagna=?;";
		ResultSet result=null;
		PreparedStatement pStatement= null;


		try {
			pStatement=con.prepareStatement(query);
			pStatement.setInt(1, campagnaID);
			result=pStatement.executeQuery();

			while(result.next()) {
				Localita localita= new Localita();
				localita.setLocalitaID(result.getInt("LocalitaID"));
				localita.setNome(result.getString("Nome"));
				localita.setLatitudine(result.getString("Latitudine")); 
				localita.setLongitudine(result.getString("Longitudine"));
				localita.setRegione(result.getString("Regione"));
				localita.setComune(result.getString("Comune"));
				localita.setCampagna(result.getInt("Campagna"));			
				lista.add(localita);
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


		return lista;
	}

	public int creaLocalita(String lat, String longi, String nome, String comune, String regione, int campagnaID) throws SQLException {
		boolean test=true;
		int id=0;
		double latitudine=Double.parseDouble(lat);
		double longitudine=Double.parseDouble(longi);
		latitudine=latitudine * 100;
		longitudine=longitudine* 100;
		

		String query;

		query= "Select * from localita where (Nome=? and Campagna=?) or (Longitudine=? and Latitudine=? and Campagna=?)";

		ResultSet res=null;
		PreparedStatement pStatement= null;

		try {
			pStatement=con.prepareStatement(query);
			pStatement.setString(1, nome);
			pStatement.setInt(2, campagnaID);
			pStatement.setString(3, String.valueOf(latitudine));
			pStatement.setString(4, String.valueOf(longitudine));
			pStatement.setInt(5, campagnaID);
			res=pStatement.executeQuery();
			test=res.next(); //falso se NON ci sono altre località uguali
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

		if (!test) {
			//registra la località


			int result=0;
			ResultSet r=null;
			String quer= "INSERT INTO localita(Latitudine, Longitudine, Nome, Comune, Regione, Campagna) VALUES (?, ?, ?, ?, ?, ?);";
			PreparedStatement pStat= null;

			try {
				pStat=con.prepareStatement(quer, Statement.RETURN_GENERATED_KEYS);
				pStat.setString(1, String.valueOf(latitudine));
				pStat.setString(2, String.valueOf(longitudine));
				pStat.setString(3, nome);
				pStat.setString(4, comune);
				pStat.setString(5, regione);
				pStat.setInt(6, campagnaID);

				result=pStat.executeUpdate();
				r=pStat.getGeneratedKeys();
				while (r.next()) {
					id=r.getInt(1);
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
			//scopri l'id della campagna

		}



		return id;



	}
}
