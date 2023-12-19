package com.nico.implpatrondto;

import com.nico.implpatrondto.entities.*;
import com.nico.implpatrondto.enums.EstadoCuenta;
import com.nico.implpatrondto.enums.TipoDeOperacion;
import com.nico.implpatrondto.exceptions.ClienteNotFoundException;
import com.nico.implpatrondto.repositories.ClienteRepository;
import com.nico.implpatrondto.repositories.CuentaBancariaRepository;
import com.nico.implpatrondto.repositories.OperacionCuentaRepository;
import com.nico.implpatrondto.services.BancoService;
import com.nico.implpatrondto.services.CuentaBancariaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ImplPatronDtoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImplPatronDtoApplication.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunner(BancoService bancoService){
        return args -> bancoService.consultar();
    }

    //@Bean
    CommandLineRunner start(ClienteRepository clienteRepository, CuentaBancariaRepository cuentaBancariaRepository, OperacionCuentaRepository operacionCuentaRepository){
        return args -> {
            Stream.of("Pepe", "Juan", "Pedro", "Marcos").forEach(nombre ->{
                Cliente cliente = new Cliente();
                cliente.setNombre(nombre);
                cliente.setEmail(nombre+"@gmail.com");
                clienteRepository.save(cliente);
                    });
            //le asignamos cuentas bancarias

            clienteRepository.findAll().forEach(cliente -> {
                CuentaActual cuentaActual = new CuentaActual();
                cuentaActual.setId(UUID.randomUUID().toString());
                cuentaActual.setBalance(Math.random()*98000);
                cuentaActual.setFechaCreacion(new Date());
                cuentaActual.setEstadoCuenta(EstadoCuenta.CREADA);
                cuentaActual.setCliente(cliente);
                cuentaActual.setSobregiro(9800);
                cuentaBancariaRepository.save(cuentaActual);

                CuentaDeAhorro cuentaDeAhorro = new CuentaDeAhorro();
                cuentaDeAhorro.setId(UUID.randomUUID().toString());
                cuentaDeAhorro.setBalance(Math.random()*98000);
                cuentaDeAhorro.setFechaCreacion(new Date());
                cuentaDeAhorro.setEstadoCuenta(EstadoCuenta.CREADA);
                cuentaDeAhorro.setCliente(cliente);
                cuentaDeAhorro.setTasaDeInteres(5.5);
                cuentaBancariaRepository.save(cuentaDeAhorro);
            });

            // agregamos las operaciones

            cuentaBancariaRepository.findAll().forEach(cuentaBancaria -> {
                for (int i = 0; i < 10; i++) {
                    OperacionCuenta operacionCuenta = new OperacionCuenta();
                    operacionCuenta.setFechaOperacion(new Date());
                    operacionCuenta.setMonto(Math.random() * 12000);
                    operacionCuenta.setTipoDeOperacion(Math.random() > 0.5 ? TipoDeOperacion.DEBITO : TipoDeOperacion.CREDITO);
                    operacionCuenta.setCuentaBancaria(cuentaBancaria);

                    operacionCuentaRepository.save(operacionCuenta);

                }
            });
        };
    }

    //@Bean
    CommandLineRunner start2(CuentaBancariaService cuentaBancariaService){
        return args -> {
            Stream.of("Pepe", "Juan", "Pedro", "Marcos").forEach(nombre ->{
                Cliente cliente = new Cliente();
                cliente.setNombre(nombre);
                cliente.setEmail(nombre+"@gmail.com");
                cuentaBancariaService.saveCliente(cliente);
            });
            cuentaBancariaService.listClientes().forEach(cliente ->{
                try{
                    cuentaBancariaService.saveCuentaBancariaActual(Math.random() * 90000, 9000, cliente.getId());
                    cuentaBancariaService.saveCuentaBancariaAhorro(120000, 5.5, cliente.getId());

                    List<CuentaBancaria> cuentasBancarias = cuentaBancariaService.listCuentasBancarias();

                    for (CuentaBancaria cuentaBancaria : cuentasBancarias){
                        for (int i = 0; i < 10; i++) {
                            cuentaBancariaService.credit(cuentaBancaria.getId(), 10000+Math.random()*120000, "Credito");
                            cuentaBancariaService.debit(cuentaBancaria.getId(), 1000+Math.random()*9000, "Debito");

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            });

        };
    }
}
