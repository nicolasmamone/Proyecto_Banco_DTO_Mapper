package com.nico.implpatrondto.services;

import com.nico.implpatrondto.dtos.*;
import com.nico.implpatrondto.entities.Cliente;
import com.nico.implpatrondto.entities.CuentaActual;
import com.nico.implpatrondto.entities.CuentaBancaria;
import com.nico.implpatrondto.entities.CuentaDeAhorro;
import com.nico.implpatrondto.exceptions.BalanceInsuficienteException;
import com.nico.implpatrondto.exceptions.ClienteNotFoundException;
import com.nico.implpatrondto.exceptions.CuentaBancariaNotFoundException;

import java.util.List;


public interface CuentaBancariaService {


    ClienteDTO saveCliente(ClienteDTO clienteDTO);
    ClienteDTO getCliente(Long clienteId) throws ClienteNotFoundException;
    ClienteDTO updateCliente(ClienteDTO clienteDTO);
    void deleteCliente(Long clienteId); //throws ClienteNotFoundException;

    CuentaActualDTO saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException;
    CuentaDeAhorroDTO saveCuentaBancariaAhorro(double balanceInicial, double tasaDeInteres, Long clienteId) throws ClienteNotFoundException;
    List<ClienteDTO> listClientes();
    CuentaBancariaDTO getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException;
    void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;
    void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException;
    void transfer(String cuentaIdPropietario,String cuentaDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;
    List<CuentaBancariaDTO> listCuentasBancarias();
    List<OperacionCuentaDTO> listarHistorialDeCuenta(String cuentaId);
}

