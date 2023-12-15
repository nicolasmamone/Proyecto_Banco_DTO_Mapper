package com.nico.implpatrondto.repositories;

import com.nico.implpatrondto.entities.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, String> {
}
