import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

class AutomobileTest extends junit.framework.TestCase{

	@Test
	void test() {
		Date data=new Date();
		double velocitàIstantanea=10.4;
		Automobile auto=new Automobile(data, velocitàIstantanea);
		assertEquals("Devo ottenere true ",(auto.getDistanzaDiSicurezza()),3.12);
		
	}

}
