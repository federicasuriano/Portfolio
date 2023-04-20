package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Lavoratore;
import beans.Manager;

public class UtenteDAO {

	private Connection con;

	public UtenteDAO(Connection connection) {
		this.con=connection;
	}

	public boolean registraManager(String username, String email, String password, boolean ruolo) throws SQLException{
		int result=0;
		if (this.checkUtente(username, email, ruolo)) {
			return false;
		} //errore, utente già nel database
		else {
			String query= "INSERT INTO manager(Nome, Email, Password) VALUES (?, ?, ?);";
			PreparedStatement pStatement= null;

			try {
				pStatement=con.prepareStatement(query);
				pStatement.setString(1, username);
				pStatement.setString(2, email);
				pStatement.setString(3, password);
				result=pStatement.executeUpdate();

			}

			catch (SQLException e) {
				e.printStackTrace();
				throw new SQLException(e);
			}

			finally {

				try {
					pStatement.close();
				}catch(Exception e2) {throw new SQLException(e2);}
			}
		}
		if (result==1) {return true;}
		return false;
	}

	public boolean registraLavoratore(String username, String email, String password, boolean ruolo) throws SQLException{
		int result=0;
		if (this.checkUtente(username, email, ruolo)) {
			return false;
		} //errore, utente già nel database
		else {
			String query= "INSERT INTO lavoratore(Nome, Email, Password, Livello) VALUES (?, ?, ?, 0);";
			PreparedStatement pStatement= null;

			try {
				pStatement=con.prepareStatement(query);
				pStatement.setString(1, username);
				pStatement.setString(2, email);
				pStatement.setString(3, password);
				result=pStatement.executeUpdate();

			}

			catch (SQLException e) {
				e.printStackTrace();
				throw new SQLException(e);
			}

			finally {

				try {
					pStatement.close();
				}catch(Exception e2) {throw new SQLException(e2);}
			}
		}
		if (result==1) {return true;}
		return false;
	}
	
	public boolean checkUtente(String username, String email, boolean ruolo) throws SQLException{

		boolean test=true;
		String query;

		//manager 
		if (ruolo){
			query= "Select ManagerID from manager where Nome=? or Email=?";
		}		

		else{
			query= "Select LavoratoreID from lavoratore where Nome=? or Email=?";
		}		

		ResultSet result=null;
		PreparedStatement pStatement= null;

		try {
			pStatement=con.prepareStatement(query);
			pStatement.setString(1, username);
			pStatement.setString(2, email);
			result=pStatement.executeQuery();
			test=result.next(); //falso se NON ci sono altri utenti con lo stesso nome o email.
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

		return test;
	}

	public Manager loginManager(String username, String password, boolean ruolo) throws SQLException{
		Manager manager=new Manager();
manager.setManagerID(0);
	
			String query= "Select * from manager where Nome=?";
			ResultSet result=null;
			PreparedStatement pStatement= null;

			try {
				pStatement=con.prepareStatement(query);
				pStatement.setString(1, username);
				result=pStatement.executeQuery();

				while(result.next()) {
					manager.setManagerID(result.getInt("ManagerID"));
					manager.setNome(result.getString("Nome"));
					manager.setEmail(result.getString("Email"));
					manager.setPassword(result.getString("Password"));	
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
			
		if (manager.getManagerID()!=0) {
			if (manager.getPassword().equals(password)) {return manager;}}
		
		return null;
	}
	
	public Lavoratore loginLavoratore(String username, String password, boolean ruolo) throws SQLException{
		Lavoratore lavoratore=new Lavoratore();
        lavoratore.setLavoratoreID(0);
	
			String query= "Select * from lavoratore where Nome=?";
			ResultSet result=null;
			PreparedStatement pStatement= null;

			try {
				pStatement=con.prepareStatement(query);
				pStatement.setString(1, username);
				result=pStatement.executeQuery();

				while(result.next()) {
					lavoratore.setLavoratoreID(result.getInt("LavoratoreID"));
					lavoratore.setNome(result.getString("Nome"));
					lavoratore.setEmail(result.getString("Email"));
					lavoratore.setPassword(result.getString("Password"));
					lavoratore.setEstensione(result.getString("Estensione"));
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
		
		if(lavoratore.getLavoratoreID()!=0) {if (lavoratore.getPassword().equals(password)) {return lavoratore;}}
		
		return null;
	}

	public boolean modificaProfiloManager(String username, String email, String password, int id) throws SQLException {
		
		int modificato=0;
		
//controllo il nuovo nome/email
		boolean test=true;
		String query;
			query= "Select ManagerID from manager where Nome=? AND ManagerID<>?";
		
			ResultSet result=null;
		PreparedStatement pStat= null;

		try {
			pStat=con.prepareStatement(query);
			pStat.setString(1, username);
			pStat.setInt(2, id);
			result=pStat.executeQuery();
			test=result.next(); //falso se non esiste qualcuno con lo stesso nome e id diverso
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
				pStat.close();
			}catch(Exception e2) {throw new SQLException(e2);}
		}
		
		

		if (test) {
			return false;
		} //errore, utente già nel database
		
		else {
			String q= "UPDATE manager SET Nome=? , Password=? WHERE ManagerID=?";
			PreparedStatement pStatement= null;

			try {
				pStatement=con.prepareStatement(q);
				pStatement.setString(1, username);
				pStatement.setString(2, password);
				pStatement.setInt(3, id);
				modificato=pStatement.executeUpdate();

			}

			catch (SQLException e) {
				e.printStackTrace();
				throw new SQLException(e);
			}

			finally {

				try {
					pStatement.close();
				}catch(Exception e2) {throw new SQLException(e2);}
			}
		}
		if (modificato==1) {return true;}
		return false;
		
	}

	public boolean modificaProfiloLavoratore(int id, String username, String password, String estensione) throws SQLException {
		
		
		int modificato=0;
		
//controllo il nuovo nome
		boolean test=true;
		String query;
			query= "Select LavoratoreID from lavoratore where Nome=? and LavoratoreID<>?";
		
			ResultSet result=null;
		PreparedStatement pStat= null;

		try {
			pStat=con.prepareStatement(query);
			pStat.setString(1, username);
			pStat.setInt(2, id);
			result=pStat.executeQuery();
			test=result.next(); //falso se non esiste qualcuno con lo stesso nome e id diverso
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
				pStat.close();
			}catch(Exception e2) {throw new SQLException(e2);}
		}
		
		

		if (test) {
			return false;
		} //errore, utente già nel database
		
		else {
			if (estensione.equals(" ")) {
				String q="Update lavoratore set Nome=?, Password=? where LavoratoreID=?";
				PreparedStatement pStatement= null;

				try {
					pStatement=con.prepareStatement(q);
					pStatement.setString(1, username);
					pStatement.setString(2, password);
					pStatement.setInt(3, id);
					modificato=pStatement.executeUpdate();

				}

				catch (SQLException e) {
					e.printStackTrace();
					throw new SQLException(e);
				}

				finally {

					try {
						pStatement.close();
					}catch(Exception e2) {throw new SQLException(e2);}
				}
			}
			
			else {	String q="Update lavoratore set Nome=?, Password=?, Estensione=? where LavoratoreID=?";
			PreparedStatement pStatement= null;

			try {
				pStatement=con.prepareStatement(q);
				pStatement.setString(1, username);
				pStatement.setString(2, password);
				pStatement.setString(3, estensione);
				pStatement.setInt(4, id);
				modificato=pStatement.executeUpdate();

			}

			catch (SQLException e) {
				e.printStackTrace();
				throw new SQLException(e);
			}

			finally {

				try {
					pStatement.close();
				}catch(Exception e2) {throw new SQLException(e2);}
			}}
		
		}
		
		if (modificato==1) {return true;}
		return false;
		
		
		
		
	}	
		
}