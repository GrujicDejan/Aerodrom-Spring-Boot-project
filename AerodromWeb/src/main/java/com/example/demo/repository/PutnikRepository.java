package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Putnik;

public interface PutnikRepository extends JpaRepository<Putnik, Integer> {

}
