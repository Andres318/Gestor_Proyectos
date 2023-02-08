package com.uis.gestor.service.impl;

import com.uis.gestor.dto.ProyectoDTO;
import com.uis.gestor.dto.TareaDTO;
import com.uis.gestor.mapper.ProyectoMapper;
import com.uis.gestor.mapper.TareaMapper;
import com.uis.gestor.model.Proyecto;
import com.uis.gestor.model.Tarea;
import com.uis.gestor.repository.IProyectoRepository;
import com.uis.gestor.repository.ITareaRepository;
import com.uis.gestor.service.interfaces.ITareaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaService implements ITareaService {

    Logger logger = LoggerFactory.getLogger(ITareaService.class);

    private ITareaRepository iTareaRepository;

    private IProyectoRepository iProyectoRepository;

    /*@Override
    public List<TareaDTO> getAll(){
        List<Tarea> tareaList = this.iTareaRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return tareaList.stream().map(TareaMapper.INSTANCE::toTareaDTO).collect(Collectors.toList());
    }*/

    @Override
    public TareaDTO crear(TareaDTO tareaDTO){
        tareaDTO.setId(null);
        Tarea tarea = TareaMapper.INSTANCE.toTarea(tareaDTO);
        Tarea tareaCreada = this.iTareaRepository.save(tarea);

        Proyecto proyecto = this.iProyectoRepository.findById(tareaCreada.getIdProyecto()).orElse(null);

        TareaDTO tareaDTOResponse = TareaMapper.INSTANCE.toTareaDTO(tareaCreada);

        assert proyecto != null;
        tareaDTOResponse.setNombreProyecto(proyecto.getNombre());

        return tareaDTOResponse;
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
        List<Tarea> tareaDTOList = this.iTareaRepository.findAllByEstadoNotLikeAndEstadoNotLikeOrderByIdDesc("FINALIZADA", "INACTIVA");
        return tareaDTOList.stream().map(TareaMapper.INSTANCE::toTareaDTO).collect(Collectors.toList());

        /*return publicacionListResponse.stream().map(PublicacionMapper.INSTANCE::toPublicacionDTO).collect(Collectors.toList());*/
    }

    @Override
    public List<ProyectoDTO> getAllProyectos(){
        List<Proyecto> proyectoList = this.iProyectoRepository.findAll(Sort.by(Sort.Order.desc("id")));
        return proyectoList.stream().map(ProyectoMapper.INSTANCE::toProyectoDTO).collect(Collectors.toList());
    }

    @Override
    public List<TareaDTO> getTareasByParametros(List<Long> idProyecto, Date to, Date from){
        List<Tarea> tareas = this.iTareaRepository.findAllByIdProyectoInOrIdProyectoIsNullOrFechaGreaterThanEqualOrFechaLessThanEqualOrderByIdDesc(idProyecto, to, from);
        /*List<Tarea> tareas = this.iTareaRepository.findAllByIdProyectoInOrderByIdDesc(idProyecto);*/
        return tareas.stream().map(TareaMapper.INSTANCE::toTareaDTO).collect(Collectors.toList());
    }


    @Autowired
    public void setiTareaRepository(ITareaRepository iTareaRepository) {
        this.iTareaRepository = iTareaRepository;
    }

    @Autowired
    public void setiProyectoRepository(IProyectoRepository iProyectoRepository) {
        this.iProyectoRepository = iProyectoRepository;
    }

}
