package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the kompanija database table.
 * 
 */
@Entity
@NamedQuery(name="Kompanija.findAll", query="SELECT k FROM Kompanija k")
public class Kompanija implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKompanija;

	private String naziv;

	//bi-directional many-to-one association to Let
	@OneToMany(mappedBy="kompanija")
	private List<Let> lets;

	//bi-directional many-to-one association to Recenzija
	@OneToMany(mappedBy="kompanija")
	private List<Recenzija> recenzijas;

	public Kompanija() {
	}

	public int getIdKompanija() {
		return this.idKompanija;
	}

	public void setIdKompanija(int idKompanija) {
		this.idKompanija = idKompanija;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Let> getLets() {
		return this.lets;
	}

	public void setLets(List<Let> lets) {
		this.lets = lets;
	}

	public Let addLet(Let let) {
		getLets().add(let);
		let.setKompanija(this);

		return let;
	}

	public Let removeLet(Let let) {
		getLets().remove(let);
		let.setKompanija(null);

		return let;
	}

	public List<Recenzija> getRecenzijas() {
		return this.recenzijas;
	}

	public void setRecenzijas(List<Recenzija> recenzijas) {
		this.recenzijas = recenzijas;
	}

	public Recenzija addRecenzija(Recenzija recenzija) {
		getRecenzijas().add(recenzija);
		recenzija.setKompanija(this);

		return recenzija;
	}

	public Recenzija removeRecenzija(Recenzija recenzija) {
		getRecenzijas().remove(recenzija);
		recenzija.setKompanija(null);

		return recenzija;
	}

}