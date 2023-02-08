package com.uis.gestor.service.interfaces;

import com.uis.gestor.dto.ProyectoDTO;
import com.uis.gestor.dto.TareaDTO;

import java.util.Date;
import java.util.List;

public interface ITareaService {
    TareaDTO crear(TareaDTO tareaDTO);

    TareaDTO cambiarEstado(Long idTarea, String estado);

    List<TareaDTO> getAllSinFinalizadosNiInactivos();

    List<ProyectoDTO> getAllProyectos();

    List<TareaDTO> getTareasByParametros(List<Long> idProyecto, Date to, Date from);
    /*List<TareaDTO> getAll();*/
}
