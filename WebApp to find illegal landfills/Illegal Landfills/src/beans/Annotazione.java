package beans;

import java.sql.Date;

import enm.Fiducia;

public class Annotazione {
private int annotazioneID;
private Date data;
private boolean validita;
private Fiducia fiducia;
private String note;
private int lavoratore;
private int immagine;
public int getAnnotazioneID() {
	return annotazioneID;
}
public void setAnnotazioneID(int annotazioneID) {
	this.annotazioneID = annotazioneID;
}
public Date getData() {
	return data;
}
public void setData(Date data) {
	this.data = data;
}
public boolean isValidita() {
	return validita;
}
public void setValidita(boolean validita) {
	this.validita = validita;
}
public Fiducia getFiducia() {
	return fiducia;
}
public void setFiducia(Fiducia fiducia) {
	this.fiducia = fiducia;
}
public int getLavoratore() {
	return lavoratore;
}
public void setLavoratore(int lavoratore) {
	this.lavoratore = lavoratore;
}
public String getNote() {
	return note;
}
public void setNote(String note) {
	this.note = note;
}
public int getImmagine() {
	return immagine;
}
public void setImmagine(int immagine) {
	this.immagine = immagine;
}
}
