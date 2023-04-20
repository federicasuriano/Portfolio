package beans;

import java.util.List;

import enm.LivelloLavoratore;

public class Lavoratore {
	private int lavoratoreID;
	private String nome;
	private String email;
	private String password;
	private LivelloLavoratore livello;
	private String estensione;
	//private List<Campagna> listaCampagne;
	//private List<Localita> listaLocalita;


	public int getLavoratoreID() {
		return lavoratoreID;
	}
	public void setLavoratoreID(int lavoratoreID) {
		this.lavoratoreID = lavoratoreID;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LivelloLavoratore getLivello() {
		return livello;
	}
	public void setLivello(LivelloLavoratore livello) {
		this.livello = livello;
	}
	public String getEstensione() {
		return estensione;
	}
	public void setEstensione(String estensione) {
		this.estensione = estensione;
	}
	/*public List<Campagna> getListaCampagna(){
		return listaCampagne;
	}
	public void setListaCampagna(List<Campagna> lista){
		this.listaCampagne = lista;
	}
	public List<Localita> getListaLocalita(){
		return listaLocalita;
	}
	public void setListaLocalita(List<Localita> lista){
		this.listaLocalita = lista;
	}*/

}
