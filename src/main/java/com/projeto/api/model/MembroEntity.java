package com.projeto.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Builder
@Entity
@Table(name = "membros")
@AllArgsConstructor
@NoArgsConstructor
@IdClass(MembroEntity.class)
public class MembroEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpessoa", referencedColumnName = "id")
    private PessoaEntity idPessoa;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprojeto", referencedColumnName = "id")
    private ProjetoEntity idProjeto;

}
