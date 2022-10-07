package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the putnik database table.
 * 
 */
@Entity
@NamedQuery(name="Putnik.findAll", query="SELECT p FROM Putnik p")
public class Putnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPutnik;

	private String adresa;

	private String brojTelefona;

	@Temporal(TemporalType.DATE)
	private Date datumRodjenja;

	private String email;

	private String grad;

	private String ime;

	private String prezime;

	//bi-directional many-to-one association to Karta
	@OneToMany(mappedBy="putnik")
	private List<Karta> kartas;

	//bi-directional many-to-one association to Korisnik
	@OneToMany(mappedBy="putnik")
	private List<Korisnik> korisniks;

	//bi-directional many-to-one association to Platnakartica
	@OneToMany(mappedBy="putnik")
	private List<Platnakartica> platnakarticas;

	//bi-directional many-to-one association to Recenzija
	@OneToMany(mappedBy="putnik")
	private List<Recenzija> recenzijas;

	public Putnik() {
	}

	public int getIdPutnik() {
		return this.idPutnik;
	}

	public void setIdPutnik(int idPutnik) {
		this.idPutnik = idPutnik;
	}

	public String getAdresa() {
		return this.adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getBrojTelefona() {
		return this.brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public Date getDatumRodjenja() {
		return this.datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGrad() {
		return this.grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return this.prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public List<Karta> getKartas() {
		return this.kartas;
	}

	public void setKartas(List<Karta> kartas) {
		this.kartas = kartas;
	}

	public Karta addKarta(Karta karta) {
		getKartas().add(karta);
		karta.setPutnik(this);

		return karta;
	}

	public Karta removeKarta(Karta karta) {
		getKartas().remove(karta);
		karta.setPutnik(null);

		return karta;
	}

	public List<Korisnik> getKorisniks() {
		return this.korisniks;
	}

	public void setKorisniks(List<Korisnik> korisniks) {
		this.korisniks = korisniks;
	}

	public Korisnik addKorisnik(Korisnik korisnik) {
		getKorisniks().add(korisnik);
		korisnik.setPutnik(this);

		return korisnik;
	}

	public Korisnik removeKorisnik(Korisnik korisnik) {
		getKorisniks().remove(korisnik);
		korisnik.setPutnik(null);

		return korisnik;
	}

	public List<Platnakartica> getPlatnakarticas() {
		return this.platnakarticas;
	}

	public void setPlatnakarticas(List<Platnakartica> platnakarticas) {
		this.platnakarticas = platnakarticas;
	}

	public Platnakartica addPlatnakartica(Platnakartica platnakartica) {
		getPlatnakarticas().add(platnakartica);
		platnakartica.setPutnik(this);

		return platnakartica;
	}

	public Platnakartica removePlatnakartica(Platnakartica platnakartica) {
		getPlatnakarticas().remove(platnakartica);
		platnakartica.setPutnik(null);

		return platnakartica;
	}

	public List<Recenzija> getRecenzijas() {
		return this.recenzijas;
	}

	public void setRecenzijas(List<Recenzija> recenzijas) {
		this.recenzijas = recenzijas;
	}

	public Recenzija addRecenzija(Recenzija recenzija) {
		getRecenzijas().add(recenzija);
		recenzija.setPutnik(this);

		return recenzija;
	}

	public Recenzija removeRecenzija(Recenzija recenzija) {
		getRecenzijas().remove(recenzija);
		recenzija.setPutnik(null);

		return recenzija;
	}

}