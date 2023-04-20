
import java.util.ArrayList;
import java.util.TimerTask;

public class TimedIndice extends TimerTask {
	int indice;
	public Utente u;
	public ArrayList<String> percorso;
	
	public TimedIndice(int i,Utente utente, ArrayList<String> per) {
		this.indice=i;
		this.u=utente; this.percorso=per;}

	public void run() {
		if(indice>0) {
		indice--;
		u.setCoordinateGPS(percorso.get(indice));
	}}
}