package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Aerodrom;

public interface AerodromRepository extends JpaRepository<Aerodrom, Integer> {

}
