import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SimulatoreApp {
	public static ArrayList<Nodo> coordinate=new ArrayList<Nodo>();
	public String nome;
	public String email;
	public String psw;
	public static ArrayList<String> percorso=new ArrayList<String>();
	public static int indice;

	public static void main(String[] args) throws MalformedURLException, RemoteException, InstantiationException, IllegalAccessException, NotBoundException, SQLException {

		Nodo n1=new Nodo("45.804790 9.090658", 1); coordinate.add(n1);
		Nodo n2=new Nodo("45.803354 9.091044", 2); coordinate.add(n2);
		Nodo n3=new Nodo("45.802547 9.091384", 3);coordinate.add(n3);
		Nodo n4=new Nodo("45.801868 9.091775", 4);coordinate.add(n4);
		Nodo n5=new Nodo("45.801227 9.092273", 5);coordinate.add(n5);
		Nodo n6=new Nodo("45.801745 9.093276", 6);coordinate.add(n6);
		Nodo n7=new Nodo("45.802221 9.094150", 7);coordinate.add(n7);
		Nodo n8=new Nodo("45.801227 9.095169", 8);coordinate.add(n8);
		Nodo n9=new Nodo("45.802530 9.093835", 9);coordinate.add(n9);
		Nodo n10=new Nodo("45.802791 9.093528", 10);coordinate.add(n10);
		Nodo n11=new Nodo("45.803609 9.095153", 11);coordinate.add(n11);
		Nodo n12=new Nodo("45.804346 9.096899", 12);coordinate.add(n12);
		Nodo n13=new Nodo("45.803822 9.094869", 13);coordinate.add(n13);
		Nodo n14=new Nodo("45.803123 9.093689", 14);coordinate.add(n14);
		Nodo n15=new Nodo("45.801638 9.090364", 15);coordinate.add(n15);
		Nodo n16=new Nodo("45.802690 9.089889", 16);coordinate.add(n16);
		Nodo n17=new Nodo("45.804511 9.089069", 17);coordinate.add(n17);
		Nodo n18=new Nodo("45.803448 9.089513", 18);coordinate.add(n18);
		Nodo n19=new Nodo("45.804148 9.087422", 19);coordinate.add(n19);
		Nodo n20=new Nodo("45.803886 9.086180", 20);coordinate.add(n20);
		Nodo n21=new Nodo("45.803048 9.086349", 21);coordinate.add(n21);
		Nodo n22=new Nodo("45.804752 9.085843", 22);coordinate.add(n22);
		Nodo n23=new Nodo("45.802613 9.086368", 23);coordinate.add(n23);
		Nodo n24=new Nodo("45.801686 9.088514", 24);coordinate.add(n24);
		Nodo n25=new Nodo("45.801207 9.089072", 25);coordinate.add(n25);
		Nodo n26=new Nodo("45.802209 9.084545", 26);coordinate.add(n26);
		Nodo n27=new Nodo("45.802897 9.083429", 27);coordinate.add(n27);
		Nodo n28=new Nodo("45.803959 9.090810", 28);coordinate.add(n28);
		Nodo n29=new Nodo("45.804498 9.092312", 29);coordinate.add(n29);
		Nodo n30=new Nodo("45.805141 9.093579", 30);coordinate.add(n30);
		Nodo n31=new Nodo("45.805051 9.095338", 31);coordinate.add(n31);
		Nodo n32=new Nodo("45.805620 9.089823", 32);coordinate.add(n32);
		Nodo n33=new Nodo("45.805605 9.088557", 33);coordinate.add(n33);
		Nodo n34=new Nodo("45.804857 9.084373", 34);coordinate.add(n34);

		//ArrayList<Nodo> array=new ArrayList<Nodo>();
		n1.getFigli().add(n28); n1.getFigli().add(n17); n1.getFigli().add(n32); 
		n2.getFigli().add(n28); n2.getFigli().add(n16); n2.getFigli().add(n3); 
		n3.getFigli().add(n15); n3.getFigli().add(n2);  
		n4.getFigli().add(n10); n4.getFigli().add(n3); 
		n5.getFigli().add(n4); n5.getFigli().add(n6); 
		n6.getFigli().add(n5); n6.getFigli().add(n7);  
		n7.getFigli().add(n6); n7.getFigli().add(n8); n7.getFigli().add(n9); 
		n8.getFigli().add(n7);
		n9.getFigli().add(n10); n9.getFigli().add(n7); 
		n10.getFigli().add(n9); n10.getFigli().add(n11); 
		n11.getFigli().add(n12); n11.getFigli().add(n13);
		n12.getFigli().add(n13);
		n13.getFigli().add(n29); n13.getFigli().add(n14);
		n14.getFigli().add(n3); n14.getFigli().add(n15);
		n15.getFigli().add(n24);
		n16.getFigli().add(n15); n16.getFigli().add(n23);
		n17.getFigli().add(n19); n17.getFigli().add(n18); n17.getFigli().add(n1);
		n18.getFigli().add(n21); n18.getFigli().add(n16);
		n19.getFigli().add(n20); n19.getFigli().add(n17); 
		n20.getFigli().add(n22); n20.getFigli().add(n19);
		n21.getFigli().add(n20);
		n22.getFigli().add(n34);
		n23.getFigli().add(n21);
		n24.getFigli().add(n23); n24.getFigli().add(n26);
		n25.getFigli().add(n24); n25.getFigli().add(n4);
		n26.getFigli().add(n25); n26.getFigli().add(n27);
		n27.getFigli().add(n26);
		n28.getFigli().add(n1);
		n29.getFigli().add(n30); 
		n30.getFigli().add(n31);
		n31.getFigli().add(n13);
		n32.getFigli().add(n29); n32.getFigli().add(n17); 
		n33.getFigli().add(n17);
		n34.getFigli().add(n21); n34.getFigli().add(n27);

		inizioPercorso();

	}

	public static void inizioPercorso() throws MalformedURLException, RemoteException, InstantiationException, IllegalAccessException, NotBoundException, SQLException
	{
		//simuliamo il login: l'utente è già entrato e quindi si inizializza l'oggetto utente con le sue informazioni
		String email="simulatore@mail.com";
		String psw="simulatore";
		int x=(int) Randomizzatore.randInt(0, 33);
		String gps=coordinate.get(x).getCoordinate();
      Utente utente=new Utente(email, psw, gps);
      
      int durata=(int) Randomizzatore.randInt(3,50);
      
     generaPercorso(coordinate.get(x), durata);
      
      
      System.out.println(percorso);
      
     indice=percorso.size()-1;
    
     MyRunnablePercorso task=new MyRunnablePercorso(indice, utente, percorso);
     Thread myThread=new Thread(task);
     
     MyRunnableSimulatore task1=new MyRunnableSimulatore();
     task1.setUtente(utente);
     Thread myThread1= new Thread(task1);
     
     myThread.start();
     myThread1.start();
   
	}


	

	public static void generaPercorso(
			Nodo n, int durata) throws MalformedURLException, RemoteException, InstantiationException, IllegalAccessException, NotBoundException, SQLException
	{
		if(durata>0) {
			percorso.add(n.getCoordinate());
			int f=(int) Randomizzatore.randInt(0, n.getFigli().size()-1);
			Nodo next=n.getFigli().get(f);
			durata--;
			generaPercorso(next, durata);
		}
	}
}

