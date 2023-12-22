package com.nico.implpatrondto.repositories;

import com.nico.implpatrondto.entities.OperacionCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OperacionCuentaRepository extends JpaRepository<OperacionCuenta, String> {

    List<OperacionCuenta> findByCuentaBancariaId(String cuentaId);
}
