import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.junit.jupiter.api.Test;

class CalcolatoreTrafficoTest extends junit.framework.TestCase{


	@Test
	void test() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String inputString= "00:00:00.000";
		Date data= sdf.parse("1970-01-01 " + inputString);
		
		/*caso in cui restituisce 0
        Automobile auto1=new Automobile(data, 40.0);
    	long secondi=data.getTime()+5000;
        Automobile auto2=new Automobile(data1, 40.0);*/
		
		//caso in cui restituisce 4
		Automobile auto1=new Automobile(data, 10.0);
		long secondi=data.getTime()+720;
    	Date data1=new Date(secondi);
    	Automobile auto2=new Automobile(data1, 10.0);
    
    	CalcolatoreTraffico calcolatore1=new CalcolatoreTraffico(auto1, auto2, 50);
    	
    	//caso 0
		//assertTrue(calcolatore1.controllaDistanza()==0);
    	
    	//caso 4
    	assertTrue(calcolatore1.controllaDistanza()==4);
	}

}
