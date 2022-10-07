package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the let database table.
 * 
 */
@Entity
@NamedQuery(name="Let.findAll", query="SELECT l FROM Let l")
public class Let implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idLet;

	private double cena;

	@Temporal(TemporalType.DATE)
	private Date datumDolaska;

	@Temporal(TemporalType.DATE)
	private Date datumPolaska;

	private String trajanjeLeta;

	private String vremePolaska;

	//bi-directional many-to-one association to Karta
	@OneToMany(mappedBy="let")
	private List<Karta> kartas;

	//bi-directional many-to-one association to Aerodrom
	@ManyToOne
	@JoinColumn(name="Aerodrom_idAerodromOdlazni")
	private Aerodrom aerodrom1;

	//bi-directional many-to-one association to Aerodrom
	@ManyToOne
	@JoinColumn(name="Aerodrom_idAerodromDolazni")
	private Aerodrom aerodrom2;

	//bi-directional many-to-many association to Aerodrom
	@ManyToMany
	@JoinTable(
		name="presedanje"
		, joinColumns={
			@JoinColumn(name="Let_idLet")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Aerodrom_idAerodrom")
			}
		)
	private List<Aerodrom> aerodroms;

	//bi-directional many-to-one association to Kompanija
	@ManyToOne
	private Kompanija kompanija;

	//bi-directional many-to-one association to Mesto
	@OneToMany(mappedBy="let")
	private List<Mesto> mestos;

	public Let() {
	}

	public int getIdLet() {
		return this.idLet;
	}

	public void setIdLet(int idLet) {
		this.idLet = idLet;
	}

	public double getCena() {
		return this.cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Date getDatumDolaska() {
		return this.datumDolaska;
	}

	public void setDatumDolaska(Date datumDolaska) {
		this.datumDolaska = datumDolaska;
	}

	public Date getDatumPolaska() {
		return this.datumPolaska;
	}

	public void setDatumPolaska(Date datumPolaska) {
		this.datumPolaska = datumPolaska;
	}

	public String getTrajanjeLeta() {
		return this.trajanjeLeta;
	}

	public void setTrajanjeLeta(String trajanjeLeta) {
		this.trajanjeLeta = trajanjeLeta;
	}

	public String getVremePolaska() {
		return this.vremePolaska;
	}

	public void setVremePolaska(String vremePolaska) {
		this.vremePolaska = vremePolaska;
	}

	public List<Karta> getKartas() {
		return this.kartas;
	}

	public void setKartas(List<Karta> kartas) {
		this.kartas = kartas;
	}

	public Karta addKarta(Karta karta) {
		getKartas().add(karta);
		karta.setLet(this);

		return karta;
	}

	public Karta removeKarta(Karta karta) {
		getKartas().remove(karta);
		karta.setLet(null);

		return karta;
	}

	public Aerodrom getAerodrom1() {
		return this.aerodrom1;
	}

	public void setAerodrom1(Aerodrom aerodrom1) {
		this.aerodrom1 = aerodrom1;
	}

	public Aerodrom getAerodrom2() {
		return this.aerodrom2;
	}

	public void setAerodrom2(Aerodrom aerodrom2) {
		this.aerodrom2 = aerodrom2;
	}

	public List<Aerodrom> getAerodroms() {
		return this.aerodroms;
	}

	public void setAerodroms(List<Aerodrom> aerodroms) {
		this.aerodroms = aerodroms;
	}

	public Kompanija getKompanija() {
		return this.kompanija;
	}

	public void setKompanija(Kompanija kompanija) {
		this.kompanija = kompanija;
	}

	public List<Mesto> getMestos() {
		return this.mestos;
	}

	public void setMestos(List<Mesto> mestos) {
		this.mestos = mestos;
	}

	public Mesto addMesto(Mesto mesto) {
		getMestos().add(mesto);
		mesto.setLet(this);

		return mesto;
	}

	public Mesto removeMesto(Mesto mesto) {
		getMestos().remove(mesto);
		mesto.setLet(null);

		return mesto;
	}

}