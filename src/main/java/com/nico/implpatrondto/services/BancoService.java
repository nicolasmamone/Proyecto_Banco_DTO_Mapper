package com.nico.implpatrondto.services;

import com.nico.implpatrondto.entities.CuentaActual;
import com.nico.implpatrondto.entities.CuentaBancaria;
import com.nico.implpatrondto.entities.CuentaDeAhorro;
import com.nico.implpatrondto.repositories.CuentaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BancoService {

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    public void consultar(){
        CuentaBancaria cuentaBancariaBBDD = cuentaBancariaRepository.findById("2a52eccc-7edc-4967-9cc5-6cd14bf1a451").orElse(null);
        if (cuentaBancariaBBDD != null){
            System.out.println("*****************************");
            System.out.println("ID: " + cuentaBancariaBBDD.getId());
            System.out.println("BALANCE: " + cuentaBancariaBBDD.getBalance());
            System.out.println("ESTADO: " + cuentaBancariaBBDD.getEstadoCuenta());
            System.out.println("FECHA_DE_CREACION: " +cuentaBancariaBBDD.getFechaCreacion());
            System.out.println("CLIENTE: " + cuentaBancariaBBDD.getCliente().getNombre());
            System.out.println("NOMBRE_DE_LA_CLASE: " + cuentaBancariaBBDD.getClass().getSimpleName());

            if (cuentaBancariaBBDD instanceof CuentaActual){
                System.out.println("SOBREGIRO: " + (((CuentaActual) cuentaBancariaBBDD).getSobregiro()));
            }
            else if (cuentaBancariaBBDD instanceof CuentaDeAhorro){
                System.out.println("TASA_DE_INTERES: " + (((CuentaDeAhorro) cuentaBancariaBBDD).getTasaDeInteres()));
            }

            cuentaBancariaBBDD.getOperacionCuentas().forEach(operacionCuenta -> {
                System.out.println(("-----------------------------------------------"));
                System.out.println("TIPO DE OPERACIONES: " + operacionCuenta.getTipoDeOperacion());
                System.out.println("FECHA DE OPERACION: " + operacionCuenta.getFechaOperacion());
                System.out.println("MONTO: " + operacionCuenta.getMonto());
            });

        }
    }
}
