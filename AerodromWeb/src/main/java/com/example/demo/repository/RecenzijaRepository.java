package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Kompanija;
import model.Recenzija;

public interface RecenzijaRepository extends JpaRepository<Recenzija, Integer> {

	List<Recenzija> findByKompanijaOrderByOcena(Kompanija k);

}
