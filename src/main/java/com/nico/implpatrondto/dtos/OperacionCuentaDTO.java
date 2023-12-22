package com.nico.implpatrondto.dtos;

import com.nico.implpatrondto.enums.TipoDeOperacion;
import lombok.Data;

import java.util.Date;

@Data
public class OperacionCuentaDTO {

    private Long id;
    private Date fechaOperacion;
    private double monto;
    private TipoDeOperacion tipoDeOperacion;
    private String descripcion;
}
