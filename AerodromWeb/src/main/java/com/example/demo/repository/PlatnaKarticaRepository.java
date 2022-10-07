package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Platnakartica;
import model.Putnik;

public interface PlatnaKarticaRepository extends JpaRepository<Platnakartica, Integer> {

	Platnakartica findByPutnik(Putnik putnik);

}
