package com.projeto.api.mapper;

import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.projeto.api.dto.ProjetoDto;
import com.projeto.api.model.PessoaEntity;
import com.projeto.api.model.ProjetoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMappingStrategy = RETURN_NULL)
public interface ProjetoMapper {

  ProjetoMapper INSTANCE = Mappers.getMapper(ProjetoMapper.class);

  ProjetoEntity toEntity(ProjetoDto request);

  @Mapping(source = "request.nome", target = "nome")
  @Mapping(source = "idGerente.id", target = "id", ignore = true)
  ProjetoEntity toUpdEntity(ProjetoDto request, @MappingTarget ProjetoEntity entity, String id, PessoaEntity idGerente);

  @Mapping(source = "idGerente.id", target = "idGerente")
  ProjetoDto toDto(ProjetoEntity entity);

  PessoaEntity map(String value);

}

