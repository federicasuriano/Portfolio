import java.util.Date;

class Automobile {

	private Date data;
	private double velocitàIstantanea; //in chilometri orari

	public Automobile(Date data, double velocitàIstantanea)
	{
		this.setData(data);
		this.velocitàIstantanea = velocitàIstantanea;
	}

	public double getVelocitàIstantanea() {return velocitàIstantanea;}
	public void setVelocitàIstantanea(double velocità) {this.velocitàIstantanea=velocità;}

	public Date getData() {return this.data;}

	private void setData(Date data) {this.data = data;} //la data va settata solo in inizializzazione

	public double getDistanzaDiSicurezza() {return ((this.velocitàIstantanea*3)/10);}} //viene restituita in metri
