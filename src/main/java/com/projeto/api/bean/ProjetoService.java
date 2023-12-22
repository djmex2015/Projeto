package com.projeto.api.bean;

import static com.projeto.api.enums.EStatus.EM_ANDAMENTO;
import static com.projeto.api.enums.EStatus.ENCERRADO;
import static com.projeto.api.enums.EStatus.INICIADO;
import static com.projeto.api.exception.ErrorCode.PROJETO_CAN_NOT_BE_REMOVED;
import static com.projeto.api.exception.ErrorCode.PROJETO_NOT_FOUND;
import static com.projeto.api.exception.Precondition.checkFalse;
import static com.projeto.api.exception.Precondition.checkNotNull;

import com.projeto.api.dto.ProjectoPessoasDto;
import com.projeto.api.dto.ProjetoDto;
import com.projeto.api.exception.ProjetoBusinessException;
import com.projeto.api.mapper.ProjetoMapper;
import com.projeto.api.model.MembroEntity;
import com.projeto.api.model.ProjetoEntity;
import com.projeto.api.repository.MembroRepository;
import com.projeto.api.repository.ProjetoRepository;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjetoService {

  private final PessoaService pessoaService;

  private final ProjetoRepository projetoRepo;

  private final MembroRepository membroRepo;

  public ProjetoDto find(String projetoId) throws ProjetoBusinessException {
    return ProjetoMapper.INSTANCE.toDto(findProjeto(projetoId));
  }

  @Transactional
  public ProjetoDto create(ProjetoDto request) throws ProjetoBusinessException {
    var gerente = pessoaService.find(request.getIdGerente());
    var entity = ProjetoMapper.INSTANCE.toEntity(request);
    entity.setIdGerente(gerente);
    return ProjetoMapper.INSTANCE.toDto(projetoRepo.save(entity));
  }

  @Transactional
  public ProjetoDto update(String projetoId, ProjetoDto request) throws ProjetoBusinessException {
    var projeto = findProjeto(projetoId);
    var gerente = pessoaService.find(request.getIdGerente());
    var entity = ProjetoMapper.INSTANCE.toUpdEntity(request, projeto, projetoId, gerente);
    entity.setIdGerente(gerente);
    return ProjetoMapper.INSTANCE.toDto(projetoRepo.save(entity));
  }

  @Transactional
  public void delete(String projetoId) throws ProjetoBusinessException {
    var projecto = findProjeto(projetoId);
    checkFalse(
        Stream.of(INICIADO, EM_ANDAMENTO, ENCERRADO).findFirst().filter(stat -> Objects.equals(stat, projecto.getStatus())).isPresent(),
        PROJETO_CAN_NOT_BE_REMOVED);
    projetoRepo.delete(projecto);
  }

  @Transactional
  public ProjectoPessoasDto associateMembers(String projetoId, ProjectoPessoasDto memberDto) throws ProjetoBusinessException {
    var projeto = findProjeto(projetoId);
    memberDto.getIdPessoas().stream().map(idPess -> {
          try {
            var pessoa = pessoaService.checkFuncionario(idPess);
            return MembroEntity.builder().idProjeto(projeto).idPessoa(pessoa).build();
          } catch (ProjetoBusinessException e) {
            throw new RuntimeException(e);
          }
        })
        .forEach(membroRepo::save);
    return memberDto;
  }

  private ProjetoEntity findProjeto(String projetoId) throws ProjetoBusinessException {
    return checkNotNull(projetoRepo.findById(projetoId), PROJETO_NOT_FOUND);
  }

}
