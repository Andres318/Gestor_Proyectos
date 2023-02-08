package com.uis.gestor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Data
@ToString
@NoArgsConstructor
public class TareaDTO implements Serializable {

    private static final long serialVersionUID = 1194773724605235898L;

    @NotNull
    private Long id;

    @NotNull
    private Long idProyecto;


    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fecha;

    @NotNull
    private String descripcion;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaRegistro;

    @NotNull
    private String estado;

    @NotNull
    private String nombre;

}
