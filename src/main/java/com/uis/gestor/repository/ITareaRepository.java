package com.uis.gestor.repository;

import com.uis.gestor.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ITareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findAllByEstadoNotLikeAndEstadoNotLikeOrderByIdDesc(String finalizado, String inactivo);

    /*List<Tarea> findAllByIdProyectoInOrIdProyectoIsNullOrFechaBetweenOrderByIdDesc(List<Long> idProyecto, Date to, Date from);*/

    List<Tarea> findAllByIdProyectoInOrIdProyectoIsNullAndFechaGreaterThanEqualOrFechaLessThanEqualAndDescripcionLikeOrderByIdDesc(List<Long> idProyecto, Date to, Date from, String descripcion);

    List<Tarea> findAllByIdProyectoInOrderByIdDesc(List<Long> idProyecto);

    /*List<Tarea> findAllByFechaAfterOrderByIdDesc(Date from);*/

    List<Tarea> findAllByFechaGreaterThanEqualOrderByIdDesc(Date from);

    /*List<Tarea> findAllByFechaBeforeOrderByIdDesc(Date to);*/

    List<Tarea> findAllByFechaLessThanEqualOrderByIdDesc(Date from);

    List<Tarea> findAllByFechaBetweenOrderByIdDesc(Date from, Date to);

    List<Tarea> findAllByDescripcionLikeOrderByIdDesc(String descripcionTarea);


}
