import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class AmministratoreTest {

	@Test
	void testCheckDati() throws InstantiationException, IllegalAccessException, SQLException {
		//caso dati non esistenti (GPS sbagliato, range esatto): restituisce false
		String GPS="Non ci sono";
		int range=20;
		
		//assertFalse(Amministratore.checkDati(GPS, range));
		
		//caso dati non esistenti (GPS esatto, range sbagliato): restituisce false
		String GPS1="45.8119642 9.0854556";
		int range1=2;
		
       //assertFalse(Amministratore.checkDati(GPS1, range1));
		
		//caso dati esistenti (GPS esatto, range esatto): restituisce true
				String GPS2="45.8119642 9.0854556";
				int range2=20;
				
				assertTrue(Amministratore.checkDati(GPS2,range2));
		
	}
	
	@Test
	void testCreaDato() throws InstantiationException, IllegalAccessException, SQLException {
		//caso dati non esistenti (GPS sbagliato, range esatto): restituisce false
		String GPS="Non ci sono";
		int range=20;
		int livello=1;
		
assertTrue(Amministratore.creaDato(range, livello, GPS));
	}
	
	@Test
	void testMostraDati() throws FileNotFoundException, InstantiationException, IllegalAccessException, SQLException 
	{ ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
	 PrintStream ps=new PrintStream(baos);
	 PrintStream old = System.out;
	 
		System.setOut(ps);
		Amministratore.mostraDatiTraffico();
		
		assertFalse(ps.toString().equals("Non ci sono dati disponibili"));
	}

}
