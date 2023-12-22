package com.nico.implpatrondto.web;

import com.nico.implpatrondto.dtos.CuentaBancariaDTO;
import com.nico.implpatrondto.dtos.OperacionCuentaDTO;
import com.nico.implpatrondto.exceptions.CuentaBancariaNotFoundException;
import com.nico.implpatrondto.services.CuentaBancariaService;
import lombok.Lombok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CuentaBancariaController {

    @Autowired
    CuentaBancariaService cuentaBancariaService;

    @GetMapping("/cuentas/{id}")
    public CuentaBancariaDTO listarDatosDeUnaCuentaBancaria(@PathVariable String id) throws CuentaBancariaNotFoundException {
        return cuentaBancariaService.getCuentaBancaria(id);
    }
    @GetMapping("/cuentas")
    public List<CuentaBancariaDTO> listarCuentasBancarias(){
        return cuentaBancariaService.listCuentasBancarias();
    }
    @GetMapping("/cuentas/{cuentaId}/operaciones")
    public List<OperacionCuentaDTO> listarHistorialDeCuenta(@PathVariable String cuentaId){
        return cuentaBancariaService.listarHistorialDeCuenta(cuentaId);
    }

}
