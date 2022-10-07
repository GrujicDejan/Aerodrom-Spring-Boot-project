package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Let;
import model.Mesto;

public interface MestoRepository extends JpaRepository<Mesto, Integer> {

	@Query("select m from Mesto m where m.let.idLet=:idLet and m not in "
			+ "(select k.mesto from Karta k)")
	List<Mesto> getSlobodnaMestaZaLet(@Param("idLet")Integer idLet);

	List<Mesto> findByLet(Let l);

}
