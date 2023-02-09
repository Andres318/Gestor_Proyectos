package com.uis.gestor.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
public class GetTareasByParametrosDTO implements Serializable {

    private static final long serialVersionUID = 2998769466264337692L;

    private String toDate;

    private String fromDate;

    private List<Long> idProyecto;

    private String descripcionTarea;

    private String estado;

}
