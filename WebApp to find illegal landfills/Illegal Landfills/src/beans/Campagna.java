package beans;

import enm.Stato;

public class Campagna {
    private int campagnaID;
   private String nome;
    private String committente;
private Stato stato;
private int manager;

public int getCampagnaID() {
	return campagnaID;
}
public void setCampagnaID(int campagnaID) {
	this.campagnaID = campagnaID;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getCommittente() {
	return committente;
}
public void setCommittente(String committente) {
	this.committente = committente;
}
public Stato getStato() {
	return stato;
}
public void setStato(Stato stato) {
this.stato=stato;
}
public int getManager() {
	return manager;
}
public void setManager(int manager) {
	this.manager = manager;
}

}
