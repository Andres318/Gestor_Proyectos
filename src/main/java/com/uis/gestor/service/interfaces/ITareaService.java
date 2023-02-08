package com.uis.gestor.service.interfaces;

import com.uis.gestor.dto.TareaDTO;

import java.util.List;

public interface ITareaService {
    TareaDTO crear(TareaDTO tareaDTO);

    TareaDTO cambiarEstado(Long idTarea, String estado);

    List<TareaDTO> getAllSinFinalizadosNiInactivos();
    /*List<TareaDTO> getAll();*/
}
