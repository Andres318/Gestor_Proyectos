package com.uis.gestor.repository;

import com.uis.gestor.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findAllByEstadoNotLikeAndEstadoNotLikeOrderById(String finalizado, String inactivo);


}
