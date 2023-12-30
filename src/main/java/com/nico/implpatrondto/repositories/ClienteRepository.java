package com.nico.implpatrondto.repositories;

import com.nico.implpatrondto.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE :kw")
    List<Cliente> searchClientes(@Param(value ="kw") String keyword);
}
