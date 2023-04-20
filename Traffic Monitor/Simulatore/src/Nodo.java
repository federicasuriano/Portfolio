import java.util.ArrayList;

public class Nodo {
	private String coordinate;
	private int indice;
	private ArrayList<Nodo> figli=new ArrayList<Nodo>(2);


	public Nodo(String gps, int indice) {
		this.setCoordinate(gps);
		this.setIndice(indice);

	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public int getIndice() {
		return indice;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}
	public ArrayList<Nodo> getFigli() {
		return figli;
	}
	public void setFigli(ArrayList<Nodo> figli) {
		this.figli = figli;
	}
}
