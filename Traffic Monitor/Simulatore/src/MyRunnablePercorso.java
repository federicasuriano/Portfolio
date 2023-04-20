import java.util.ArrayList;
import java.util.Timer;

public class MyRunnablePercorso implements Runnable{
	public int indice;
	public Utente u;
	public ArrayList<String> percorso;
	public MyRunnablePercorso(int i, Utente utente, ArrayList<String> per) {
		this.indice=i;
		this.u=utente; this.percorso=per;}

	public void run() {
		Timer timer = new Timer();
		timer.schedule(new TimedIndice(indice, u, percorso), 0, 7000);	
	}
}
