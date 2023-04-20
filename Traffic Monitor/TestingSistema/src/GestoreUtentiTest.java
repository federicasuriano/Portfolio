import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class GestoreUtentiTest {

	@Test
	void testRegistrazioneELogin() throws InstantiationException, IllegalAccessException, SQLException {
		String email = "utentetest@mail.com";
		String psw = "utentetest";
		
		assertTrue(GestoreUtenti.RegistrazioneApp(email, psw));
		
		assertTrue(GestoreUtenti.loginApp(email, psw));
	}

}
