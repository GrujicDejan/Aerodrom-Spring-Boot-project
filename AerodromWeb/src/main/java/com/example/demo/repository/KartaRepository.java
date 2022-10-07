package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Karta;
import model.Let;
import model.Putnik;

public interface KartaRepository extends JpaRepository<Karta, Integer> {

	List<Karta> findByLet(Let l);

	@Query("select k from Karta k where k.putnik.ime like :ime and k.putnik.prezime like :prezime "
			+ "and k.let.aerodrom2.mesto like :destinacija and k.datum like :datum")
	List<Karta> filtrirajZaImePrezimeDestinacijaDatum(@Param("ime")String ime, @Param("prezime")String prezime,
			@Param("destinacija")String destinacija, @Param("datum")Date datum);

	@Query("select k from Karta k where k.putnik.ime like :ime and k.putnik.prezime like :prezime "
			+ "and k.let.aerodrom2.mesto like :destinacija")
	List<Karta> filtrirajZaImePrezimeDestinacija(@Param("ime")String ime, @Param("prezime")String prezime,
			@Param("destinacija")String destinacija);

	@Query("select k from Karta k where k.putnik.ime like :ime and k.putnik.prezime like :prezime "
			+ "and k.datum like :datum")
	List<Karta> filtrirajZaImePrezimeDatum(@Param("ime")String ime, @Param("prezime")String prezime,  @Param("datum")Date datum);

	@Query("select k from Karta k where k.putnik.ime like :ime "
			+ "and k.let.aerodrom2.mesto like :destinacija and k.datum like :datum")
	List<Karta> filtrirajZaImeDestinacijaDatum(@Param("ime")String ime, @Param("destinacija")String destinacija, @Param("datum")Date datum);

	@Query("select k from Karta k where k.putnik.prezime like :prezime "
			+ "and k.let.aerodrom2.mesto like :destinacija and k.datum like :datum")
	List<Karta> filtrirajZaPrezimeDestinacijaDatum(@Param("prezime")String prezime, @Param("destinacija")String destinacija, @Param("datum")Date datum);

	@Query("select k from Karta k where k.putnik.ime like :ime and k.putnik.prezime like :prezime")
	List<Karta> filtrirajZaImePrezime(@Param("ime")String ime, @Param("prezime")String prezime);

	@Query("select k from Karta k where k.putnik.ime like :ime and k.let.aerodrom2.mesto like :destinacija")
	List<Karta> filtrirajZaImeDestinacija(@Param("ime")String ime, @Param("destinacija")String destinacija);

	@Query("select k from Karta k where k.putnik.ime like :ime and k.datum like :datum")
	List<Karta> filtrirajZaImeDatum(@Param("ime")String ime, @Param("datum")Date datum);

	@Query("select k from Karta k where k.putnik.prezime like :prezime and k.let.aerodrom2.mesto like :destinacija")
	List<Karta> filtrirajZaPrezimeDestinacija(@Param("prezime")String prezime, @Param("destinacija")String destinacija);

	@Query("select k from Karta k where k.putnik.ime like :ime and k.putnik.prezime like :prezime "
			+ "and k.let.aerodrom2.mesto like :destinacija and k.datum like :datum")
	List<Karta> filtrirajZaPrezimeDatum(@Param("prezime")String prezime, @Param("datum")Date datum);

	@Query("select k from Karta k where k.let.aerodrom2.mesto like :destinacija and k.datum like :datum")
	List<Karta> filtrirajZaDestinacijaDatum(@Param("destinacija")String destinacija, @Param("datum")Date datum);

	@Query("select k from Karta k where k.putnik.ime like :ime")
	List<Karta> filtrirajZaIme(@Param("ime")String ime);

	@Query("select k from Karta k where k.putnik.prezime like :prezime")
	List<Karta> filtrirajZaPrezime(@Param("prezime")String prezime);

	@Query("select k from Karta k where k.let.aerodrom2.mesto like :destinacija")
	List<Karta> filtrirajZaDestinacija(@Param("destinacija")String destinacija);

	@Query("select k from Karta k where k.datum like :datum")
	List<Karta> filtrirajZaDatum(@Param("datum")Date datum);

	@Query("select k from Karta k where k.let.datumPolaska > :datum or  k.let.datumPolaska = :datum order by k.datum")
	List<Karta> getAkutelne(@Param("datum")Date datum);

	List<Karta> findAllByPutnik(Putnik putnik);

	@Query("select k from Karta k where k.datum like :datum and k.let.aerodrom2.mesto like :destinacija"
			+ " and k.putnik.idPutnik =:idPutnik")
	List<Karta> filtrirajZaDestinacijaDatumPutnik(@Param("idPutnik")int idPutnik, @Param("destinacija")String destinacija, @Param("datum")Date datum);

	@Query("select k from Karta k where k.let.aerodrom2.mesto like :destinacija and k.putnik.idPutnik =:idPutnik")
	List<Karta> filtrirajZaDestinacijaPutnik(@Param("idPutnik")int idPutnik, @Param("destinacija")String destinacija);
	
	@Query("select k from Karta k where k.datum like :datum and k.putnik.idPutnik =:idPutnik")
	List<Karta> filtrirajZaDatumPutnik(@Param("idPutnik")int idPutnik, @Param("datum")Date datum);

}
