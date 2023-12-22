package com.projeto.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pessoa")
public class PessoaEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(length = 64)
    private Long id;

    @Column(nullable = false, length = 128)
    private String nome;

    @Column
    private LocalDate datanascimento;

    @Column(length = 14)
    private String cpf;

    @Column
    private boolean funcionario;

}
