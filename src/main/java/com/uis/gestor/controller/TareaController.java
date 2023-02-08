package com.uis.gestor.controller;

import com.uis.gestor.dto.TareaDTO;
import com.uis.gestor.service.interfaces.ITareaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tarea")
public class TareaController {

    Logger logger = LoggerFactory.getLogger(TareaController.class);

    private ITareaService iTareaService;


    @PostMapping("/crear")
    public ResponseEntity<TareaDTO> crear(@RequestBody @Valid TareaDTO tareaDTO){

        try {
            TareaDTO tareaSaved = this.iTareaService.crear(tareaDTO);
            return new ResponseEntity<>(tareaSaved, HttpStatus.OK);
        }
        catch (Exception exception) {
            logger.error(String.valueOf(exception));
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cambiarEstado")
    public ResponseEntity<TareaDTO> cambiarEstado(@RequestParam Long idTarea, @RequestParam String estado){

        try {
            TareaDTO tareaDTO = this.iTareaService.cambiarEstado(idTarea, estado);
            if (tareaDTO == null){
                /*logger.info("La tarea con id " + idTarea + " no se encuentra en la BD");*/
                return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
            }
            else {
                return new ResponseEntity<>(tareaDTO, HttpStatus.OK);
            }


        }
        catch (Exception exception) {
            logger.error(String.valueOf(exception));
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/finalizar")
    public ResponseEntity<TareaDTO> finalizar(@RequestParam Long idTarea){

        try {
            TareaDTO tareaDTO = this.iTareaService.cambiarEstado(idTarea, "finalizada");
            if (tareaDTO == null){
                logger.info("La tarea con id " + idTarea + " no se encuentra en la BD");
                return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
            }
            else {
                return new ResponseEntity<>(tareaDTO, HttpStatus.OK);
            }


        }
        catch (Exception exception) {
            logger.error(String.valueOf(exception));
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAllSinFinalizadosNiInactivos")
    public ResponseEntity<List<TareaDTO>> getAllSinFinalizadosNiInactivos(){
        try {
            List<TareaDTO> tareaDTOList = this.iTareaService.getAllSinFinalizadosNiInactivos();
            return new ResponseEntity<>(tareaDTOList, HttpStatus.OK);
        }
        catch (Exception exception) {
            logger.error(String.valueOf(exception));
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Autowired
    public void setiCiudadService(ITareaService iTareaService) {
        this.iTareaService = iTareaService;
    }

}
