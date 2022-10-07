package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the korisnik database table.
 * 
 */
@Entity
@NamedQuery(name="Korisnik.findAll", query="SELECT k FROM Korisnik k")
public class Korisnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKorisnik;

	private String korisnickoIme;

	private String sifra;

	//bi-directional many-to-one association to Putnik
	@ManyToOne
	private Putnik putnik;

	//bi-directional many-to-many association to Uloga
	@ManyToMany(fetch = FetchType.EAGER, mappedBy="korisniks")
	private Set<Uloga> ulogas = new HashSet<Uloga>();

	public Korisnik() {
	}

	public int getIdKorisnik() {
		return this.idKorisnik;
	}

	public void setIdKorisnik(int idKorisnik) {
		this.idKorisnik = idKorisnik;
	}

	public String getKorisnickoIme() {
		return this.korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getSifra() {
		return this.sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public Putnik getPutnik() {
		return this.putnik;
	}

	public void setPutnik(Putnik putnik) {
		this.putnik = putnik;
	}

	public Set<Uloga> getUlogas() {
		return this.ulogas;
	}

	public void setUlogas(Set<Uloga> ulogas) {
		this.ulogas = ulogas;
	}

}