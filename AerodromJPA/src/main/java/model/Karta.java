package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the karta database table.
 * 
 */
@Entity
@NamedQuery(name="Karta.findAll", query="SELECT k FROM Karta k")
public class Karta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKarta;

	private double cena;

	@Temporal(TemporalType.DATE)
	private Date datum;

	private String opis;

	//bi-directional many-to-one association to Let
	@ManyToOne
	private Let let;

	//bi-directional many-to-one association to Mesto
	@ManyToOne
	private Mesto mesto;

	//bi-directional many-to-one association to Putnik
	@ManyToOne
	private Putnik putnik;

	public Karta() {
	}

	public int getIdKarta() {
		return this.idKarta;
	}

	public void setIdKarta(int idKarta) {
		this.idKarta = idKarta;
	}

	public double getCena() {
		return this.cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Let getLet() {
		return this.let;
	}

	public void setLet(Let let) {
		this.let = let;
	}

	public Mesto getMesto() {
		return this.mesto;
	}

	public void setMesto(Mesto mesto) {
		this.mesto = mesto;
	}

	public Putnik getPutnik() {
		return this.putnik;
	}

	public void setPutnik(Putnik putnik) {
		this.putnik = putnik;
	}

}