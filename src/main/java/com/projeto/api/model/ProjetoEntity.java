package com.projeto.api.model;

import com.projeto.api.enums.ERisco;
import com.projeto.api.enums.EStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "projeto")
public class ProjetoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(length = 64)
  private Long id;

  @Column(nullable = false, length = 128)
  private String nome;

  @Column(length = 128, name = "data_inicio")
  private LocalDate dataInicio;

  @Column(name = "data_previsao_fim")
  private LocalDate dataPrevisaoFim;

  @Column(name = "data_fim")
  private LocalDate dataFim;

  @Column(length = 5000)
  private String descricao;

  @Enumerated(EnumType.STRING)
  @Column(length = 45)
  private EStatus status;

  @Column(nullable = false)
  private float orcamento;

  @Enumerated(EnumType.STRING)
  @Column(length = 45)
  private ERisco risco;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idgerente", referencedColumnName = "id")
  private PessoaEntity idGerente;

}
