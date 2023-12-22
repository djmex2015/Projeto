package com.projeto.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectoPessoasDto {

  private String idProjeto;

  private List<String> idPessoas;

}
