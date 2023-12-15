package com.nico.implpatrondto.entities;

import com.nico.implpatrondto.enums.TipoDeOperacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperacionCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date fechaOperacion;

    private double monto;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoDeOperacion tipoDeOperacion;

    @ManyToOne
    private CuentaBancaria cuentaBancaria;
}
