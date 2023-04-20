package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Campagna;
import enm.Stato;

public class CampagnaDAO {
	private Connection con;
	
public CampagnaDAO(Connection con) {
	this.con=con;
}
public List<Campagna> listaCampagneManager(int managerID) throws SQLException{
	
	List<Campagna> lista= new ArrayList();

	
	String query= "Select * from campagna where Manager=?;";
	ResultSet result=null;
	PreparedStatement pStatement= null;

	try {
		pStatement=con.prepareStatement(query);
		pStatement.setInt(1, managerID);
		result=pStatement.executeQuery();

		while(result.next()) {
			Campagna campagna= new Campagna();
			campagna.setCampagnaID(result.getInt("CampagnaID"));
			campagna.setNome(result.getString("Nome"));
			campagna.setCommittente(result.getString("Committente")); 
			int stat= result.getInt("stato");		
			campagna.setStato(Stato.fromInteger(stat));
			campagna.setManager(result.getInt("Manager"));
			lista.add(campagna);
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

public int registraCampagna(String nome, String committente, int manager) throws SQLException {
	//controlla se esiste già il nome e il committente
	boolean test=true;
	int id=0;
	String query;

		query= "Select Nome from campagna where Nome=? and Committente=?";
			
		ResultSet res=null;
	PreparedStatement pStatement= null;

	try {
		pStatement=con.prepareStatement(query);
		pStatement.setString(1, nome);
		pStatement.setString(2, committente);
		res=pStatement.executeQuery();
		test=res.next(); //falso se NON ci sono altri utenti con lo stesso nome e committente.
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
	//registra la campagna
	

	int result=0;
	
		String quer= "INSERT INTO campagna(Nome, Committente, Stato, Manager) VALUES (?, ?, 0, ?);";
		PreparedStatement pStat= null;

		try {
			pStat=con.prepareStatement(quer);
			pStat.setString(1, nome);
			pStat.setString(2, committente);
		    pStat.setInt(3, manager);
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
		//scopri l'id della campagna
		
		if (result==1) //inserimento riuscito
		{String q= "SELECT CampagnaID from campagna where Nome=? and Committente=?;";
		PreparedStatement p= null;
        ResultSet r=null;
		try {
			p=con.prepareStatement(q);
			p.setString(1, nome);
			p.setString(2, committente);
		
			r=p.executeQuery();
			while(r.next()) {
			id=r.getInt("CampagnaID");
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
		}}
	
		
		}

return id;
}

public boolean avviaCampagna(int id) throws SQLException {
	int avviata=0;
	String query;

		query= "Update campagna set Stato=1 where CampagnaID=?;";
			
	PreparedStatement pStatement= null;

	try {
		
		pStatement=con.prepareStatement(query);
		pStatement.setInt(1, id);
		avviata=pStatement.executeUpdate();
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
if (avviata!=0) {
	return true;
}
return false;
}
public boolean chiudiCampagna(int id) throws SQLException {
	int chiusa=0;
	String query;

		query= "Update campagna set Stato=2 where CampagnaID=?;";
			
	PreparedStatement pStatement= null;

	try {
		pStatement=con.prepareStatement(query);
		pStatement.setInt(1, id);
		chiusa=pStatement.executeUpdate();
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
if (chiusa!=0) {
	return true;
}
return false;
}
public List<Campagna> campagneAvviate(int lavoratoreID) throws SQLException {
	List<Campagna> lista= new ArrayList();


String query= "select * from campagna where stato=1 and CampagnaID not in (Select CampagnaID from campagna join listacampagne on CampagnaID=Campagna where Lavoratore=? )";
ResultSet result=null;
PreparedStatement pStatement= null;

try {
	pStatement=con.prepareStatement(query);
	pStatement.setInt(1, lavoratoreID);
	result=pStatement.executeQuery();

	while(result.next()) {
		Campagna campagna= new Campagna();
		campagna.setCampagnaID(result.getInt("CampagnaID"));
		campagna.setNome(result.getString("Nome"));
		campagna.setCommittente(result.getString("Committente")); 
		int stat= result.getInt("stato");		
		campagna.setStato(Stato.fromInteger(stat));
		campagna.setManager(result.getInt("Manager"));
		lista.add(campagna);
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
public List<Campagna> campagneScelte(int lavoratoreID) throws SQLException {
	List<Campagna> lista= new ArrayList();


	String query= "select * from campagna join listacampagne on CampagnaID=Campagna where Lavoratore=? and stato=1;";
	ResultSet result=null;
	PreparedStatement pStatement= null;

	try {
		pStatement=con.prepareStatement(query);
		pStatement.setInt(1, lavoratoreID);
		result=pStatement.executeQuery();

		while(result.next()) {
			Campagna campagna= new Campagna();
			campagna.setCampagnaID(result.getInt("CampagnaID"));
			campagna.setNome(result.getString("Nome"));
			campagna.setCommittente(result.getString("Committente")); 
			int stat= result.getInt("stato");		
			campagna.setStato(Stato.fromInteger(stat));
			campagna.setManager(result.getInt("Manager"));
			lista.add(campagna);
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

public boolean scegliCampagna(int campagna, int lavoratore) throws SQLException
{
	int modificato=0;
	

		String q= "INSERT into listacampagne(Campagna, Lavoratore) VALUES(?,?)";
		PreparedStatement pStatement= null;

		try {
			pStatement=con.prepareStatement(q);
			pStatement.setInt(1, campagna);
			pStatement.setInt(2, lavoratore);
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
	
	if (modificato==1) {return true;}
	return false;
	

	}
}



