package com.uis.gestor.repository;

import com.uis.gestor.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    List<Tarea> findAllByEstadoLikeOrderByIdDesc(String estado);

    @Query("SELECT t FROM Tarea t "+
    " WHERE t.estado = :estado " +
    " AND ( t.idProyecto = :idProyecto OR :idProyecto IS NULL)" +
    " AND ( t.fecha <= :toDate OR  (cast(:toDate as timestamp)) IS NULL)" +
    " AND ( t.fecha >= :fromDate OR (cast(:fromDate as timestamp)) IS NULL)" +
    " AND ( t.descripcion LIKE :descripcionTarea OR :descripcionTarea IS NULL)" +
    " ORDER BY t.id desc")
    List<Tarea> findByParams(Long idProyecto, Date toDate, Date fromDate, String descripcionTarea, String estado);

}
