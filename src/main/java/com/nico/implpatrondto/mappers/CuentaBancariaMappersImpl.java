package com.nico.implpatrondto.mappers;

import com.nico.implpatrondto.dtos.ClienteDTO;
import com.nico.implpatrondto.dtos.CuentaActualDTO;
import com.nico.implpatrondto.dtos.CuentaDeAhorroDTO;
import com.nico.implpatrondto.entities.Cliente;
import com.nico.implpatrondto.entities.CuentaActual;
import com.nico.implpatrondto.entities.CuentaDeAhorro;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CuentaBancariaMappersImpl {

    public ClienteDTO mapearDeCliente(Cliente cliente){
        ClienteDTO clienteDTO = new ClienteDTO();
        BeanUtils.copyProperties(cliente, clienteDTO);
        return clienteDTO;
    }
    public Cliente mapearDeClienteDTO(ClienteDTO clienteDTO){
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO, cliente);
        return cliente;
    }
    public CuentaDeAhorroDTO mapearDeCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro){
        CuentaDeAhorroDTO cuentaDeAhorroDTO = new CuentaDeAhorroDTO();
        BeanUtils.copyProperties(cuentaDeAhorro, cuentaDeAhorroDTO);
        cuentaDeAhorroDTO.setClienteDTO(mapearDeCliente(cuentaDeAhorro.getCliente()));
        cuentaDeAhorroDTO.setTipo(cuentaDeAhorro.getClass().getSimpleName());//
        return cuentaDeAhorroDTO;
    }
    public CuentaDeAhorro mapearDeCuentaDeAhorroDTO(CuentaDeAhorroDTO cuentaDeAhorroDTO){
        CuentaDeAhorro cuentaDeAhorro = new CuentaDeAhorro();
        BeanUtils.copyProperties(cuentaDeAhorroDTO, cuentaDeAhorro);
        cuentaDeAhorro.setCliente(mapearDeClienteDTO(cuentaDeAhorroDTO.getClienteDTO()));
        return cuentaDeAhorro;
    }
    public CuentaActualDTO mapearDeCuentaActual(CuentaActual cuentaActual){
        CuentaActualDTO cuentaActualDTO = new CuentaActualDTO();
        BeanUtils.copyProperties(cuentaActual, cuentaActualDTO);
        cuentaActualDTO.setClienteDTO(mapearDeCliente(cuentaActual.getCliente()));
        cuentaActualDTO.setTipo(cuentaActual.getClass().getSimpleName());
        return cuentaActualDTO;
    }
    public CuentaActual mapearDeCuentaActualDTO(CuentaActualDTO cuentaActualDTO){
        CuentaActual cuentaActual = new CuentaActual();
        BeanUtils.copyProperties(cuentaActualDTO, cuentaActual);
        cuentaActual.setCliente(mapearDeClienteDTO(cuentaActualDTO.getClienteDTO()));
        return cuentaActual;
    }
}
