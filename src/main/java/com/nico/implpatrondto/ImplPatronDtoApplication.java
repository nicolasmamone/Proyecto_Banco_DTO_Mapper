package com.nico.implpatrondto;

import com.nico.implpatrondto.entities.Cliente;
import com.nico.implpatrondto.entities.CuentaActual;
import com.nico.implpatrondto.entities.CuentaDeAhorro;
import com.nico.implpatrondto.entities.OperacionCuenta;
import com.nico.implpatrondto.enums.EstadoCuenta;
import com.nico.implpatrondto.enums.TipoDeOperacion;
import com.nico.implpatrondto.repositories.ClienteRepository;
import com.nico.implpatrondto.repositories.CuentaBancariaRepository;
import com.nico.implpatrondto.repositories.OperacionCuentaRepository;
import com.nico.implpatrondto.services.BancoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ImplPatronDtoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImplPatronDtoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BancoService bancoService){
        return args -> {
            bancoService.consultar();
        };
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
}
