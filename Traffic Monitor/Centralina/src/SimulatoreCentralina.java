import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class SimulatoreCentralina {

	static final int velocitaLimite=50;
	static final int tempoMassimo=1200; //massimo numero di secondi tra un'auto e l'altra

	public static Automobile trafficoBasso (Date data) {

		double velocitaMinima=(velocitaLimite*0.60);
		///3600000;
		double velocitaMax= (velocitaLimite + velocitaLimite*0.25);
		///3600000;
		long tempoMax = (long) (tempoMassimo*1000); //20 min in millisecondi
		long tempoMin = (long) (tempoMassimo*0.6*1000); //in millisecondi

		long nuovoOrario=(long) (data.getTime()+Randomizzatore.randInt(tempoMin, tempoMax));
		Date nuovaData= new Date(nuovoOrario);

		double velocita=Randomizzatore.randInt(velocitaMinima, velocitaMax);

		Automobile auto = new Automobile(nuovaData, velocita);

		return auto;
	}

	public static Automobile trafficoMedio (Date data) {

		double velocitaMinima=(velocitaLimite*0.30);
		///3600000;
		double velocitaMax=(velocitaLimite*0.70);
		///3600000;

		long tempoMax = (long) 180*1000;
		//*1000;
		long tempoMin = (long) 30*1000;

		//il tempo che intercorre tra il passaggio di un'auto e un'altra
		long nuovoOrario=(long) (data.getTime()+Randomizzatore.randInt(tempoMin, tempoMax));
		Date nuovaData= new Date(nuovoOrario);
		double velocita=(Randomizzatore.randInt(velocitaMinima, velocitaMax));
		Automobile auto = new Automobile(nuovaData, velocita);
		return auto;
	}

	public static Automobile trafficoAlto (Date data) {

		double velocitaMinima=(velocitaLimite*0.01);
		///3600000;
		double velocitaMax=(velocitaLimite*0.40);
		///3600000;
		long tempoMax = (long) 30*1000;
		//*1000;
		long tempoMin = (long) 60;
		//*1000;


		//il tempo che intercorre tra il passaggio di un'auto e un'altra
		long nuovoOrario=(long) (data.getTime()+Randomizzatore.randInt(tempoMin, tempoMax));
		Date nuovaData= new Date(nuovoOrario);
		double velocita=(Randomizzatore.randInt(velocitaMinima, velocitaMax));
		Automobile auto = new Automobile(nuovaData, velocita);
		return auto;
	}


	public static void main (String[] args) throws MalformedURLException, RemoteException, NotBoundException, Exception {
		GestoreInvioCentralina gestore=new GestoreInvioCentralina();

		//setto Date perchè inizi a registrare i secondi da mezzanotte
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String inputString= "00:00:00.000";
		Date data= sdf.parse("1970-01-01 " + inputString);

		int t=0; //t contiene il numero di auto che passano

		while (t<10)
		{
			Automobile auto1= trafficoBasso(data);
			data=auto1.getData();
			Automobile auto2=trafficoBasso(data);
			data=auto2.getData();
			CalcolatoreTraffico calcolatore=new CalcolatoreTraffico(auto1, auto2, velocitaLimite);
			System.out.println(calcolatore.calcolaLivello());
			int x = calcolatore.calcolaLivello();
			gestore.invioDatiCentralina(x, data);
			System.out.println("Auto 1 " + auto1.getVelocitàIstantanea() + " " + auto1.getData().getTime() + " Auto2 " + 
					auto2.getVelocitàIstantanea() + " " + auto2.getData().getTime());
			t++;
		}

		t=0;

		while (t<10)
		{
			Automobile auto1= trafficoMedio(data);
			data=auto1.getData();
			Automobile auto2=trafficoMedio(data);
			data=auto2.getData();
			CalcolatoreTraffico calcolatore=new CalcolatoreTraffico(auto1, auto2, velocitaLimite);
			System.out.println(calcolatore.calcolaLivello());
			int x = calcolatore.calcolaLivello();
			gestore.invioDatiCentralina(x, data);
			System.out.println("Auto 1 " + auto1.getVelocitàIstantanea() + " " + auto1.getData().getTime() + " Auto2 " + 
					auto2.getVelocitàIstantanea() + " " + auto2.getData().getTime());
			t++;
		}
		t=0;

		while (t<10)
		{
			Automobile auto1= trafficoAlto(data);
			data=auto1.getData();
			Automobile auto2=trafficoAlto(data);
			data=auto2.getData();
			CalcolatoreTraffico calcolatore=new CalcolatoreTraffico(auto1, auto2, velocitaLimite);
			System.out.println(calcolatore.calcolaLivello());
			int x = calcolatore.calcolaLivello();
			gestore.invioDatiCentralina(x, data);
			System.out.println("Auto 1 " + auto1.getVelocitàIstantanea() + " " + auto1.getData().getTime() + " Auto2 " + 
					auto2.getVelocitàIstantanea() + " " + auto2.getData().getTime());
			t++;
		}
	}
}