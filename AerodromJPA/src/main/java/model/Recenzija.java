package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the recenzija database table.
 * 
 */
@Entity
@NamedQuery(name="Recenzija.findAll", query="SELECT r FROM Recenzija r")
public class Recenzija implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idRecenzija;

	@Temporal(TemporalType.DATE)
	private Date datum;

	private int ocena;

	private String sadrzaj;

	//bi-directional many-to-one association to Kompanija
	@ManyToOne
	private Kompanija kompanija;

	//bi-directional many-to-one association to Putnik
	@ManyToOne
	private Putnik putnik;

	public Recenzija() {
	}

	public int getIdRecenzija() {
		return this.idRecenzija;
	}

	public void setIdRecenzija(int idRecenzija) {
		this.idRecenzija = idRecenzija;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public int getOcena() {
		return this.ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public String getSadrzaj() {
		return this.sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public Kompanija getKompanija() {
		return this.kompanija;
	}

	public void setKompanija(Kompanija kompanija) {
		this.kompanija = kompanija;
	}

	public Putnik getPutnik() {
		return this.putnik;
	}

	public void setPutnik(Putnik putnik) {
		this.putnik = putnik;
	}

}