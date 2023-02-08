package com.uis.gestor.service.impl;

import com.uis.gestor.dto.TareaDTO;
import com.uis.gestor.mapper.TareaMapper;
import com.uis.gestor.model.Tarea;
import com.uis.gestor.repository.ITareaRepository;
import com.uis.gestor.service.interfaces.ITareaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaService implements ITareaService {

    Logger logger = LoggerFactory.getLogger(ITareaService.class);

    private ITareaRepository iTareaRepository;

    /*@Override
    public List<TareaDTO> getAll(){
        List<Tarea> tareaList = this.iTareaRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return tareaList.stream().map(TareaMapper.INSTANCE::toTareaDTO).collect(Collectors.toList());
    }*/

    @Override
    public TareaDTO crear(TareaDTO tareaDTO){
        Tarea tarea = TareaMapper.INSTANCE.toTarea(tareaDTO);
        Tarea tareaCreada = this.iTareaRepository.save(tarea);
        return TareaMapper.INSTANCE.toTareaDTO(tareaCreada);
    }

    @Override
    public TareaDTO cambiarEstado(Long idTarea, String estado){
        Tarea tarea = this.iTareaRepository.findById(idTarea).orElse(null);
        if (tarea == null){
            logger.info("La tarea con id " + idTarea + " no se encuentra en la BD");
            return null;
        }
        if (tarea.getEstado().equals("FINALIZADA")){
            logger.info("La tarea con id " + idTarea + " se FINALIZADA");
            return TareaMapper.INSTANCE.toTareaDTO(tarea);
        }

        tarea.setEstado(estado.toUpperCase());
        Tarea tareaSaved = this.iTareaRepository.save(tarea);
        return TareaMapper.INSTANCE.toTareaDTO(tareaSaved);
    }


    @Override
    public List<TareaDTO> getAllSinFinalizadosNiInactivos(){
        List<Tarea> tareaDTOList = this.iTareaRepository.findAllByEstadoNotLikeAndEstadoNotLikeOrderById("FINALIZADA", "INACTIVA");
        return tareaDTOList.stream().map(TareaMapper.INSTANCE::toTareaDTO).collect(Collectors.toList());

        /*return publicacionListResponse.stream().map(PublicacionMapper.INSTANCE::toPublicacionDTO).collect(Collectors.toList());*/
    }


    @Autowired
    public void setiTareaRepository(ITareaRepository iTareaRepository) {
        this.iTareaRepository = iTareaRepository;
    }

}
