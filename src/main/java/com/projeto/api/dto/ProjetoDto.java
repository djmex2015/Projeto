package com.projeto.api.dto;

import com.projeto.api.enums.ERisco;
import com.projeto.api.enums.EStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class ProjetoDto {

  private String id;

  @NotNull
  private String nome;

  private LocalDate dataInicio;

  private LocalDate dataPrevisaoFim;

  private LocalDate dataFim;

  private String descricao;

  private EStatus status;

  private double orcamento;

  private ERisco risco;

  @NotNull
  private String idGerente;

}
