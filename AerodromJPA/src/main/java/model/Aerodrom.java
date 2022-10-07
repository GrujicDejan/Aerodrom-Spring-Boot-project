package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the aerodrom database table.
 * 
 */
@Entity
@NamedQuery(name="Aerodrom.findAll", query="SELECT a FROM Aerodrom a")
public class Aerodrom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idAerodrom;

	private String adresa;

	private String mesto;

	private String naziv;

	//bi-directional many-to-one association to Let
	@OneToMany(mappedBy="aerodrom1")
	private List<Let> lets1;

	//bi-directional many-to-one association to Let
	@OneToMany(mappedBy="aerodrom2")
	private List<Let> lets2;

	//bi-directional many-to-many association to Let
	@ManyToMany(mappedBy="aerodroms")
	private List<Let> lets3;

	public Aerodrom() {
	}

	public int getIdAerodrom() {
		return this.idAerodrom;
	}

	public void setIdAerodrom(int idAerodrom) {
		this.idAerodrom = idAerodrom;
	}

	public String getAdresa() {
		return this.adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getMesto() {
		return this.mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Let> getLets1() {
		return this.lets1;
	}

	public void setLets1(List<Let> lets1) {
		this.lets1 = lets1;
	}

	public Let addLets1(Let lets1) {
		getLets1().add(lets1);
		lets1.setAerodrom1(this);

		return lets1;
	}

	public Let removeLets1(Let lets1) {
		getLets1().remove(lets1);
		lets1.setAerodrom1(null);

		return lets1;
	}

	public List<Let> getLets2() {
		return this.lets2;
	}

	public void setLets2(List<Let> lets2) {
		this.lets2 = lets2;
	}

	public Let addLets2(Let lets2) {
		getLets2().add(lets2);
		lets2.setAerodrom2(this);

		return lets2;
	}

	public Let removeLets2(Let lets2) {
		getLets2().remove(lets2);
		lets2.setAerodrom2(null);

		return lets2;
	}

	public List<Let> getLets3() {
		return this.lets3;
	}

	public void setLets3(List<Let> lets3) {
		this.lets3 = lets3;
	}

}