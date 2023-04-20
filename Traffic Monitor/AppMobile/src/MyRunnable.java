public class MyRunnable implements Runnable{
	public Utente u;
	public void setUtente(Utente u){this.u=u;}
	public void run() {
		u.timer();
	}	
}