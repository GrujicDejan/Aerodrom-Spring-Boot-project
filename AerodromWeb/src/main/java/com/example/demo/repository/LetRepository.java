package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Let;

public interface LetRepository extends JpaRepository<Let, Integer>{
	
	@Query("select l from Let l where l.datumPolaska like :datumPolaska and l.datumDolaska like :datumDolaska "
			+ "and l.aerodrom2.mesto like :destinacija")
	List<Let> filtrirajPoDatumuIDestinaciji(@Param("datumDolaska")Date datumDolaska, 
			@Param("datumPolaska")Date datumPolaska, @Param("destinacija")String destinacija);

	@Query("select l from Let l where l.datumPolaska like :datumPolaska and l.datumDolaska like :datumDolaska")
	List<Let> filtrirajPoDatumu(@Param("datumDolaska")Date datumDolaska,
			@Param("datumPolaska")Date datumPolaska);

	@Query("select l from Let l where l.aerodrom2.mesto like :destinacija")
	List<Let> filtrirajPoDestinaciji(@Param("destinacija")String destinacija);

}
