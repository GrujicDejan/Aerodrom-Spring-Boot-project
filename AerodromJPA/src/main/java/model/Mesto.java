package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the mesto database table.
 * 
 */
@Entity
@NamedQuery(name="Mesto.findAll", query="SELECT m FROM Mesto m")
public class Mesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMesto;

	private String broj;

	private String klasa;

	private String red;

	//bi-directional many-to-one association to Karta
	@OneToMany(mappedBy="mesto")
	private List<Karta> kartas;

	//bi-directional many-to-one association to Let
	@ManyToOne
	private Let let;

	public Mesto() {
	}

	public int getIdMesto() {
		return this.idMesto;
	}

	public void setIdMesto(int idMesto) {
		this.idMesto = idMesto;
	}

	public String getBroj() {
		return this.broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public String getKlasa() {
		return this.klasa;
	}

	public void setKlasa(String klasa) {
		this.klasa = klasa;
	}

	public String getRed() {
		return this.red;
	}

	public void setRed(String red) {
		this.red = red;
	}

	public List<Karta> getKartas() {
		return this.kartas;
	}

	public void setKartas(List<Karta> kartas) {
		this.kartas = kartas;
	}

	public Karta addKarta(Karta karta) {
		getKartas().add(karta);
		karta.setMesto(this);

		return karta;
	}

	public Karta removeKarta(Karta karta) {
		getKartas().remove(karta);
		karta.setMesto(null);

		return karta;
	}

	public Let getLet() {
		return this.let;
	}

	public void setLet(Let let) {
		this.let = let;
	}

}