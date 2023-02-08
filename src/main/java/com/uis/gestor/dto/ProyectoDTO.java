package com.uis.gestor.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
public class ProyectoDTO implements Serializable {

    private static final long serialVersionUID = -1791114870731864009L;

    private Long id;

    private Date fechaRegistro;

    private String nombre;

    private String estado;
}
