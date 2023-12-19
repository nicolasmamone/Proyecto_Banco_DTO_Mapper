package com.nico.implpatrondto.web;

import com.nico.implpatrondto.entities.Cliente;
import com.nico.implpatrondto.services.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ClienteController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @GetMapping("/clientes")
    public List<Cliente> listarClientes(){
        return cuentaBancariaService.listClientes();
    }
}
