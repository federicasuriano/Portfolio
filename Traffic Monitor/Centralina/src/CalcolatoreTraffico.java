
public class CalcolatoreTraffico {
	private Automobile auto1;
	private Automobile auto2;
	private int limiteDiVelocita; //in km/h

	CalcolatoreTraffico(Automobile  a1, Automobile a2, int limite){
		setAuto1(a1);
		setAuto2(a2);
		setLimiteDiVelocita(limite);
	}

	public Automobile  getAuto1 () {return this.auto1;}
	public Automobile  getAuto2 () {return this.auto2;}
	public int getLimiteDiVelocita () {return this.limiteDiVelocita;}
	public void setAuto1(Automobile  auto1) {this.auto1=auto1;}
	public void setAuto2(Automobile  auto2) {this.auto2=auto2;}
	public void setLimiteDiVelocita (int lim) {this.limiteDiVelocita=lim;}

	public double getMedia() {
		double media=(this.getAuto1().getVelocitàIstantanea()+this.getAuto2().getVelocitàIstantanea())/2;
		return media;}

	public long getDifferenzaRilevazione() {

		long tempoAuto1= this.getAuto1().getData().getTime();
		long tempoAuto2= this.getAuto2().getData().getTime();

		return (tempoAuto2-tempoAuto1);

	}

	public double getDistanza() {
		//restituisce la distanza in metri
		return ((this.getAuto1().getVelocitàIstantanea()*this.getDifferenzaRilevazione())/3600);
	}

	public int controllaDistanza() {
		if (this.getDistanza()>this.getAuto2().getDistanzaDiSicurezza())
		{System.out.println("La distanza tra le due auto è maggiore della distanza di sicurezza consigliata.");
		return 0;}
		else
		{return calcolaLivello();}
	}

	public int calcolaLivello() {
		double media= this.getMedia();

		if (media>0 && media<=this.getLimiteDiVelocita()*0.2)
		{
			if (this.getDistanza()>this.getAuto2().getDistanzaDiSicurezza()/2) {return 4;}
			else {return 5;}
		}

		else if (media>this.getLimiteDiVelocita()*0.2 && media<=this.getLimiteDiVelocita()*0.4)
		{
			if (this.getDistanza()>this.getAuto2().getDistanzaDiSicurezza()/2) {return 3;}
			else {return 4;}
		}

		else if (media>this.getLimiteDiVelocita()*0.4 && media<=this.getLimiteDiVelocita()*0.6) 
		{
			if (this.getDistanza()>this.getAuto2().getDistanzaDiSicurezza()/2) {return 2;}
			else {return 3;}
		}

		else if (media>this.getLimiteDiVelocita()*0.6 && media<=this.getLimiteDiVelocita()*0.8)
		{if (this.getDistanza()>this.getAuto2().getDistanzaDiSicurezza()/2) {return 1;}
		else {return 2;}
		}

		else if (media>this.getLimiteDiVelocita()*0.8) 
		{
			if (this.getDistanza()>this.getAuto2().getDistanzaDiSicurezza()/2) {return 1;}
			else {return 0;}
		}

		return -1;
	}

}
