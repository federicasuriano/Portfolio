package beans;

import java.sql.Date;
import java.util.List;

import enm.Risoluzione;

public class Immagine {
private int immagineID;
private Date data;
private Risoluzione risoluzione;
private String provenienza;
private int localita;
private String estensione;
private List<Annotazione> annotazioni;
private boolean conflitto;
private boolean validita;

public Date getData() {
	return data;
}
public void setData(Date data) {
	this.data = data;
}
public int getImmagineID() {
	return immagineID;
}
public void setImmagineID(int immagineID) {
	this.immagineID = immagineID;
}
public Risoluzione getRisoluzione() {
	return risoluzione;
}
public void setRisoluzione(Risoluzione risoluzione) {
	this.risoluzione = risoluzione;
}
public String getProvenienza() {
	return provenienza;
}
public void setProvenienza(String provenienza) {
	this.provenienza = provenienza;
}
public int getLocalita() {
	return localita;
}
public void setLocalita(int localita) {
	this.localita = localita;
}
public String getEstensione() {
	return estensione;
}
public void setEstensione(String estensione) {
	this.estensione = estensione;
}
public List<Annotazione> getAnnotazioni() {
	return annotazioni;
}
public void setAnnotazioni(List<Annotazione> annotazioni) {
	this.annotazioni = annotazioni;
}
public boolean isConflitto() {
	return conflitto;
}
public void setConflitto(boolean conflitto) {
	this.conflitto = conflitto;
}
public boolean isValidita() {
	return validita;
}
public void setValidita(boolean validita) {
	this.validita = validita;
}

}
