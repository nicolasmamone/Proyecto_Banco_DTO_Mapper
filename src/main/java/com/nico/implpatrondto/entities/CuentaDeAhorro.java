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

@DiscriminatorValue("SA") // "Saving Account"  valor que le va a pertenecer a esta columna
public class CuentaDeAhorro extends CuentaBancaria{

    private double tasaDeInteres;

}
