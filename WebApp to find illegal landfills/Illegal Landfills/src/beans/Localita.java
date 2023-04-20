package beans;

import java.util.List;

public class Localita {
private int localitaID;
private String latitudine;
private String longitudine;
private String nome;
private String comune;
private String regione;
private int campagna;
private List<Immagine> immagini;
public int getLocalitaID() {
	return localitaID;
}
public void setLocalitaID(int localitaID) {
	this.localitaID = localitaID;
}
public String getLatitudine() {
	return latitudine;
}
public void setLatitudine(String latitudine) {
	this.latitudine = latitudine;
}
public String getLongitudine() {
	return longitudine;
}
public void setLongitudine(String longitudine) {
	this.longitudine = longitudine;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getComune() {
	return comune;
}
public void setComune(String comune) {
	this.comune = comune;
}
public String getRegione() {
	return regione;
}
public void setRegione(String regione) {
	this.regione = regione;
}
public int getCampagna() {
	return campagna;
}
public void setCampagna(int campagna) {
	this.campagna = campagna;
}
public List<Immagine> getImmagini() {
	return immagini;
}
public void setImmagini(List<Immagine> immagini) {
	this.immagini = immagini;
}
}
