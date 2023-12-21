package com.nico.implpatrondto.mappers;

import com.nico.implpatrondto.dtos.ClienteDTO;
import com.nico.implpatrondto.entities.Cliente;
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
}
