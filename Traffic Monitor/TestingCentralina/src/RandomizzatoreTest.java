import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RandomizzatoreTest {

	@Test
	void test() {
		double b=10.7;
		double a=4.5;
		
		assertTrue(Randomizzatore.randInt(a, b)>=a && Randomizzatore.randInt(a, b)<=b);
		
	}

}
