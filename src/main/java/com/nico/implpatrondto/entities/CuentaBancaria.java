package com.nico.implpatrondto.entities;

import com.nico.implpatrondto.enums.EstadoCuenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO", length = 4) // indica que todas las clases de una jerarquia se van a asignar en una sola tabla
public class CuentaBancaria {

    @Id
    private String id;

    private double balance;
    private Date fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "cuentaBancaria", fetch = FetchType.LAZY)
    // mappedBy indica que esta es la clase padre, la dueña de la identidad
    // Aparace la List solo si la llaman LAZY --- Sino EAGER aparece siempre
    private List<OperacionCuenta> operacionCuentas;

}
