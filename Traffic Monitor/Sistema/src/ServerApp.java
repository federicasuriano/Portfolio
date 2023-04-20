
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class ServerApp {
	public static void main(String[] args) throws RemoteException {
		Registry registry = LocateRegistry.createRegistry(5099);
		registry.rebind("invioapp", new InterfacciaRicezioneApp());
	}
}
