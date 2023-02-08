package com.uis.gestor.repository;

import com.uis.gestor.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ITareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findAllByEstadoNotLikeAndEstadoNotLikeOrderByIdDesc(String finalizado, String inactivo);

    List<Tarea> findAllByIdProyectoInOrIdProyectoIsNullOrFechaBetweenOrderByIdDesc(List<Long> idProyecto, Date to, Date from);

    List<Tarea> findAllByIdProyectoInOrIdProyectoIsNullOrFechaGreaterThanEqualOrFechaLessThanEqualOrderByIdDesc(List<Long> idProyecto, Date to, Date from);

    /*List<Tarea> findAllByIdProyectoInOrderByIdDesc(List<Long> idProyecto);*/

    /*List<Tarea> findAllByFechaBetweenOrderByIdDesc(Date to, Date from);*/

}
