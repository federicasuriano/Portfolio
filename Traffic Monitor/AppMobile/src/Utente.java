import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

class Utente {
	private String email;
	private String psw;
	private String coordinateGPS;


	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	public String getPsw() {return psw;}
	public void setPsw(String psw) {this.psw = psw;}
	public String getCoordinateGPS() {return coordinateGPS;}
	public void setCoordinateGPS(String coordinateGPS) {
		//richieste al sistema gps del dispositivo
		this.coordinateGPS = coordinateGPS;}

	public void aggiornaGPS() throws MalformedURLException, RemoteException, NotBoundException, InstantiationException, IllegalAccessException, SQLException {
		ClientApp client=new ClientApp();
		String str=client.aggiornaGPS(this.coordinateGPS, this.email);
		if(!str.equals(" ")) {MostraNotifica.main(str);}
	}

	public void timer() {
		Timer timer = new Timer();
		timer.schedule(new TimedAggiornamento(this), 0, 5000);	
	}

	public Utente(String email, String psw, String coordinateGPS)
	{
		this.setCoordinateGPS(coordinateGPS);
		this.setEmail(email);
		this.setPsw(psw);
	
	}
	public void inviaSegnalazione(int livello) throws MalformedURLException, RemoteException, InstantiationException, IllegalAccessException, NotBoundException, SQLException, ParseException {
		// TODO Auto-generated method stub
		Date data=new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String inputString= "00:00:00.000";
		Date annoZero= sdf.parse("2018-12-16 " + inputString);
		System.out.println("In millisecondi: " + annoZero.getTime()); 

		long temp= data.getTime()-annoZero.getTime();
		Date dataNuova=new Date(temp);
		switch(livello) {
		case 1:ClientApp.inviaSegnalazione(this.coordinateGPS, dataNuova, LivelloDiTraffico.L1, email);
		case 2:ClientApp.inviaSegnalazione(this.coordinateGPS, dataNuova, LivelloDiTraffico.L2, email);
		case 3:ClientApp.inviaSegnalazione(this.coordinateGPS, dataNuova, LivelloDiTraffico.L3, email);
		case 4:ClientApp.inviaSegnalazione(this.coordinateGPS, dataNuova, LivelloDiTraffico.L4, email);
		case 5:ClientApp.inviaSegnalazione(this.coordinateGPS, dataNuova, LivelloDiTraffico.L5, email);

		}
	}
}
