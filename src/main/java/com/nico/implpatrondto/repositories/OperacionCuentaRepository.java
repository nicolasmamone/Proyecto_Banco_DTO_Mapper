package com.nico.implpatrondto.repositories;

import com.nico.implpatrondto.entities.OperacionCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacionCuentaRepository extends JpaRepository<OperacionCuenta, String> {
}
