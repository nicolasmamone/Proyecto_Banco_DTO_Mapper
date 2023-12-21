package com.nico.implpatrondto.services.impl;

import com.nico.implpatrondto.dtos.ClienteDTO;
import com.nico.implpatrondto.entities.*;
import com.nico.implpatrondto.enums.TipoDeOperacion;
import com.nico.implpatrondto.exceptions.BalanceInsuficienteException;
import com.nico.implpatrondto.exceptions.ClienteNotFoundException;
import com.nico.implpatrondto.exceptions.CuentaBancariaNotFoundException;
import com.nico.implpatrondto.mappers.CuentaBancariaMappersImpl;
import com.nico.implpatrondto.repositories.ClienteRepository;
import com.nico.implpatrondto.repositories.CuentaBancariaRepository;
import com.nico.implpatrondto.repositories.OperacionCuentaRepository;
import com.nico.implpatrondto.services.CuentaBancariaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j // Usamos la anotación @Slf4j que se utiliza para mantener un registro en consola de las transacciones de la aplicación, lo que se conoce como un Logger.
public class CuentaBancariaServiceImpl implements CuentaBancariaService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    @Autowired
    private OperacionCuentaRepository operacionCuentaRepository;

    @Autowired
    private CuentaBancariaMappersImpl cuentaBancariaMappers;

    @Override
    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        log.info("Guardando un nuevo cliente");
        Cliente cliente = cuentaBancariaMappers.mapearDeClienteDTO(clienteDTO);
        Cliente clienteBBDD = clienteRepository.save(cliente);
        return cuentaBancariaMappers.mapearDeCliente(clienteBBDD);
    }

    @Override
    public ClienteDTO getCliente(Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente No encontrado"));
        return cuentaBancariaMappers.mapearDeCliente(cliente);
    }

    @Override
    public ClienteDTO updateCliente(ClienteDTO clienteDTO) {
        log.info("Actualizando Cliente");
        Cliente cliente = cuentaBancariaMappers.mapearDeClienteDTO(clienteDTO);
        Cliente clienteBBDD = clienteRepository.save(cliente);
        return cuentaBancariaMappers.mapearDeCliente(clienteBBDD);
    }

    @Override
    public void deleteCliente(Long clienteId) {
        clienteRepository.deleteById(clienteId);
    }

    @Override
    public CuentaActual saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) throw new ClienteNotFoundException("Cliente No encontrado");

        CuentaActual cuentaActual = new CuentaActual();
        cuentaActual.setId(UUID.randomUUID().toString());
        cuentaActual.setFechaCreacion(new Date());
        cuentaActual.setBalance(balanceInicial);
        cuentaActual.setSobregiro(sobregiro);
        cuentaActual.setCliente(cliente);

        CuentaActual cuentaActualBBDD = cuentaBancariaRepository.save(cuentaActual);
        return cuentaActualBBDD;
    }

    @Override
    public CuentaDeAhorro saveCuentaBancariaAhorro(double balanceInicial, double tasaDeInteres, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) throw new ClienteNotFoundException("Cliente No encontrado");

        CuentaDeAhorro cuentaDeAhorro = new CuentaDeAhorro();
        cuentaDeAhorro.setId(UUID.randomUUID().toString());
        cuentaDeAhorro.setFechaCreacion(new Date());
        cuentaDeAhorro.setBalance(balanceInicial);
        cuentaDeAhorro.setTasaDeInteres(tasaDeInteres);
        cuentaDeAhorro.setCliente(cliente);

        CuentaDeAhorro cuentaDeAhorroBBDD = cuentaBancariaRepository.save(cuentaDeAhorro);
        return cuentaDeAhorroBBDD;
    }

    @Override
public List<ClienteDTO> listClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clientesDTO = clientes.stream()
                .map(cliente -> cuentaBancariaMappers.mapearDeCliente(cliente))
                .collect(Collectors.toList());
        return clientesDTO;
    }

    @Override
    public CuentaBancaria getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta Bancaria no encontrada"));
        return cuentaBancaria;
    }

    @Override
    public void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        CuentaBancaria cuentaBancaria = getCuentaBancaria(cuentaId);
        if(cuentaBancaria.getBalance() < monto){ // Si se requiere retirar mayor monto que el que hay, se lanza un exception
            throw new BalanceInsuficienteException("Balance Insuficiente");
        }

        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoDeOperacion(TipoDeOperacion.DEBITO);
        operacionCuenta.setMonto(monto);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);
        operacionCuentaRepository.save(operacionCuenta);
        // --------------------------------------------------
        cuentaBancaria.setBalance(cuentaBancaria.getBalance() - monto);
        cuentaBancariaRepository.save(cuentaBancaria);

    }

    @Override
    public void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = getCuentaBancaria(cuentaId);

        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoDeOperacion(TipoDeOperacion.CREDITO);
        operacionCuenta.setMonto(monto);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);
        operacionCuentaRepository.save(operacionCuenta);
        // --------------------------------------------------
        cuentaBancaria.setBalance(cuentaBancaria.getBalance() + monto);
        cuentaBancariaRepository.save(cuentaBancaria);
    }

    @Override
    public void transfer(String cuentaIdPropietario, String cuentaDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        debit(cuentaIdPropietario, monto, "Transferencia a: " + cuentaIdPropietario);
        credit(cuentaDestinatario, monto, "Transferencia de: " + cuentaIdPropietario);
    }

    @Override
    public List<CuentaBancaria> listCuentasBancarias() {
        return cuentaBancariaRepository.findAll();
    }
}
