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

import java.util.ArrayList;
import java.util.Comparator;
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
    public List<TareaDTO> getTareasByParametros(List<Long> idProyecto, Date to, Date from, String descripcionTarea, String estado){

        if (descripcionTarea != null){
            descripcionTarea = "%" + descripcionTarea + "%";
        }
        List<Tarea> tareasFiltradas = this.iTareaRepository.findByParams(idProyecto.get(0), to, from, descripcionTarea, estado);

        /**List<Tarea> allTareas =  new ArrayList<>();
        List<Tarea> allTareasSinOrdenar =  new ArrayList<>();
        List<Tarea> tareasTest =  new ArrayList<>();
        List<Long> idsTaresAgregados =  new ArrayList<>();
        List<List<Tarea>> listaDeListas = new ArrayList<>();
        List<Tarea> tareasByIdProyecto;
        List<Tarea> tareasByDescripcion;
        List<Tarea> tareasByEstado;
        List<Tarea> tareasByFechaRange;
        List<Tarea> tareasByOnlyFechaFrom;
        List<Tarea> tareasByOnlyFechaTo;

        if (idProyecto == null && to == null && from == null && descripcionTarea == null && estado == null){
            allTareas = this.iTareaRepository.findAll(Sort.by(Sort.Order.desc("id")));
            return allTareas.stream().map(TareaMapper.INSTANCE::toTareaDTO).collect(Collectors.toList());
        }

        if (idProyecto != null){
            tareasByIdProyecto = this.iTareaRepository.findAllByIdProyectoInOrderByIdDesc(idProyecto);
            listaDeListas.add(tareasByIdProyecto);
        }

        if (estado != null){
            estado = "%" + estado + "%";
            tareasByEstado = this.iTareaRepository.findAllByEstadoLikeOrderByIdDesc(estado);
            listaDeListas.add(tareasByEstado);
        }

        if (descripcionTarea != null){
            descripcionTarea = "%" + descripcionTarea + "%";
            tareasByDescripcion = this.iTareaRepository.findAllByDescripcionLikeOrderByIdDesc(descripcionTarea);
            listaDeListas.add(tareasByDescripcion);
        }

        if (from != null && to != null){
            tareasByFechaRange = this.iTareaRepository.findAllByFechaBetweenOrderByIdDesc(from, to);
            listaDeListas.add(tareasByFechaRange);
        }
        else if (from != null ){
            tareasByOnlyFechaFrom = this.iTareaRepository.findAllByFechaGreaterThanEqualOrderByIdDesc(from);
            listaDeListas.add(tareasByOnlyFechaFrom);
        }
        else if (to != null ){
            tareasByOnlyFechaTo = this.iTareaRepository.findAllByFechaLessThanEqualOrderByIdDesc(to);
            listaDeListas.add(tareasByOnlyFechaTo);
        }

        logger.info("------------------------------------------------------------------------------------------------------------------------------------------------------------");
        //tareasTest = this.iTareaRepository.findAllByIdProyectoInOrIdProyectoIsNullAndFechaGreaterThanEqualOrFechaLessThanEqualAndDescripcionLikeOrderByIdDesc(idProyecto, to, from, descripcionTarea);
        logger.info("------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (List<Tarea> lista: listaDeListas) {
            for (Tarea tarea: lista) {
                if (!idsTaresAgregados.contains(tarea.getId())){
                    idsTaresAgregados.add(tarea.getId());
                    allTareasSinOrdenar.add(tarea);
                }
            }
        }

        List<Long> listaOrdenada = idsTaresAgregados.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        for (Long idTarea: listaOrdenada) {
            Boolean pass = Boolean.TRUE;
            for (List<Tarea> lista: listaDeListas) {
                for(Tarea tarea: lista){
                    if (tarea.getId().equals(idTarea) && pass){
                        allTareas.add(tarea);
                        pass = Boolean.FALSE;
                    }
                }
            }
        }**/



        return tareasFiltradas.stream().map(TareaMapper.INSTANCE::toTareaDTO).collect(Collectors.toList());
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
