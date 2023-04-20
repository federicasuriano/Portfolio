
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.sql.SQLException;

public class GestoreInvioCentralina {
	private int conteggioAuto=0;
	private int livelloPrecedente=0;
	private String coordinateGPS= "45.8119642 9.0854556";

	//da cambiare (è in numero di auto)
	private int periodo=10;

	//chiamato dal CalcolatoreTraffico
	public void invioDatiCentralina (int livelloAttuale, Date data) throws MalformedURLException, RemoteException, NotBoundException, SQLException, InstantiationException, IllegalAccessException
	{
		conteggioAuto=conteggioAuto+2;

		if (livelloAttuale>livelloPrecedente) {
			periodo=periodo/((livelloAttuale-livelloPrecedente)+1); System.out.println("ho diviso il periodo:"+ periodo);
		}
		else if (livelloAttuale<livelloPrecedente)
		{
			periodo=periodo*((livelloPrecedente-livelloAttuale)+1); System.out.println("ho moltiplicato il periodo:" + periodo);
		}

		if (conteggioAuto>=periodo) {

			//manda informazione centralina


			if (livelloAttuale==0) {

				ClientCentralina client=new ClientCentralina();

				client.inviaInformazione(coordinateGPS, data, LivelloDiTraffico.L0);
			}

			else if (livelloAttuale==1) {

				ClientCentralina client=new ClientCentralina();

				client.inviaInformazione(coordinateGPS, data, LivelloDiTraffico.L1);

			}
			else if (livelloAttuale==2) {

				ClientCentralina client=new ClientCentralina();
				client.inviaInformazione(coordinateGPS, data, LivelloDiTraffico.L2);
			}
			else if (livelloAttuale==3) {
				ClientCentralina client=new ClientCentralina();
				client.inviaInformazione(coordinateGPS, data, LivelloDiTraffico.L3);
			}
			else if (livelloAttuale==4) {
				ClientCentralina client=new ClientCentralina();
				client.inviaInformazione(coordinateGPS, data, LivelloDiTraffico.L4);
			}
			else if (livelloAttuale==5) {

				ClientCentralina client=new ClientCentralina();
				client.inviaInformazione(coordinateGPS, data, LivelloDiTraffico.L5);
			}


			//resetta conteggio auto
			conteggioAuto=0;
		}

		livelloPrecedente=livelloAttuale;

	}
}
