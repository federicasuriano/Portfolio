import java.util.ArrayList;

public class MyRunnableSimulatore implements Runnable {
	public Utente u;
	public ArrayList<String> percorso;
	public void setUtente(Utente u) {this.u=u;}

	@Override
	public void run() {

		u.timer();
	}

}
