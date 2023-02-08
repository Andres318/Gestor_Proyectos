package com.uis.gestor.mapper;

import com.uis.gestor.dto.ProyectoDTO;
import com.uis.gestor.model.Proyecto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProyectoMapper {

    ProyectoMapper INSTANCE = Mappers.getMapper(ProyectoMapper.class);

    // Dto to Entity
    @Mapping(target = "id", source = "id")
    Proyecto toProyecto(ProyectoDTO proyectoDTO);


    // Entity to Dto
    @Mapping(target = "id", source = "id")
    ProyectoDTO toProyectoDTO(Proyecto proyecto);
}
