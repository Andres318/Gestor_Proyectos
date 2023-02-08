package com.uis.gestor.mapper;

import com.uis.gestor.dto.TareaDTO;
import com.uis.gestor.model.Tarea;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TareaMapper {

    TareaMapper INSTANCE = Mappers.getMapper(TareaMapper.class);

    // Dto to Entity
    @Mapping(target = "id", source = "id")
    Tarea toTarea(TareaDTO tareaDTO);


    // Entity to Dto
    @Mapping(target = "id", source = "id")
    TareaDTO toTareaDTO(Tarea tarea);

}
