package com.nico.implpatrondto.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@DiscriminatorValue("CA") // Current Account
public class CuentaActual extends CuentaBancaria {

    private double sobregiro;
}
