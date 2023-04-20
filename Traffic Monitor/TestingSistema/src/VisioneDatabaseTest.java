import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class VisioneDatabaseTest {

	@Test
	void testMostraDati() throws FileNotFoundException, InstantiationException, IllegalAccessException, SQLException 
	{ ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
	 PrintStream ps=new PrintStream(baos);
	 PrintStream old = System.out;
	 
		System.setOut(ps);
		VisioneDatabase.mostraDatiTraffico();
		
		assertFalse(ps.toString().equals("Non ci sono dati disponibili"));
	}

}
