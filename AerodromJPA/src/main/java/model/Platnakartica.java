package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the platnakartica database table.
 * 
 */
@Entity
@NamedQuery(name="Platnakartica.findAll", query="SELECT p FROM Platnakartica p")
public class Platnakartica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPlatnaKartica;

	private String brojKartice;

	private int cvv;

	private String datumIsteka;

	//bi-directional many-to-one association to Putnik
	@ManyToOne
	private Putnik putnik;

	public Platnakartica() {
	}

	public int getIdPlatnaKartica() {
		return this.idPlatnaKartica;
	}

	public void setIdPlatnaKartica(int idPlatnaKartica) {
		this.idPlatnaKartica = idPlatnaKartica;
	}

	public String getBrojKartice() {
		return this.brojKartice;
	}

	public void setBrojKartice(String brojKartice) {
		this.brojKartice = brojKartice;
	}

	public int getCvv() {
		return this.cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public String getDatumIsteka() {
		return this.datumIsteka;
	}

	public void setDatumIsteka(String datumIsteka) {
		this.datumIsteka = datumIsteka;
	}

	public Putnik getPutnik() {
		return this.putnik;
	}

	public void setPutnik(Putnik putnik) {
		this.putnik = putnik;
	}

}